# OpenMall 项目概览

## 项目简介

OpenMall 是一个基于 Spring Boot 3.5.10、JDK 21、Thymeleaf、MySQL 的 B2B2C 开源电商平台系统。项目采用前后端不分离架构，使用 Thymeleaf 模板引擎渲染页面，模仿天猫商城的 B2B2C 模式。

## 架构设计

### 技术架构

```
┌─────────────────────────────────────────────────────────────┐
│                         前端层                                │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │ 前台商城     │  │ 商家后台     │  │ 平台管理     │          │
│  │ (Thymeleaf) │  │ (Thymeleaf) │  │ (Thymeleaf) │          │
│  │ Bootstrap 5 │  │ Bootstrap 5 │  │ Bootstrap 5 │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
                              ▲
                              │
┌─────────────────────────────────────────────────────────────┐
│                         控制器层                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │PortalContr. │  │MerchantCtrl │  │PlatformCtrl │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
                              ▲
                              │
┌─────────────────────────────────────────────────────────────┐
│                         服务层                                │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │UserService  │  │ProductSrv   │  │OrderService │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
                              ▲
                              │
┌─────────────────────────────────────────────────────────────┐
│                      数据访问层 (JPA)                         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐          │
│  │UserReposit. │  │ProductRepo  │  │OrderRepo    │          │
│  └─────────────┘  └─────────────┘  └─────────────┘          │
└─────────────────────────────────────────────────────────────┘
                              ▲
                              │
┌─────────────────────────────────────────────────────────────┐
│                         数据存储层                             │
│  ┌─────────────┐              ┌─────────────┐              │
│  │   MySQL     │              │    Redis     │              │
│  │ (持久化数据) │              │   (缓存)     │              │
│  └─────────────┘              └─────────────┘              │
└─────────────────────────────────────────────────────────────┘
```

### 模块划分

#### 1. 公共模块 (openmall-common)

```
openmall-common/
├── common-core/           # 核心工具类、常量、枚举
│   ├── enums/             # 枚举类
│   │   ├── UserType.java  # 用户类型枚举
│   │   ├── OrderStatus.java
│   │   └── PayType.java
│   ├── constant/          # 常量类
│   │   └── RedisConstants.java
│   └── result/            # 统一响应结果
│       ├── Result.java
│       └── PageResult.java
├── common-domain/         # 领域对象
│   └── entity/            # 实体类
│       ├── BaseEntity.java
│       ├── User.java
│       ├── ProductSpu.java
│       └── ...
├── common-security/       # 安全认证
└── common-web/            # Web配置
```

#### 2. 前台商城模块 (openmall-portal)

```
openmall-portal/
├── controller/            # 控制器
│   ├── IndexController.java
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── CartController.java
│   └── OrderController.java
├── service/               # 服务
│   ├── UserService.java
│   ├── ProductService.java
│   └── ...
├── repository/            # 数据访问
│   ├── UserRepository.java
│   ├── ProductSpuRepository.java
│   └── ...
├── dto/                   # 数据传输对象
├── vo/                    # 视图对象
├── config/                # 配置类
└── resources/
    ├── templates/         # Thymeleaf模板
    └── static/            # 静态资源
```

#### 3. 商家后台模块 (openmall-merchant)

```
openmall-merchant/
├── controller/            # 商家控制器
│   ├── MerchantDashboardController.java
│   ├── MerchantProductController.java
│   ├── MerchantOrderController.java
│   └── ...
├── service/               # 商家服务
├── repository/            # 数据访问
└── resources/
    ├── templates/         # 商家后台模板
    └── static/            # 静态资源
```

#### 4. 平台管理模块 (openmall-platform)

```
openmall-platform/
├── controller/            # 平台控制器
│   ├── PlatformDashboardController.java
│   ├── PlatformUserController.java
│   ├── PlatformMerchantController.java
│   └── ...
├── service/               # 平台服务
├── repository/            # 数据访问
└── resources/
    ├── templates/         # 平台管理模板
    └── static/            # 静态资源
```

## 核心功能

### 前台商城功能

| 功能模块 | 说明 |
|---------|------|
| 用户中心 | 注册、登录、个人信息管理 |
| 商品浏览 | 商品列表、详情、搜索、分类浏览 |
| 购物车 | 添加商品、数量修改、删除、选择结算 |
| 订单管理 | 下单、支付、订单列表、订单详情 |
| 收货地址 | 地址列表、新增、编辑、删除、设置默认 |
| 商品收藏 | 收藏商品、取消收藏 |
| 浏览记录 | 查看浏览历史 |

### 商家后台功能

| 功能模块 | 说明 |
|---------|------|
| 控制台 | 数据统计、待处理事项 |
| 商品管理 | SPU/SKU管理、上架下架、库存管理 |
| 订单管理 | 订单列表、订单详情、发货处理 |
| 店铺管理 | 店铺信息、店铺装修 |
| 营销管理 | 优惠券、满减活动、限时折扣 |
| 数据统计 | 销售统计、商品统计、访客统计 |

### 平台管理功能

