# 认证中心

## Auth模块

提供OAuth认证服务

基于SpringSecurity提供认证中心登录和路径拦截功能

基于OAuth2提供授权端点，通过访问/OAuth/token进行授权功能

使用jwt替换默认随机UUID token，增强JWT内容&有状态增强(redis存储token和用户信息的双向绑定)

## 认证流程

### 密码模式

![](https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/202401220043807.png)

### 授权码模式

![](https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/202401220043935.png)

### 客户端模式&RefreshToken模式

类似密码模式，只是请求内容变了 暂时用不到就懒得画了...

## 认证切面

基于认证拦截注解对controller层进行拦截
