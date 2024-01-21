# SpirngCloud项目开发脚手架食用指南

@author wzf

@date 2024-01-18

## 脚手架简介

> 开发背景

对原始脚手架进行模块拆分，代码封装，配置抽取，使用gradle替换maven

将各核心功能配置(mybatis-plus,redis,spring-web等)拆分成独立模块，归属于核心core模块下，将拓展功能提取到ext模块下，以实现代码分离解耦的目的，
简化业务功能的代码和配置。

原脚手架地址: https://github.com/wzf0125/springboot-archetype

新单体脚手架: https://github.com/wzf0125/Springboot-starter

> 脚手架模块依赖关系

| 核心组件                 | 版本            |
|----------------------|---------------|
| Java                 | 11            |
| Spring Cloud Alibaba | 2021.0.5.0    |
| Spring Cloud         | 2021.0.5      |
| SpringBoot           | 2.7.5         |
| Mybatis-plus         | 3.5.3.2       |
| Gateway              | 3.1.4         |
| Security             | 5.7.4         |
| Security-OAuth       | 2.5.2.RELEASE |
| Security-jwt         | 1.1.1.RELEASE |
| Swagger              | 2.3.0         |
| knif4j               | 3.0.3         |
| nacos                | 2.3.0         |
| hutool               | 5.8.5         |

## 脚手架模块

> `Core`模块

- `starter-auth` 认证封装
    - [x] 认证注解
    - [x] 认证切面
- `starter-base` 核心模块
    - [x] 所有starter的共用代码，通用工具封装
- `starter-feign`
    - [x] feign认证传递切面
- `starter-log`
    - [x] 日志切面
    - [x] 日志切面注解
    - [x] 日志配置抽取
- `starter-mybatis`
    - [x] mybatis-plus配置
- `starter-redis`
    - [x] redisTemplate配置
    - [x] redisTemplate操作封装
    - [x] SpringCache封装
    - [x] redisson配置
- `starter-tool`
    - [x] 
- `starter-web`
    - [x] MVC拦截器配置
    - [x] 拦截路径配置抽取
    - [x] 自定义权限拦截器
    - [x] 统一响应配置
    - [x] 全局异常拦截

> `Ext`模块

- [x] swagger模块
    - [x] swagger配置
- [x] mail邮件模块
- [ ] 对象存储模块
    - [X] 腾讯云COS
    - [X] 阿里云OOS
    - [ ] 亚马逊云AWS
    - [ ] minio
- [ ] RabbitMQ模块
    - rabbitMQ工具封装
- [ ] Elasticsearch模块
    - es工具封装

> `system`模块

- [x] swagger模块
    - [x] 所有模块聚合文档
- [x] user模块
    - [x] core
        - [x] 角色管理
        - [x] 权限管理
        - [x] 用户管理
    - [x] api
        - [x] feign接口

> `Gateway`网关

- [x] IP拦截器
- [x] 路径拦截
- [x] 认证拦截
- [ ] 接口限流

## 食用指南

### core模块

#### stater-base

- 核心模块
- 工具类
- 常量定义
- 共享依赖

#### starter-mybatis

- mybatis-plus配置

#### stater-redis

- redis配置
- redisTempalte封装
- SpringCache封装
- redisson封装

#### starter-web

- webMVC配置
- webMVC配置抽取
    - 具体看WebMvcConfigProperties
        - ~~抽取放行路径配置~~(微服务路径控制放在网关控制)
        - ~~抽取拦截路径配置~~(微服务路径控制放在网关控制)
        - 抽取资源路径映射配置
- 响应状态码定义
- 全局统一响应类
- ~~认证拦截器~~抽取为core-starter-auth 认证切面
- 全局异常拦截器
    - 拦截各类异常
    - 自定义业务异常
- 系统配置抽取
    - debug模式开关(全局异常拦截是否返回异常信息)

#### starter-log

- 日志切面注解
- 日志切面
- 日志配置抽取

#### starter-auth

认证切面

jwt校验和权限控制

通过权限拦截注解拦截注解修饰方法，判断用户是否具权限列表中的某种权限

拦截优先级

方法 > 类

优先处理方法注解，若想放行

### ext 拓展模块

#### swagger

- swagger + knif4j配置
    - swagger地址: http://127.0.0.1:8081/swagger-ui/index.html
    - knif4j地址: http://127.0.0.1:8081/doc.html (一般用这个)

#### mail

- 邮件功能封装
    - 责任链模式处理下发
    - 提供异步/同步功能支持
    - 提供

### common

- 通用模块