| 功能模块 | 说明 |
|---------|------|
| 控制台 | 平台数据统计、待处理事项 |
| 用户管理 | 买家管理、商家管理 |
| 商家审核 | 商家入驻审核、商家信息审核 |
| 分类管理 | 商品分类管理 |
| 品牌管理 | 品牌管理 |
| 订单管理 | 全平台订单查看、订单仲裁 |
| 系统设置 | 菜单管理、角色管理、参数配置 |

## 数据库设计

### 核心表结构

| 表名 | 说明 |
|-----|------|
| user | 用户表 |
| merchant | 商家表 |
| shop | 店铺表 |
| category | 商品分类表 |
| brand | 品牌表 |
| product_spu | 商品SPU表 |
| product_sku | 商品SKU表 |
| order_master | 订单主表 |
| order_item | 订单明细表 |
| cart_item | 购物车表 |
| user_address | 用户收货地址表 |
| coupon | 优惠券表 |
| user_favorite | 商品收藏表 |

## 技术特点

### 1. Spring Boot 3.5.10
- 最新稳定版本
- 自动配置简化开发
- 内置Tomcat服务器
- 丰富的Starter依赖

### 2. JDK 21 Virtual Threads
- 轻量级虚拟线程
- 提高并发性能
- 简化异步编程

### 3. Spring Security
- 完善的安全认证
- 基于角色的权限控制
- 密码加密存储
- CSRF防护

### 4. Spring Data JPA
- 简化数据访问
- 自动生成SQL
- 支持复杂查询
- 乐观锁支持

### 5. Thymeleaf
- 服务端渲染
- 自然模板
- 与Spring无缝集成
- 布局复用

### 6. Redis缓存
- 热点数据缓存
- 会话管理
- 分布式锁
- 提高系统性能

## 开发规范

### 命名规范

| 类型 | 规范 | 示例 |
|-----|------|-----|
| 类名 | 大驼峰 | UserService |
| 方法名 | 小驼峰 | getUserById |
| 变量名 | 小驼峰 | userName |
| 常量名 | 全大写下划线 | USER_SESSION_KEY |
| 数据库表名 | 小写下划线 | user_address |
| 数据库字段名 | 小写下划线 | user_id |

### 代码规范

1. **Controller层**
   - 方法命名要语义清晰
   - 返回String返回视图名称
   - 返回@ResponseBody的返回JSON数据

2. **Service层**
   - 接口定义业务逻辑方法
   - 实现类添加@Service注解
   - 使用@Transactional管理事务

3. **Repository层**
   - 继承JpaRepository
   - 按需自定义查询方法

4. **实体类**
   - 继承BaseEntity
   - 使用Lombok简化代码
   - 添加JPA注解

## 部署架构

```
                    ┌─────────────┐
                    │   Nginx     │
                    │ (反向代理)   │
                    └─────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
┌───────▼───────┐  ┌──────▼──────┐  ┌────────▼─────┐
│   Portal     │  │  Merchant   │  │   Platform   │
│   :8081      │  │  :8082      │  │   :8083      │
└──────────────┘  └─────────────┘  └──────────────┘
        │                  │                  │
        └──────────────────┼──────────────────┘
                           │
              ┌────────────▼────────────┐
              │       MySQL :3306       │
              └─────────────────────────┘

              ┌────────────▼────────────┐
              │       Redis :6379       │
              └─────────────────────────┘
```

## 性能优化

### 1. 数据库优化
- 合理创建索引
- 使用连接池
- 优化SQL查询
- 分页查询

### 2. 缓存优化
- Redis缓存热点数据
- 合理设置过期时间
- 多级缓存

### 3. 应用优化
- 启用虚拟线程
- 异步处理
- 连接池配置

## 扩展性设计

### 1. 模块化设计
- 各模块独立部署
- 公共模块复用
- 清晰的职责划分

### 2. 接口预留
- 预留支付接口
- 预留物流接口
- 预留第三方登录接口

### 3. 配置化
- 外部化配置
- 动态配置更新
- 环境隔离

## 安全设计

### 1. 认证安全
- 密码BCrypt加密
- Session管理
- 记住我功能

### 2. 接口安全
- CSRF防护
- XSS防护
- SQL注入防护

### 3. 数据安全
- 敏感信息脱敏
- 数据备份
- 审计日志

## 后续规划

### 第一阶段
- [x] 基础框架搭建
- [x] 用户认证模块
- [x] 商品管理模块
- [ ] 购物车完整功能
- [ ] 订单完整功能

### 第二阶段
- [ ] 支付集成
- [ ] 物流对接
- [ ] 搜索功能优化
- [ ] 推荐系统

### 第三阶段
- [ ] 微服务拆分
- [ ] 消息队列集成
- [ ] 分布式事务
- [ ] 容器化部署

## 联系方式

- 项目地址: https://github.com/qoobot-com/openmall
- 文档地址: https://github.com/qoobot-com/openmall/wiki
- 问题反馈: https://github.com/qoobot-com/openmall/issues

## 许可证

Apache License 2.0
