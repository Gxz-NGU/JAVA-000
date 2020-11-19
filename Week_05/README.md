# 作业说明：

## 作业1：几种装配bean的方式

放在了包bean中，有三种形式。第一种是xml注入bean，第二种是在类上配置@Configuration，方法上写@Bean，第三种是直接在类上写@Component，然后开启@ComponentScan，在使用的时候写@Autowired自动注入

## 作业2：实现Klass、Student、School自动装配和starter

放在了starter包中

说实话，不知道这题啥意思。所以在网上搜了一下，如何编写starter，所以就照着网上教程的例子，写了个HelloworldService，并打包成jar包，放在了springboot里面，没有在pom.xml中声明这个jar包，而是直接导入的包，在application.yml中编写helloword.words时可以实现对应的值的修改，在Controller中@Autowired一下HelloworldService是可以成功运行。

**但是照葫芦画瓢想要生成一个SchoolService的话，就报了如下的错误，如果助教老师能给解答一下不胜感激。**测试用的springboot程序也放上去了。

![image-20201119164600673](E:\workspace\JAVA-000\Week_05\resource\image-20201119164600673.png)

## 作业3：原生JDBC实现CRUD，用事务，PreparedStatement，Hikari连接池进行改进。

放在了jdbc包中。

其他的使用方法都还好，其中Hikari连接池使用比较有问题，我可以根据github上给出的调用方式拿到Connection，但是我并不知道该怎么测试它，看它是否真的有了个性能提升，照理说，应该是用多线程抢占资源的方式去看看是否比单线程更快，但按我程序写的那样，测时间，似乎并没有更快 。

学习笔记

## Spring FX

### IOC——使用的DI方法

两个类没有关系，靠Spring搞他们互相依赖的关系。

分散在两个项目的类的属性循环依赖问题spring可以解决，构造函数内的循环依赖无法解决。

### AOP

在类上添加新的能力。通过拦截方法调用来做增强的。

用了接口的类，使用的是动态代理。没有接口，直接的类就是用的CGLib

### Instrumentation

完全不侵入jar包，Javaagent，在类加载之前改变它，而且javaagent不与待拦截的类打包在一起。

### ByteBuddy

字节码增强工具，优势：好用，不需要自己一个指令一个指令的拼。

### Spring Bean生命周期

复杂的原因：通用的框架类

构造函数-依赖注入-BeanNameAware-BeanFactoryAware-ApplicationContextAware-BeanPostPreocessor前置方法-InitializingBean-自定义init方法-BeanPostProcessor-使用-DisposableBean-自定义destory方法

步骤和classloader加载类的时候步骤一样

### Spring Messaging

同步 转 异步，依赖关系变简单。

JMS（J2EE规范，定义了消息的格式，类似于JDBC） 

### 什么是SpringBoot

框架：具备各种功能可以让你应用于业务系统（spring）

脚手架：限定性的框架（springboot）

解决方案：针对某一领域解决特定问题的方法（springcloud解决微服务）

### SpringBoot核心原理

1. 自动化配置：Configuration，EnbaleXX，Condition
2. spring-boot-starter：整合第三方类库

### 配置原理：

application.yaml - configuration- Bean 

前缀-一组配置-starter组件

### 为何要约定大于配置？

### 为何大公司都用Mybatis而不是Hibernate

1. mybatis便于sql优化，可读性强
2. 可以将SQL 语句从程序中抠出来，交给DBA去做code review

