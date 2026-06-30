# OpenMall 常见问题 (FAQ)

## 1. 文档信息

| 属性 | 内容 |
|------|------|
| 文档名称 | OpenMall 常见问题 |
| 版本号 | v1.0.0 |
| 创建日期 | 2026-06-30 |

## 2. 环境与安装

### Q: 项目需要什么运行环境？
A: JDK 21+、Maven 4.0.0+、MySQL 8.0+、Redis 6.0+。

### Q: 如何快速启动项目？
A: 参考 README.md 中的快速开始部分：
```bash
git clone https://github.com/qoobot-com/openmall.git
cd openmall
mysql -u root -p < db/schema.sql
mvn clean install -DskipTests
cd openmall-portal && mvn spring-boot:run
```

### Q: 编译报错怎么办？
A: 尝试清理 Maven 缓存后重新编译：
```bash
mvn clean
mvn install -U
```

### Q: 数据库连接失败？
A: 检查 MySQL 服务是否启动，以及 application.yml 中的数据库连接配置是否正确。

### Q: Redis 连接失败？
A: 检查 Redis 服务是否启动：`redis-cli ping`

## 3. 功能使用

### Q: 默认账号密码是什么？
A: 
| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | admin123 |
| 买家 | buyer1 | admin123 |
| 商家 | merchant1 | admin123 |

### Q: 如何申请成为商家？
A: 注册买家账号后，在个人中心申请成为商家，填写公司信息后等待平台审核。

### Q: 如何添加商品？
A: 商家登录后，进入"商品管理" → "发布商品"，填写商品信息并提交。

### Q: 订单状态有哪些？
A: 0=待付款, 1=待发货, 2=待收货, 3=已完成, 4=已取消, 5=售后中。

### Q: 支持哪些支付方式？
A: 当前预留了支付宝、微信支付、银行卡三种支付方式接口，具体对接需自行实现。

## 4. 开发相关

### Q: 项目采用什么架构？
A: 前后端不分离的 MVC 架构，使用 Thymeleaf 模板引擎渲染页面。

### Q: 数据库表结构在哪里？
A: 在 `db/schema.sql` 文件中，详细的表设计见 `docs/architecture/02-数据库设计.md`。

### Q: 如何添加新的 API 接口？
A: 在对应模块的 controller 包下添加新的 Controller 类，遵循 RESTful 风格。

### Q: 如何添加新的实体类？
A: 在 `openmall-common/common-domain/entity` 下创建，继承 `BaseEntity`。

### Q: 代码规范是什么？
A: 详见 `docs/development/01-编码规范.md`。

## 5. 部署相关

### Q: 如何部署到生产环境？
A: 支持 JAR 包部署和 Docker 部署，详见 `docs/deployment/01-部署指南.md`。

### Q: 如何配置 HTTPS？
A: 在 Nginx 中配置 SSL 证书，将请求转发到后端应用。

### Q: 如何做数据库备份？
A: `mysqldump -u root -p openmall > backup.sql`

### Q: 如何监控系统运行状态？
A: 使用 `/actuator/health` 端点进行健康检查。

## 6. 性能优化

### Q: 如何提升系统并发能力？
A: 项目已启用 JDK 21 Virtual Threads，可通过增大连接池、使用 Redis 缓存进一步优化。

### Q: 商品列表加载慢怎么办？
A: 
1. 确认数据库已建立索引
2. 启用 Redis 缓存热点商品数据
3. 使用分页查询避免一次性加载过多数据

### Q: 如何配置 Redis 缓存？
A: 在 application.yml 中配置 Redis 连接信息，Service 层使用 `@Cacheable` 注解。

## 7. 版本历史

| 版本 | 日期 | 作者 | 变更说明 |
|------|------|------|----------|
| v1.0.0 | 2026-06-30 | - | 初稿 |
