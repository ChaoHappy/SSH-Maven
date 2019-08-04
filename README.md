[toc]

# 1. Maven基础
Maven是Apache公司的开源项目，是项目构建工具。用来依赖管理。
## 1.1 Maven的好处
1. 不需要再拷贝jar包。项目中不需要存放jar包。项目源代码占用空间很小
2. 使用maven开发的工程，如果环境统一导入别的项目不会报错
3. 代码耦合度进一步降低
4. 方便项目进行升级
5. 节省人力成本 

## 1.2 Maven两大核心：

    依赖管理：对jar包统一管理。
    项目构建：项目在编码完成后，对项目进行编译、测试、打包、部署等一系列的操作都通过命令实现。
## 1.3 Maven安装、配置本地仓库
Maven程序安装前提：Maven程序是Java开发的，它运行依赖JDK
## 1.4 Maven项目标准目录结构
```
ProjectName
    src
        main
            java
            resource
            webapp/WEB-INF/web.xml
        Test
            java
            Resource
    pom.xml
```

## 1.5 Maven的常用命令

**mvn tomcat:run**

    通过Maven命令将Web项目发布到Tomcat中
**clean:清理**

    将项目目录中target目录清理掉
**compile：编译**

    将项目中.java文件编译成为.class文件
    
**test：单元测试**

    将项目的根目录下src/test/java目录下的单元测试类都会执行。
    单元测试类有要求：XxxTest.java
    
**package:打包**

    web项目----war包
    java项目----jar包
    将项目打包，打包项目根目录target目录
**install：安装**

    解决本地多个项目共用一个jar包。
    打包到本地仓库
**deploy：**

    将jar包上传到私服

**Maven的生命周期**

    在Maven中存在“三套”生命周期，每一套声明周期相互独立，互不影响。在一套生命周期内，执行后面的命令，前面的操作会自动执行。

    CleanLifeCycle:清理声明周期。
        clean
    defaultLifeCycle:默认生命周期
        compile，test,package,deploy
    siteLifeCycle:站点生命周期
        site
        
# 2. Maven整合Web项目案例

## 2.1 配置eclipse中Maven环境
- 配置m2e插件，新版eclipse自带Maven插件
- 配置Maven程序
- 配置userSetting：让eclipse知道Maven仓库

**Group Id : 公司名称**

**Artifact Id：项目名称**

**Version：版本**

    SNAPSHOT 测试版本
    RELEASES 正式版本

**packaging： 打包方式**

    jar--- java 项目
    war ---web项目
    pom ----父工程
**在pom文件中添加 jdk编译插件**

```
<plugins>
    	<plugin>
    		<groupId>org.apache.maven.plugins</groupId>
    		<artifactId>maven-compiler-plugin</artifactId>
    		<version>3.8.0</version>
    		<configuration>
    			<source>1.7</source>
    			<target>1.7</target>
    			<encoding>UTF-8</encoding>
    		</configuration>
    	</plugin>
    </plugins>
```

## 2.2 创建Servlet

创建Servlet类

```
public class HelloMaven extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HelloMaven() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
```

在web.xml中配置Servlet

```
<servlet>
    <description></description>
    <display-name>HelloMaven</display-name>
    <servlet-name>HelloMaven</servlet-name>
    <servlet-class>com.zlc.servlet.HelloMaven</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>HelloMaven</servlet-name>
    <url-pattern>/HelloMaven</url-pattern>
  </servlet-mapping>
```


## 2.3 依赖范围

以来范围 | 对于编译classpath有效|对于测试classpath有效|对于运行时classpath有效|例子
---|---|---|---|---|
compile | Y | Y |Y  |spring-core    |
test    |-  | Y | - |junit          |
provided| Y | Y |-  |servlet-api    |
runtime | - | Y |Y  |jdbc驱动       |
system  | Y | Y |-  |本地的Maven仓库之外的类库|

==**添加依赖范围：默认是compile**==

==**provided 运行部署到tomcat不在需要**==


## 2.4 Maven整合struts2
- 添加struts2依赖

```
<dependency>
	  <groupId>org.apache.struts</groupId>
	  <artifactId>struts2-core</artifactId>
	  <version>2.5.20</version>
</dependency>
```

