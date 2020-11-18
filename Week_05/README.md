学习笔记



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

