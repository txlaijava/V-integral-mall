#server-integral 积分商城系统

### 组织结构

```
server-integral
    ├── server-integral-common -- 框架公共模块
    ├── server-integral-dao -- 代码生成模块，无需开发
    ├── server-integral-server -- 公共接口模块
    └── server-inegral-platform- -- 积分平台端
```
### 技术选型

#### 后端技术:
技术 | 名称 | 官网
----|------|----
Spring Boot | 框架  | [http://projects.spring.io/spring-boot/](http://projects.spring.io/spring-boot/)
Spring Data Jpa | 数据访问  | [http://projects.spring.io/spring-data-jpa/](http://projects.spring.io/spring-data-jpa/)
Druid | 数据库连接池  | [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
Velocity | 模板引擎  | [http://velocity.apache.org/](http://velocity.apache.org/)
Log4J | 日志组件  | [http://logging.apache.org/log4j/1.2/](http://logging.apache.org/log4j/1.2/)
Swagger2 | 接口测试框架  | [http://swagger.io/](http://swagger.io/)
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)

### 框架规范约定

约定优于配置(convention over configuration)，此框架约定了很多编程规范，下面一一列举：

```
- service类，需要在叫名`service`的包下，并以`Service`结尾，如`UserServiceImpl`

- controller类，需要在以`controller`结尾的包下，类名以Controller结尾，如`UserController.java`，并继承`BaseController`

- spring task类，需要在叫名`task`的包下，并以`Task`结尾，如`TestTask.java`

- model实体类，需要在名叫`model`的包下，命名规则为数据表转驼峰规则，如`UserLog.java`

- 类名：首字母大写驼峰规则；方法名：首字母小写驼峰规则；常量：全大写；变量：首字母小写驼峰规则，尽量非缩写

- 配置文件放到`src/main/resources`目录下

- 静态资源文件放到`src/main/resources/META-INF/resources/`目录下

- html文件，需要在`src/main/resources/templates`目录下

- 更多规范，参考[[阿里巴巴Java开发手册] http://git.oschina.net/shuzheng/zheng/attach_files

```

#### 开发工具:
- MySql: 数据库
- IntelliJ IDEA: 开发IDE
- Navicat for MySQL: 数据库客户端

#### 开发环境：
- Jdk8+
- Mysql5.5+
- Redis

### 常见问题

- Spring Boot 自定义注解 "HandlerMethodArgumentResolver" 实现下注入Dao异常. [参考](https://segmentfault.com/q/1010000009440217)

### 工具

- [IntelliJ IDEA 常用设置讲解](http://www.phperz.com/article/15/0923/159043.html "IntelliJ IDEA 常用设置讲解")

### 在线小工具

- [文本到ASCII艺术生成器](http://patorjk.com/software/taag "文本到ASCII艺术生成器")

- [在线Cron表达式生成器](http://cron.qqe2.com/ "在线Cron表达式生成器")