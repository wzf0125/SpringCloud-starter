# SWAGGER模块

## 作用

聚合所有模块接口文档

实现方式: 通过`com.github.xiaoymin:knife4j-aggregation-spring-boot-starter`库

在配置中编写其他模块的接口文档地址，搭配gateway做模块转发，实现模块接口文档聚合

## 使用方式

### 查看文档

访问: `http://loaclhost/swagger/doc.html`

### 新增文档

在配置中新增routes配置

- name: 页面上显示的文档名称(可以中文)
- uri: 接口文档地址
- location: 后缀

这里是利用gateway转发机制，也可以直接配置端口

这里是nacos里的配置

```yaml
server:
  port: 8100
knife4j:
  enableAggregation: true
  cloud:
    enable: true
    # 这里
    routes:
      - name: demo
        uri: localhost
        location: /app-demo/v3/api-docs?group=quanta
      - name: user
        uri: localhost
        location: /app-system-user/v3/api-docs?group=quanta
```
