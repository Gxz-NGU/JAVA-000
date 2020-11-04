第一份作业请见项目nio02-first
=======

添加了`OkhttpOutboundHandler`内容

修改了`HttpInboundHandler`使用的handler

# 第三份作业是在第一份作业的基础上修改的

添加了`MyHttpRequestFilter`以实现添加{"nio":"xiaofeiyang"}请求头的功能

在`HttpInboundHandler` channelRead方法中，添加了filter

在`OkhttpOutboundHandler`中获取了请求头内容，并将请求头内容作为另一个请求头发起

## 收获：

```
解决了一个疑问，
在chrome中，访问http://localhost:8888/api/hello中的network查看请求头时，没有nio这项，算成功么？
// 经过群友的解答，发现了问题，我在chrome里看到的请求头，是chrome为我添加好的，我在网关中添加的请求，在chrome肯定是看不见的，但是我可以
// 把请求里的东西拿出来，放入response中
```

<img src="resources/noheaders.png" style="zoom:50%;" />

<img src="resources/resphere.png" style="zoom:50%;" />

学习笔记

### 什么是高性能？

高并发用户（日活用户数量、同时在线的用户数量）、高吞吐量、低延迟

### 延迟是针对系统的，响应时间是针对客户或调用系统的

### P99 百分之99的请求的响应时间是多少

### 高性能的副作用

1. 复杂度10倍增长、建设和维护成本++++、故障和bug导致的损失10倍增长

### 目前国内最大的并发量：

双十一的第一分钟内支付宝50万TPS，所有电商都需要用支付宝支付

