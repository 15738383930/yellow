**项目说明**
- yellow是一个开箱即用，前后端分离的Java敏捷开发平台，能快速开发项目并交付，可扩展、可维护程度高。
- 支持MySQL、Oracle、SQL Server、PostgreSQL等主流数据库
- 前端地址：https://gitee.com/xuan_zheng/yellow-web
- 代码生成器：https://gitee.com/xuan_zheng/yellow/tree/master/renren-generator

<br>


**具有如下特点**
- 友好的代码结构及注释，便于阅读及二次开发
- 实现前后端分离，通过token进行数据交互，前端再也不用关注后端技术
- 灵活的权限控制，可控制到页面或按钮，满足绝大部分的权限需求
- 页面交互使用Vue2.x，极大的提高了开发效率
- 完善的代码生成机制，可在线生成entity、xml、dao、service、vue、sql代码，减少70%以上的开发任务
- 引入API模板，根据token作为登录令牌，极大的方便了APP接口开发
- 引入Hibernate Validator校验框架，轻松实现后端校验
- 引入swagger文档支持，方便编写API接口文档
  <br>

**项目结构**
```
yellow
├─yellow-common 公共模块
│  ├─entity 全局通用数据实体
│  │ ├─code 字典枚举（可实现字典规范）
│  │ ├─request 通用数据请求
│  │ └─response 通用数据响应（包含响应代码枚举，其他模块可基础该模块，实现高扩展、可维护的响应模型）
│  ├─exception 异常处理（异常拦截细粒度高，拦截顺序：自定义异常->第三方异常->未知异常）
│  └─xss XSS过滤组件
│ 
├─yellow-api 功能接口模块
│  ├─aspect 切面（日志、认证等）
│  ├─task 定时任务
│  ├─config api配置（Kaptcha、mybatisplus、stars-datachange、security）
│  ├─rsa 数据加密（token、 私钥zh.keystore）
│  ├─security 安全模块
│  ├─log 日志模块（登录、操作、异常等）
│  ├─RBAC 用户、角色、菜单等模块（动态授权，细粒化到接口/按钮级别，可加2行代码实现数据级授权）
│  ├─doc 接口文档 
│  ├─data-model 数据模型（提供动态数据转换功能，基于stars-datachange） 
│  ├─dictionary 字典
│  ├─cache 缓存（基于Redis） 
│  └─autoconfigure 自动配置属性
│  
└─renren-generator 代码生成器 

```
<br> 

**如何交流、反馈、参与贡献？**
- Git仓库：https://gitee.com/xuan_zheng/yellow
- QQ群：142713860
- 如需关注项目最新动态，请Watch、Star项目，同时也是对项目最好的支持
<br>

**技术选型：**
- 核心框架：Spring Boot 2.7
- 安全框架：Spring Security 5.7
- 视图框架：Spring MVC 5.3
- 持久层框架：Mybatis Plus 3.3
- 缓存框架：Redis
- 数据库连接池：Druid 1.1.10
- 日志管理：logback
- 页面交互：Vue2.x
  <br>

**后端部署**
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库yellow，数据库编码为UTF-8
- 执行yellow-api/resources/sql/yellow.sql文件，初始化数据
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行ApiApplication.java，则可启动项目
- 接口文档地址：http://localhost:11100/yellow/doc.html
<br> 

**前端部署**
- 本项目是前后端分离的，还需要部署前端，才能运行起来
- 前端下载地址：https://gitee.com/xuan_zheng/yellow-web
- npm install
- npm run dev
- 前端部署完毕，就可以访问项目了，账号：zhouhao，密码：Yellow$123$Yellow
 <br>

**效果图** <br>
[点击查看](https://gitee.com/xuan_zheng/yellow-web/blob/master/README.md)
<br>

**版本更新说明** <br>
- v1.0：完成了基本的系统管理大模块，基于Spring Security的认证授权功能等；
<br>