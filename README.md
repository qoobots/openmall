# OpenMall B2B2C 电商平台

基于 Spring Boot 3.5.10 + JDK 21 + Thymeleaf + MySQL 的开源B2B2C电商平台系统

## 项目简介

OpenMall 是一个功能完善的 B2B2C 电商平台，采用前后端不分离架构，使用 Thymeleaf 模板引擎渲染页面。系统模仿天猫商城的B2B2C模式，为用户提供完整的电商解决方案。

## 技术栈

### 后端技术
- **Spring Boot**: 3.5.10
- **Spring Security**: 6.2.0
- **Spring MVC**: Web层框架
- **Spring Data JPA**: 持久层框架
- **Thymeleaf**: 3.1.1.RELEASE (模板引擎)
- **MySQL**: 8.0+
- **Redis**: 缓存、会话管理
- **JDK**: 21 (启用Virtual Threads特性)

### 前端技术
- **Bootstrap 5**: UI框架
- **jQuery**: JavaScript库
- **SweetAlert2**: 弹窗组件
- **FontAwesome**: 图标库

### 工具库
- **Lombok**: 1.18.42
- **Hutool**: 5.8.25
- **Apache Commons**: Lang3、Collections4、IO

## 项目结构

```
openmall/
├── openmall-common/              # 公共模块
│   ├── common-core/             # 核心工具类、常量、枚举
│   ├── common-domain/           # 通用领域对象和实体
│   ├── common-security/         # 安全认证模块
│   └── common-web/              # Web通用配置
│
├── openmall-portal/              # 前台商城模块（买家端）
│   ├── controller/              # 控制器层
│   ├── service/                 # 服务层
│   ├── repository/              # 数据访问层
│   ├── resources/
│   │   ├── templates/           # Thymeleaf模板文件
│   │   └── static/              # 静态资源
│   └── PortalApplication.java    # 启动类
│
├── openmall-merchant/            # 商家后台模块（卖家端）
│   ├── controller/              # 控制器层
│   ├── service/                 # 服务层
│   ├── repository/              # 数据访问层
│   ├── resources/
│   │   ├── templates/           # Thymeleaf模板文件
│   │   └── static/              # 静态资源
│   └── MerchantApplication.java
│
├── openmall-platform/           # 平台管理模块（管理员端）
│   ├── controller/              # 控制器层
│   ├── service/                 # 服务层
│   ├── repository/              # 数据访问层
│   ├── resources/
│   │   ├── templates/           # Thymeleaf模板文件
│   │   └── static/              # 静态资源
│   └── PlatformApplication.java
│
└── db/                          # 数据库脚本
    └── schema.sql               # 数据库初始化脚本
```

## 核心功能

### 前台商城模块 (openmall-portal)
- 首页展示
- 商品列表、详情、搜索
- 购物车管理
- 订单下单、支付、管理
- 用户中心、收货地址管理
- 商品收藏、浏览记录
- 用户注册、登录

### 商家后台模块 (openmall-merchant)
- 控制台数据统计
- 商品管理（SPU/SKU）
- 订单管理、发货
- 店铺管理、装修
- 营销活动（优惠券、满减、限时折扣）
- 数据统计报表

### 平台管理模块 (openmall-platform)
- 用户管理（买家、商家）
- 商家审核、管理
- 分类、品牌管理
- 平台订单查看
- 系统设置、权限管理

## 快速开始

### 环境要求
- JDK 21+
- Maven 4.0.0+
- MySQL 8.0+
- Redis 6.0+

### 1. 克隆项目

```bash
git clone https://github.com/qoobot/openmall.git
cd openmall
```

### 2. 初始化数据库

```bash
# 创建数据库并导入初始数据
mysql -u root -p < db/schema.sql
```

### 3. 修改配置

修改各模块的 `application.yml` 文件，配置数据库和 Redis 连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/openmall?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false
    username: root
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password
```

### 4. 编译项目

```bash
mvn clean install -DskipTests
```

### 5. 启动应用

分别启动三个模块：

```bash
# 启动前台商城
cd openmall-portal
mvn spring-boot:run

# 启动商家后台
cd openmall-merchant
mvn spring-boot:run

# 启动平台管理
cd openmall-platform
mvn spring-boot:run
```

### 6. 访问系统

- 前台商城: http://localhost:8081
- 商家后台: http://localhost:8082/merchant
- 平台管理: http://localhost:8083/platform

### 默认账号

**平台管理员**
- 用户名: admin
- 密码: admin123

**测试买家**
- 用户名: buyer1
- 密码: admin123

**测试商家**
- 用户名: merchant1
- 密码: admin123

## 开发指南

### 代码规范

遵循以下代码规范：
- Controller方法命名规范
- Service接口定义规范
- 实体类继承BaseEntity
- 使用Lombok简化代码
- 统一使用Result返回结果

### 提交代码

```bash
git add .
git commit -m "feat: 添加新功能"
git push origin main
```

## 许可证

[Apache License 2.0](LICENSE)

## 联系方式

- 官网: https://github.com/qoobot/openmall
- 邮箱: support@openmall.com

## 致谢

感谢所有为 OpenMall 做出贡献的开发者！