- 创建struts.xml
- 创建action
- 在web.xml中配置struts2框架核心过滤器

```
<!--  配置struts2框架核心过滤器 -->
 <filter>
 	<filter-name>struts2</filter-name>
 	<filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilte</filter-class>
 </filter>
 
 <filter-mapping>
 	<filter-name>struts2</filter-name>
 	<url-pattern>/*</url-pattern>
 </filter-mapping>
```
- 编写action方法

```
public String save() throws Exception {
		System.out.println("CustomerAction调用了save方法");
		return SUCCESS;
	}
```

- 在struts.xml中配置

```
<package name="demo" namespace="/" extends="struts-default">
    	<action name="customerAction_*" class="com.zlc.action.CustomerAction">
    		<result name="success">/index.jsp</result>
    	</action>
    </package>
```

## 2.5 总结
1. 安装
2. Maven标准目录结构

```
ProjectName
    src
        main
            java
            resource
            webapp/WEB-INF/web.xml
        Test
            java
            Resource
    pom.xml
```

3. Maven常用命令
4. 使用eclipse开发maven项目
5. pom.xml:项目对象模型

# 3 Maven 实战
## 3.1 pom.xml文件
### 3.1.1传递依赖

    A依赖于B，B依赖于C(1.1版本)，B是A的直接依赖，C就是A的传递依赖。
    导入依赖D，D依赖C(1.2版本)
###    3.1.2 Maven自己调节原则
- 第一声明者有限原则

```
谁先定义的就用谁的传递依赖
```
- 路径近者优先原则

```
直接依赖级别高于传递依赖
```
### 3.1.3 排除依赖

```
<dependency>
	  <groupId>org.apache.struts</groupId>
	  <artifactId>struts2-core</artifactId>
	  <version>2.5.20</version>
	  <exclusions>
	  <!-- 将struts2-core.jar包中commons-io的传递依赖排除掉 -->
	  	<exclusion>
	  		<groupId>commons-io</groupId>
	  		<artifactId>commons-io</artifactId>
	  	</exclusion>
	  </exclusions>
    </dependency>
```
### 3.1.4 指定依赖版本

```
<!-- 版本锁定：指定项目中的依赖版本 -->
<dependencyManagement>
  	<dependencies>
  		<dependency>
  			<groupId>org.springframework</groupId>
  			<artifactId>spring-beans</artifactId>
  			<version>4.1.4.RELEASE</version>
  		</dependency>
  	</dependencies>
  </dependencyManagement>
```
### 3.1.5 属性

```
 <!-- 属性 -->
  <properties>
  	<spring.version>4.1.4.RELEASE</spring.version>
  </properties>
<dependencyManagement>
  	<dependencies>
  		<dependency>
  			<groupId>org.springframework</groupId>
  			<artifactId>spring-beans</artifactId>
  			<version>${spring.version}</version>
  		</dependency>
  	</dependencies>
  </dependencyManagement>
```





## 3.2 通过Maven整合SSH框架（重点）
项目名称：SSH-Maven
### 3.2.1 添加ssh依赖

```
<properties>
  	<spring.version>4.1.4.RELEASE</spring.version>
  	<hibernate.version>5.0.7.Final</hibernate.version>
  	<struts.version>2.3.24</struts.version>
  </properties>
  
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.10</version>
      <scope>test</scope>
    </dependency>
    <dependency>
    	<groupId>javax.servlet</groupId>
    	<artifactId>javax.servlet-api</artifactId>
    	<version>4.0.1</version>
    </dependency>

    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-context</artifactId>
    	<version>${spring.version}</version>
    </dependency>
    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-aspects</artifactId>
    	<version>${spring.version}</version>
    </dependency>
    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-orm</artifactId>
    	<version>${spring.version}</version>
    </dependency>
    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-test</artifactId>
    	<version>${spring.version}</version>
    </dependency>
    <dependency>
    	<groupId>org.springframework</groupId>
    	<artifactId>spring-web</artifactId>
    	<version>${spring.version}</version>
    </dependency>
    <dependency>
    	<groupId>org.hibernate</groupId>
    	<artifactId>hibernate-core</artifactId>
    	<version>${hibernate.version}</version>
    </dependency>
    <dependency>
    	<groupId>org.apache.struts</groupId>
    	<artifactId>struts2-core</artifactId>
    	<version>${struts.version}</version>
    </dependency>
    <dependency>
    	<groupId>org.apache.struts</groupId>
    	<artifactId>struts2-spring-plugin</artifactId>
    	<version>${struts.version}</version>
    </dependency>
  </dependencies>
```