- BaseController
    - 附带日志切面注解，继承此类即可被日志切入
        - 默认Request Log + Response Log
    - Token解析，可通过getUid / getRole方法获取用户id/角色/权限列表(解析jwt)
        - 用户信息在认证拦截器处放入
        - 需要其他信息可修改认证拦截器配置和BaseController代码

### Auth模块

认证中心

SpringSecurity + OAuth2 + jwt

提供密码模式、授权码模式、客户端模式、RefreshToken模式认证功能

具体介绍见:[认证中心文档](./auth/README.md)

### System模块

#### swagger模块

swagger接口文档服务

聚合所有服务的接口文档

使用指南:

按照以下顺序启动服务

- gateway
- 业务模块
- swagger模块

访问: http://xxxx/swagger/doc.html

具体见:[Swagger模块介绍](./system/swagger/README.md)

#### 用户服务

> user-core

用户模块实现

基于RBAC实现用户模块

提供用户信息，权限管理，角色管理功能

> user-api

用户模块接口

提供用户模块feign调用接口

## 快速开始

### 基础准备

环境准备:

- `mysql`:8.x
- `redis`:7.x
- `nacos`:2.x
- `gradle`:8.x
- `Docker(可选)`: 部署使用

### 配置

#### nacos配置

访问nacos xxx.xxx.xxx.xxx:8848/nacos

> 创建命名空间

推荐创建dev/test/prod 分别对应开发/测试/线上环境

![](https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/202401212037638.png)

> 创建配置

配置文件命名规则: 文件名称-环境.文件类型

例: application-dev.yaml

需要创建以下配置文件

- application-环境.yaml
    - 通用配置
    - [模板](./common/src/main/resources/application-nacos.yaml)
- gateway-dev.yaml
    - gateway专属配置
    - [模板](./gateway/src/main/resources/application-nacos.yaml)
- user-dev.yaml
    - 用户模块配置
    - [模板](./system/user-core/src/main/resources/application-nacos.yaml)
- swagger-dev.yaml
    - swagger服务配置
    - [模板](./system/swagger/src/main/resources/application-nacos.yaml)
- auth-dev.yaml
    - 认证服务配置
    - [模板](./auth/auth-core/src/main/resources/application-nacos.yaml)

![](https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/202401212041504.png)

修改所有模块的bootstrap-dev.yaml为你的nacos配置

#### 数据库配置

执行[SQL文件](./sql/user.sql)

![](https://974500760-1303995467.cos.ap-guangzhou.myqcloud.com/PicGo/202401220031244.png)

```yaml
spring:
  application:
    name: app-gateway # 应用名称
  cloud:
    nacos:
      # nacos注册中心配置
      server-addr: xxx.xxx.xxx.xxx:8848 # 注册中心地址(注意9848(nacos端口号+1000)端口也需要开放 )
      username: nacos # 账号
      password: nacos # 密码
      # 注册中心的namespace
      discovery:
        namespace: 3bb01079-490a-4014-a5c7-5fbf51cab92a
      # 配置中心配置 核心文件配置
      config:
        server-addr: xxx.xxx.xxx.xxx:8848
        namespace: 3bb01079-490a-4014-a5c7-5fbf51cab92a
        username: nacos # 账号
        password: nacos # 密码
        refresh-enabled: true
        # 主配置
        prefix: application
        file-extension: yaml
        # 拓展配置
        extension-configs:
          - data-id: gateway-dev.yaml # 模块专属配置
            group: gateway # 配置所属组
            refresh: true
```

### 构建

构建插件`apply plugin: 'java-library'`

注意: 非构建模块引入此插件会出现自动创建src/main src/test模块

### 新模块

1. 在build.gradle中定义模块类型

```
ext {
    // 所有项目模块
    allProjectList = Arrays.asList(
            project(":core"),
            project(":ext"),
            project(":common"),
            project("demo"),
            project("demo-api")
    ).toSet()
    allProjectList.addAll(project(":core").subprojects)
    allProjectList.addAll(project(":ext").subprojects)

    // 空父工程
    pomProjectList = Arrays.asList(
            project(":core"),
            project(":ext")
    )

    // 需要打包的模块
    packageProjcetList = Arrays.asList(
            project(":demo")
    )
}
```

2. 在`settings.gradle`中注册该模块(若不存在父模块的情况)

```
rootProject.name = 'starter'

def moduleList = ['common', 'core', 'ext', 'demo', 'demo-api']
moduleList.forEach { module ->
    {
        include module
        file("${rootDir}/" + module).eachDirMatch(~/.*/) {
            include module + ":${it.name}"
        }
    }
}
```

打包成jar包使用gradle bootJar功能