### 3.2.2 搭建struts2环境
1. 创建struts2配置文件：struts.xml
2. 在web.xml中配置struts2的核心过滤器

```
 <!--  配置struts2框架核心过滤器 -->
 <filter>
 	<filter-name>struts2</filter-name>
 	<filter-class>org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter</filter-class>
 </filter>
 
 <filter-mapping>
 	<filter-name>struts2</filter-name>
 	<url-pattern>/*</url-pattern>
 </filter-mapping>
```

### 3.2.3 搭建Spring环境
1. 创建spring配置文件 applicationContext.xml
2. 在web.xml中配置监听器ContextLoaderListener

```
 <!-- 配置监听器:默认加载WEB-INF/applicationContext.xml -->
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  <!-- 通过上下文参数指定配置spring配置文件路径 -->
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:applicationContext.xml</param-value>
  </context-param>
```
### 3.2.4 搭建Hibernate环境
- 创建Hibernate核心配置文件 hibernate.cfg.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
		"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
		"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory name="mysql">
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <property name="hibernate.connection.password">1111</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/hibernate</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        
        <!-- 为了方便调试是否在运行hibernate时在日志中输出sql语句 -->
        <property name="hibernate.show_sql">true</property>
        
        <!-- 是否对日志中输出的sql语句进行格式化 -->
        <property name="hibernate.format_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">update</property>
    </session-factory>
</hibernate-configuration>

```
### 3.2.4 struts2跟spring整合

**整合关键点：action对象创建，交给spring创建**

- 创建action类
- 将action对象配置到spring配置文件中
- 在struts.xml中的action节点中class属性配置为spring工厂中action对象bean的id

### 3.2.5 spring跟hibernate框架整合

    整合关键点：
    1. 数据源DataSource交给Sprig
    2. sessionFactory对象创建交给Spring
    3. 事务管理
- 在spring配置文件中配置DataSource

```
<!-- 加载属性文件 "classpath:"只有在spring框架中使用-->
	<context:property-placeholder location="classpath:db.properties"/>
	
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driverClass}"></property>
		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
		<property name="user" value="${jdbc.username}"></property>
		<property name="password" value="${jdbc.password}"></property>
	</bean>
```
- 在spring配置文件中配置sessionFactory

```
<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
		<property name="dataSource" ref="dataSource"></property>
		<property name="configLocations" value="classpath:applicationContext.xml"></property>
	</bean>
```
- 事务管理
1）配置事务管理器：PlatFormTransactionManager接口
    i. jdbc：DataSourceTransactionManager
    ii.hibernate：HibernateTransactionManager


```
<!-- 配置事务管理器 -->
	<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>
	
	<!-- xml方式管理事务 -->
	<!-- 配置通知：具体增强逻辑 -->
	<tx:advice id="txAdvice">
		<tx:attributes>
			<!-- 匹配业务类中方法名称 -->
			<tx:method name="save*"/>
			<tx:method name="update*"/>
			<tx:method name="delete*" read-only="true"/>
			<tx:method name="find*"/>
			<tx:method name="*"/>
		</tx:attributes>
	</tx:advice>
	
	<!-- 配置AOP -->
	<aop:config>
		<!-- 配置切入点：具体哪些方法要增强（真正被增强的方法） -->
		<aop:pointcut expression="execution(* com.zlc.service.*.*(..))" id="cut"/>
		<!-- 配置切面：将增强逻辑作用到切点（通知+切入点） -->
		<aop:advisor advice-ref="txAdvice" pointcut-ref="cut"/>
	</aop:config>
	<!-- xml方式管理事务 结束 -->
	
	<!-- 注解方式管理事务 -->
	<!-- 1、开启注解驱动，2、在service类上或者方法上使用注解@Transactional -->
	<tx:annotation-driven transaction-manager="transactionManager" />
	<!-- 注解方式管理事务 结束 -->
```
### 3.2.6 需求

在地址栏输入action请求，action-service-dao。完成客户查询。

### 3.2.7 具体实现

- 创建客户实体类以及映射文件、将映射文件引入到Hibernate核心配置文件中
- 创建action、service、dao 完成注入


    在类中添加属性生成set方法
    在Spring配置文件中完成注入

```
<!-- 配置DAO对象 -->
	<bean id="customerDao" class="com.zlc.dao.impl.CustomerDaoImpl">
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>
	
	<!-- 配置service对象 -->
	<bean id="customerService" class="com.zlc.service.impl.CustomerServiceImpl">
		<property name="customerDao" ref="customerDao"></property>
	</bean>
	
	
	
	<!-- 配置action对象 切记action要设置成多实例prototype -->
	<bean id="customerAction" class="com.zlc.web.action.CustomerAction" scope="prototype">
		<property name="customerService" ref="customerService"></property>
	</bean>
```
- 在struts.xml配置文件中配置action，配置结果视图

```
<package name="demo" namespace="/" extends="struts-default" strict-method-invocation="false">
    	<action name="customerAction_*" class="customerAction" method="{1}">
    		<result name="success">/index.jsp</result>
    	</action>
    </package>
```
**总结：**
1. 页面提交参数，在服务端接收参数
2. 调用业务层方法-->dao方法-->DB
3. 将返回的数据存到值栈
4. 配置结果视图

# 4. 通过Maven对项目进行拆分、聚合（重点）

对现在已有的ssh项目进行拆分拆分思路
将dao层的代码以及配置文件全体提取出来，放到一个表现上独立的工程。service、action拆分。

拆分完成对拆分后的项目进行聚合，提出概念 父工程

    **ssh-parent** :父工程

        **ssh-dao**

        **ssh-service**

        **ssh-web**

### 4.1 创建父工程01_maven_parent-ssh

父工程名称：01_maven_parent-ssh

创建好父工程目录结构：只有pom.xml，可以推断父工程不进行编码。
> 1. 项目需要的依赖信息，在父工程中定义，子模块继承过来
> 2. 将各个子模块聚合到一起
### 8.3.2 将创建好的父工程发布到本地仓库
将来 service、dao工程发布到本地仓库，发布的service工程会报错。
如果忘记此步骤，将父工程发布到本地仓库。

### 4.2 创建子模块01_maven_ssh-dao

ssh-dao负责数据访问层：包含dao相关代码和配置文件

1. 右键-->new -->Maven Module
2. Module Name：01_maven_ssh-dao
3. Parent Project：选择父工程
4. Packaging：jar
5. 将Spring的配置文件拆分


    applicationContext-basic.xml：项目基础的信息
    applicationContext-dao.xml：dao层bean对象

### 4.3 创建子模块01_maven_ssh_service

1. 右键-->new -->Maven Module
2. Module Name：01_maven_ssh_service
3. Parent Project：选择父工程
4. Packaging：jar
5. 将Spring的配置文件拆分
6. 在service工程pom.xml文件添加ssh-dao的依赖

### 4.4  创建子模块 01_maven_ssh-web
1. 右键-->new -->Maven Module
2. Module Name：01_maven_ssh-web
3. Parent Project：选择父工程
4. Packaging：war
5. 将Spring的配置文件拆分
6. 添加web.xml，加载项目和jar包中的spring配置文件
7. 在ssh-web项目中添加ssh-service工程依赖

```
<!-- 通过上下文参数指定配置spring配置文件路径 -->
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath*:spring/applicationContext-*.xml</param-value>
  </context-param>
```

### 4.5 单元测试
- 批量加载spring配置文件
> classpath:spring/applicationContext-*.xml

> ==classpath*==:spring/applicationContext-*.xml：既要加载本项目中配置文件，还要加载jar包中的配置文件

- 传递依赖

因为依赖会有依赖范围，依赖范围对传递依赖也有影响，例如有A、B、C，A依赖B、B依赖C，C可能是A的传递依赖，如下表格：

直接依赖\传递依赖   |compile    |provided   |runtime    |test
---                 |---        |---        |---        |---
compile             | compile   |-          |runtime    |-
provided            | provided  | provided  |provided   | -
runtime             | runtime   |-          | runtime   | -
test                | test      | -         | test      | -

**总结：当项目中需要的某一个依赖没有传递过来，在自己的工程中添加对应依赖就可以。**

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-*.xml")
public class CustomerServiceTest {
	
	@Autowired
	private CustomerService customerService;

	@Test
	public void testFindOne() {
		customerService.findOne(1);
	}

}
```

### 4.6 测试

**Maven 方式：**
> 方式1：运行父工程。父工程将各个子模块聚合到一起。将ssh-web打成war包发布到tomcat
> 方式2：直接运行web工程

**其他方式：**
> 部署到tomcat







## 5. 私服应用（了解）

### 5.1 私服安装
- 下载安装包
- 解压到本地磁盘
- 使用管理员权限打开dos,执行命令安装私服
> 进入bin目录 输入：nexus install
- 启动服务 nexus start
- 找到私服的访问url:
> http://localhost:8081/nexus/#welcome
登录 admin/admin123

### 5.2 私服仓库类型

hosted：宿主仓库
    存放本公司开发的jar包（正式版本、测试版本、第三方：存在版权问题-Oracle）
proxy ：代理仓库
    代理中央仓库、Apache下测试版本的jar包
Group：组仓库
    包含Hosted:宿主仓库、Proxy：代理仓库

### 5.3 上传jar包到私服上（应用）
- 在maven目录下conf/settings.xml 认证：配置用户名密码

```
<servers>
<server>
    <id>releases</id>
    <username>admin</username>
    <password>admin123</password>
</server>

<server>
    <id></id>
    <username>snapshots</username>
    <password>admin123</password>
</server>
</servers>
```

- 在将要上传的项目的pom.xml中配置jar包上传路径（URL）

```
<distributionManagement>
  	<repository>
  		<id>releases</id>
  		<url>http://localhost:8081/nexus/content/repositories/releases</url>
  	</repository>
  	<snapshotRepository>
  		<id>snapshots</id>
  		<url>http://localhost:8081/nexus/content/repositories/snapshots</url>
  	</snapshotRepository>
  </distributionManagement>
```

- 执行命令发布项目到私服（上传）
> deploy

### 5.4 下载jar包到本地仓库（应用）
- 在maven目录下conf/setting.xml配置模板

```
<profiles>
  	<id>dev</id>
  	<repositories>
	  	<repository>
	  		<!-- 仓库id, repositories可以配置多个仓库，保证id不重复-->
	  		<id>nexus</id>
	  		<url>http://localhost:8081/nexus/content/groups/public</url>
	  	</repository>
	  	<!-- 是否下载releases构件 -->
	  	<releases>
	  		<enabled>true</enabled>
	  	</releases>
	  	<!-- 是否下载snapshots构件 -->
	  	<snapshots>
	  		<enabled>true</enabled>
	  	</snapshots>
  	</repositories>
  	<pluginRepositories>
  		<!-- 插件仓库，maven的运行依赖插件，也需要从私服下载插件 -->
  		<pluginRepository>
  			<!-- 插件仓库id不允许重复，如果重复后边配置会覆盖前边 -->
  			<id>public</id>
  			<name>Public Repositories</name>
  			<url>http://localhost:8081/nexus/content/groups/public</url>
  		</pluginRepository>
  	</pluginRepositories>
  </profiles>
```

- 激活模板

```
<activeProfiles>
  	<activeProfile>dev</activeProfile>
  </activeProfiles>
```

# 6. 总结
1. 使用maven 整合ssh框架（掌握）
2. 拆分maven工程（掌握）
> 将每一层代码&配置文件全部提取提取一个表现独立工程
3. 私服（了解）

# 7. Maven 好处
1. 不需要再拷贝jar包。项目中不需要存放jar包。项目源代码占用空间很小
2. 使用maven开发的工程，如果环境统一导入别的项目不会报错
3. 代码耦合度进一步降低
4. 方便项目进行升级
5. 节省人力成本 

