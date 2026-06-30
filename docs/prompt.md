# 开源商城平台系统 - 项目开发提示词（前后端不分离架构）

## 项目概述

你是一名资深Java全栈架构师，负责设计和开发一个基于**Spring Boot 3.5.10、JDK 21、Maven、Thymeleaf、MySQL**等技术的**B2B2C开源商城平台系统**。本项目采用**前后端不分离架构**，前端页面使用Thymeleaf模板引擎渲染，放在各模块的 `resources/templates` 目录下。本项目模仿天猫商城的B2B2C模式及主要功能，为用户提供完整的电商解决方案。

---

## 一、技术栈要求

### 1. 核心框架
- **Spring Boot**: 3.5.10
- **Spring Security**: 6.2.0
- **Spring MVC**: Web层框架
- **Spring Data JPA**: 持久层框架

### 2. 开发环境
- **JDK**: 21 (启用Virtual Threads特性)
- **Maven**: 4.0.0+
- **编码**: UTF-8

### 3. 数据库与持久层
- **MySQL**: 8.0+
- **Spring Data JPA**: ORM框架
- **QueryDSL**: 类型安全的查询（可选）
- **HikariCP**: 数据库连接池

### 4. 模板引擎
- **Thymeleaf**: 3.1.1.RELEASE (前端页面渲染)

### 5. 前端技术
- **Bootstrap 5**: UI框架
- **jQuery**: JavaScript库
- **SweetAlert2**: 弹窗组件
- **Dropzone**: 文件上传
- **KindEditor/TinyMCE**: 富文本编辑器
- **Layer**: 弹出层组件

### 6. 缓存与消息
- **Redis**: 缓存、会话管理
- **Spring Cache**: 缓存抽象

### 7. 工具库
- **Lombok**: 1.18.42
- **Hutool**: 5.8.25（Java工具类库）
- **Apache Commons**: Lang3、Collections4、IO

### 8. 构建工具
- Maven插件：compiler、surefire、failsafe、jacoco
- 代码质量要求：单元测试覆盖率 ≥ 80%

---

## 二、项目架构设计

### 1. 整体架构模式

采用**前后端不分离的单体架构** + **模块化设计**，分为前台商城模块和后台管理模块。

```
openmall/
├── openmall-common/              # 公共模块
│   ├── common-core/             # 核心工具类、常量、枚举
│   ├── common-domain/           # 通用领域对象和实体
│   ├── common-security/         # 安全认证模块
│   └── common-web/              # Web通用配置（拦截器、过滤器等）
│
├── openmall-portal/              # 前台商城模块（买家端）
│   ├── controller/              # 控制器层
│   ├── service/                 # 服务层
│   ├── repository/              # 数据访问层
│   ├── resources/
│   │   ├── templates/           # Thymeleaf模板文件
│   │   │   ├── layout/          # 布局模板
│   │   │   ├── index/           # 首页
│   │   │   ├── product/         # 商品相关页面
│   │   │   ├── cart/            # 购物车页面
│   │   │   ├── order/           # 订单相关页面
│   │   │   ├── user/            # 用户中心页面
│   │   │   └── search/          # 搜索页面
│   │   ├── static/              # 静态资源
│   │   │   ├── css/             # 样式文件
│   │   │   ├── js/              # JavaScript文件
│   │   │   └── images/         # 图片资源
│   │   └── application.yml      # 配置文件
│   └── PortalApplication.java    # 启动类
│
├── openmall-merchant/            # 商家后台模块（卖家端）
│   ├── controller/              # 控制器层
│   ├── service/                 # 服务层
│   ├── repository/              # 数据访问层
│   ├── resources/
│   │   ├── templates/           # Thymeleaf模板文件
│   │   │   ├── layout/          # 布局模板
│   │   │   ├── dashboard/       # 控制台
│   │   │   ├── product/         # 商品管理页面
│   │   │   ├── order/           # 订单管理页面
│   │   │   ├── shop/            # 店铺管理页面
│   │   │   └── statistics/      # 统计报表页面
│   │   ├── static/              # 静态资源
│   │   └── application.yml
│   └── MerchantApplication.java
│
├── openmall-platform/           # 平台管理模块（管理员端）
│   ├── controller/              # 控制器层
│   ├── service/                 # 服务层
│   ├── repository/              # 数据访问层
│   ├── resources/
│   │   ├── templates/           # Thymeleaf模板文件
│   │   │   ├── layout/          # 布局模板
│   │   │   ├── dashboard/       # 控制台
│   │   │   ├── user/            # 用户管理
│   │   │   ├── merchant/        # 商家管理
│   │   │   ├── category/        # 分类管理
│   │   │   ├── brand/           # 品牌管理
│   │   │   └── system/          # 系统设置
│   │   ├── static/              # 静态资源
│   │   └── application.yml
│   └── PlatformApplication.java
│
└── db/                          # 数据库脚本
    └── schema.sql               # 数据库初始化脚本
```

### 2. B2B2C业务模型

```
平台管理员（Platform Admin）
    ├── 管理商家（审核、禁用）
    ├── 管理用户（买家、商家）
    ├── 管理分类、品牌
    ├── 系统设置
    ├── 数据统计
    └── 售后仲裁

商家（Merchant）
    ├── 店铺管理
    ├── 商品发布（SPU/SKU）
    ├── 库存管理
    ├── 订单处理（发货）
    ├── 营销活动（优惠券、满减）
    └── 店铺数据统计

买家（Buyer）
    ├── 浏览商品
    ├── 搜索商品
    ├── 加入购物车
    ├── 下单支付
    ├── 订单管理
    ├── 收货评价
    ├── 退款/售后
    └── 个人中心
```

---

## 三、核心功能模块

### 1. 前台商城模块（openmall-portal）

#### 1.1 首页模块
- **首页布局**:
  - 顶部导航：分类导航、搜索框、登录/注册、购物车
  - 轮播图：热门活动、促销Banner
  - 推荐商品：精选商品、热门推荐
  - 分类楼层：按分类展示商品
  - 底部导航：帮助中心、关于我们

#### 1.2 商品模块
- **商品列表页** (`product/list.html`):
  - 分类筛选
  - 品牌筛选
  - 价格区间筛选
  - 排序方式（综合、销量、价格）
  - 分页显示

- **商品详情页** (`product/detail.html`):
  - 商品图片轮播
  - SKU规格选择（颜色、尺寸等）
  - 价格、库存显示
  - 商品详情（图文）
  - 商品评价列表
  - 收藏、加入购物车、立即购买

- **商品搜索页** (`search/index.html`):
  - 搜索框
  - 搜索结果列表
  - 热门搜索
  - 搜索历史

#### 1.3 购物车模块
- **购物车页** (`cart/index.html`):
  - 购物车商品列表
  - 数量增减
  - 删除商品
  - 选择商品结算
  - 优惠券选择
  - 金额计算

#### 1.4 订单模块
- **确认订单页** (`order/confirm.html`):
  - 收货地址选择/新增
  - 商品信息确认
  - 运费计算
  - 优惠券使用
  - 优惠金额显示
  - 实付金额

- **订单提交**: 选择支付方式

- **支付页** (`order/payment.html`):
  - 支付宝支付
  - 微信支付
  - 支付状态轮询

- **订单详情页** (`order/detail.html`):
  - 订单状态
  - 商品信息
  - 物流信息
  - 订单操作（取消订单、确认收货、申请售后）

- **我的订单页** (`order/list.html`):
  - 订单列表
  - 订单状态筛选（待付款、待发货、已发货、已完成）
  - 订单搜索

- **评价页** (`order/review.html`):
  - 星级评分
  - 文字评价
  - 图片上传
  - 追加评价

#### 1.5 用户中心模块
- **用户中心首页** (`user/index.html`):
  - 用户信息概览
  - 订单状态统计
  - 快捷入口

- **个人资料** (`user/profile.html`):
  - 昵称修改
  - 头像上传
  - 性别、生日修改
  - 手机号绑定

- **收货地址** (`user/address.html`):
  - 地址列表
  - 新增地址
  - 编辑地址
  - 设置默认地址
  - 删除地址

- **我的收藏** (`user/favorite.html`):
  - 收藏商品列表
  - 取消收藏
  - 加入购物车

- **浏览记录** (`user/history.html`):
  - 浏览历史列表
  - 清空历史

- **账号安全** (`user/security.html`):
  - 密码修改
  - 手机号修改
  - 登录设备管理

#### 1.6 售后模块
- **退款/退货申请** (`after-sale/apply.html`):
  - 选择订单商品
  - 填写退款原因
  - 上传凭证

- **售后列表** (`after-sale/list.html`):
  - 售后订单列表
  - 售后状态查看
  - 售后详情

#### 1.7 其他页面
- **登录页** (`auth/login.html`)
- **注册页** (`auth/register.html`)
- **关于我们** (`about/index.html`)
- **帮助中心** (`help/index.html`)

### 2. 商家后台模块（openmall-merchant）

#### 2.1 控制台
- **首页** (`dashboard/index.html`):
  - 今日数据概览（销售额、订单量、访客数）
  - 待处理订单
  - 库存预警商品
  - 数据图表（销售趋势、订单来源）

#### 2.2 商品管理
- **商品分类** (`product/category.html`):
  - 分类树形展示
  - 新增/编辑分类
  - 删除分类

- **商品列表** (`product/list.html`):
  - 商品列表展示
  - 商品上架/下架
  - 商品编辑
  - 商品删除

- **商品发布/编辑** (`product/edit.html`):
  - 基础信息（名称、副标题、分类、品牌）
  - 商品描述（富文本编辑器）
  - 商品相册（图片上传）
  - 规格设置（SKU配置）
  - 库存设置
  - 价格设置
  - 运费模板

- **库存管理** (`product/stock.html`):
  - 库存列表
  - 库存调整（入库、出库）
  - 库存预警设置

#### 2.3 订单管理
- **订单列表** (`order/list.html`):
  - 订单列表
  - 订单状态筛选
  - 订单搜索

- **订单详情** (`order/detail.html`):
  - 订单信息
  - 收货信息
  - 商品信息
  - 订单操作

- **发货** (`order/ship.html`):
  - 选择物流公司
  - 输入物流单号
  - 批量发货

- **售后处理** (`after-sale/list.html`):
  - 售后申请列表
  - 售后审核（同意/拒绝）
  - 退款处理

#### 2.4 店铺管理
- **店铺信息** (`shop/info.html`):
  - 店铺名称、Logo、简介
  - 客服联系方式

- **店铺装修** (`shop/decoration.html`):
  - 店铺首页模板选择
  - 自定义布局
  - 模块拖拽

- **子账号管理** (`shop/account.html`):
  - 子账号列表
  - 新增子账号
  - 权限分配

#### 2.5 营销管理
- **优惠券** (`promotion/coupon.html`):
  - 优惠券列表
  - 创建优惠券
  - 发放记录

- **满减活动** (`promotion/full-reduction.html`):
  - 活动列表
  - 创建活动
  - 活动商品设置

- **限时折扣** (`promotion/discount.html`):
  - 活动列表
  - 创建活动
  - 商品设置

#### 2.6 数据统计
- **销售统计** (`statistics/sales.html`):
  - 销售额统计
  - 订单量统计
  - 图表展示

- **商品统计** (`statistics/product.html`):
  - 商品销量排行
  - 商品浏览排行
  - 库存预警

- **访客统计** (`statistics/visitor.html`):
  - 访客数统计
  - 转化率统计

### 3. 平台管理模块（openmall-platform）

#### 3.1 控制台
- **首页** (`dashboard/index.html`):
  - 平台数据概览（用户数、商家数、交易额）
  - 待审核商家
  - 待处理售后
  - 数据图表

#### 3.2 用户管理
- **买家管理** (`user/buyer.html`):
  - 买家列表
  - 买家详情
  - 禁用/解禁

- **商家管理** (`user/merchant.html`):
  - 商家列表
  - 商家审核
  - 商家详情

#### 3.3 商品管理
- **分类管理** (`product/category.html`):
  - 分类树形展示
  - 分类增删改

- **品牌管理** (`product/brand.html`):
  - 品牌列表
  - 品牌增删改

#### 3.4 订单管理
- **订单列表** (`order/list.html`):
  - 全平台订单查看
  - 订单详情
  - 订单仲裁

#### 3.5 系统设置
- **菜单管理** (`system/menu.html`)
- **角色管理** (`system/role.html`)
- **字典管理** (`system/dict.html`)
- **参数配置** (`system/config.html`)

---

## 四、数据库设计规范

### 1. 命名规范
- **表名**: 小写字母+下划线，如 `order_master`
- **字段名**: 小写字母+下划线，如 `order_no`
- **主键**: 统一使用 `id`（BIGINT）
- **索引**: `idx_表名_字段名`，如 `idx_order_user_id`

### 2. 通用字段
每个表需包含以下通用字段：
```sql
id BIGINT PRIMARY KEY,
create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
create_by BIGINT NOT NULL,
update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
update_by BIGINT NOT NULL,
is_deleted TINYINT NOT NULL DEFAULT 0 COMMENT '0:未删除 1:已删除',
version INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本号'
```

### 3. 核心表设计
#### 3.1 用户表 (user)
```sql
CREATE TABLE `user` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码（BCrypt加密）',
    `mobile` VARCHAR(11) COMMENT '手机号',
    `email` VARCHAR(100) COMMENT '邮箱',
    `nickname` VARCHAR(50) COMMENT '昵称',
    `avatar` VARCHAR(255) COMMENT '头像',
    `gender` TINYINT DEFAULT 0 COMMENT '性别（0：未知，1：男，2：女）',
    `birthday` VARCHAR(20) COMMENT '生日',
    `user_type` TINYINT NOT NULL COMMENT '用户类型（1：买家，2：商家，3：平台管理员）',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态（0：正常，1：冻结，2：禁用）',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(50) COMMENT '最后登录IP',
    `register_source` VARCHAR(50) COMMENT '注册来源',
    ...通用字段
);
```

#### 3.2 商家表 (merchant)
```sql
CREATE TABLE `merchant` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `company_name` VARCHAR(200) NOT NULL COMMENT '公司名称',
    `business_license` VARCHAR(100) COMMENT '营业执照号',
    `contact_name` VARCHAR(50) NOT NULL COMMENT '联系人姓名',
    `contact_phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `shop_id` BIGINT COMMENT '店铺ID',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态（0：待审核，1：通过，2：拒绝）',
    ...通用字段
);
```

#### 3.3 店铺表 (shop)
```sql
CREATE TABLE `shop` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `merchant_id` BIGINT NOT NULL COMMENT '商家ID',
    `shop_name` VARCHAR(100) NOT NULL COMMENT '店铺名称',
    `shop_logo` VARCHAR(255) COMMENT '店铺Logo',
    `shop_desc` VARCHAR(500) COMMENT '店铺简介',
    `shop_type` TINYINT NOT NULL COMMENT '店铺类型（1：旗舰店，2：专卖店，3：专营店）',
    `service_phone` VARCHAR(20) COMMENT '客服联系电话',
    ...通用字段
);
```

#### 3.4 商品分类表 (category)
```sql
CREATE TABLE `category` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID',
    `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `level` TINYINT NOT NULL COMMENT '分类级别',
    `icon` VARCHAR(255) COMMENT '分类图标',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `is_show` TINYINT NOT NULL DEFAULT 1 COMMENT '是否显示',
    ...通用字段
);
```

#### 3.5 商品SPU表 (product_spu)
```sql
CREATE TABLE `product_spu` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `shop_id` BIGINT NOT NULL COMMENT '店铺ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `brand_id` BIGINT COMMENT '品牌ID',
    `product_name` VARCHAR(200) NOT NULL COMMENT '商品名称',
    `product_sub_title` VARCHAR(200) COMMENT '商品副标题',
    `product_desc` TEXT COMMENT '商品描述',
    `main_image` VARCHAR(255) COMMENT '商品主图',
    `album_images` TEXT COMMENT '商品相册',
    `is_shelves` TINYINT NOT NULL DEFAULT 0 COMMENT '是否上架',
    `sales` INT NOT NULL DEFAULT 0 COMMENT '销量',
    `views` INT DEFAULT 0 COMMENT '浏览量',
    ...通用字段
);
```

#### 3.6 商品SKU表 (product_sku)
```sql
CREATE TABLE `product_sku` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `spu_id` BIGINT NOT NULL COMMENT 'SPU ID',
    `sku_name` VARCHAR(200) COMMENT 'SKU名称',
    `specs` TEXT COMMENT '规格属性（JSON）',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `stock` INT NOT NULL COMMENT '库存',
    `sales` INT NOT NULL DEFAULT 0 COMMENT '销量',
    ...通用字段
);
```

#### 3.7 订单主表 (order_master)
```sql
CREATE TABLE `order_master` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `order_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '订单号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID（买家）',
    `shop_id` BIGINT NOT NULL COMMENT '店铺ID',
    `order_status` TINYINT NOT NULL COMMENT '订单状态',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '商品总额',
    `freight_amount` DECIMAL(10,2) NOT NULL COMMENT '运费',
    `discount_amount` DECIMAL(10,2) NOT NULL COMMENT '优惠金额',
    `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    `pay_type` TINYINT COMMENT '支付方式',
    `pay_time` DATETIME COMMENT '支付时间',
    `consignee_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
    `consignee_phone` VARCHAR(20) NOT NULL COMMENT '收货人手机',
    `consignee_address` VARCHAR(500) NOT NULL COMMENT '收货地址',
    ...通用字段
);
```

#### 3.8 订单明细表 (order_item)
```sql
CREATE TABLE `order_item` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
    `sku_id` BIGINT NOT NULL COMMENT 'SKU ID',
    `product_name` VARCHAR(200) NOT NULL COMMENT '商品名称',
    `specs` TEXT COMMENT '规格属性',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `quantity` INT NOT NULL COMMENT '数量',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    ...通用字段
);
```

#### 3.9 购物车表 (cart_item)
```sql
CREATE TABLE `cart_item` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `sku_id` BIGINT NOT NULL COMMENT 'SKU ID',
    `quantity` INT NOT NULL COMMENT '数量',
    `is_selected` TINYINT NOT NULL DEFAULT 1 COMMENT '是否选中',
    ...通用字段
);
```

#### 3.10 收货地址表 (user_address)
```sql
CREATE TABLE `user_address` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `consignee_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
    `consignee_phone` VARCHAR(20) NOT NULL COMMENT '收货人手机',
    `province` VARCHAR(50) NOT NULL COMMENT '省份',
    `city` VARCHAR(50) NOT NULL COMMENT '城市',
    `district` VARCHAR(50) NOT NULL COMMENT '区县',
    `detail_address` VARCHAR(200) NOT NULL COMMENT '详细地址',
    `is_default` TINYINT NOT NULL DEFAULT 0 COMMENT '是否默认',
    ...通用字段
);
```

#### 3.11 优惠券表 (coupon)
```sql
CREATE TABLE `coupon` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `shop_id` BIGINT COMMENT '店铺ID',
    `coupon_name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    `coupon_type` TINYINT NOT NULL COMMENT '优惠券类型',
    `discount_amount` DECIMAL(10,2) COMMENT '优惠金额',
    `min_consume` DECIMAL(10,2) NOT NULL COMMENT '最低消费',
    `total_count` INT NOT NULL COMMENT '总数量',
    `remain_count` INT NOT NULL COMMENT '剩余数量',
    `start_time` DATETIME NOT NULL COMMENT '开始时间',
    `end_time` DATETIME NOT NULL COMMENT '结束时间',
    ...通用字段
);
```

#### 3.12 商品收藏表 (user_favorite)
```sql
CREATE TABLE `user_favorite` (
    `id` BIGINT NOT NULL PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `spu_id` BIGINT NOT NULL COMMENT 'SPU ID',
    ...通用字段
);
```

---

## 五、Thymeleaf 模板开发规范

### 1. 模板目录结构

```
resources/templates/
├── layout/                       # 布局模板
│   ├── main.html                # 主布局
│   ├── portal-main.html         # 前台主布局
│   ├── merchant-main.html       # 商家后台主布局
│   └── platform-main.html      # 平台管理主布局
│
├── fragments/                   # 公共片段
│   ├── header.html              # 头部片段
│   ├── footer.html              # 底部片段
│   ├── pagination.html          # 分页片段
│   └── message.html            # 消息提示片段
│
├── auth/                        # 认证相关页面
│   ├── login.html
│   └── register.html
│
├── index/                       # 首页
│   └── index.html
│
├── product/                     # 商品相关页面
│   ├── list.html
│   ├── detail.html
│   └── category.html
│
├── cart/                        # 购物车页面
│   └── index.html
│
├── order/                       # 订单相关页面
│   ├── confirm.html
│   ├── detail.html
│   ├── list.html
│   ├── payment.html
│   └── review.html
│
├── user/                        # 用户中心页面
│   ├── index.html
│   ├── profile.html
│   ├── address.html
│   ├── favorite.html
│   ├── history.html
│   └── security.html
│
└── search/                      # 搜索页面
    └── index.html
```

### 2. 布局模板示例

#### 主布局模板 (layout/main.html)
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head th:fragment="head(title, css)">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:replace="${title}">默认标题</title>
    <!-- Bootstrap CSS -->
    <link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
    <!-- 自定义CSS -->
    <link th:href="@{/css/common.css}" rel="stylesheet">
    <th:block th:replace="${css}" />
</head>
<body>
    <!-- 头部 -->
    <div th:replace="fragments/header :: header"></div>

    <!-- 主体内容 -->
    <main class="main-content">
        <div th:replace="${content}"></div>
    </main>

    <!-- 底部 -->
    <div th:replace="fragments/footer :: footer"></div>

    <!-- jQuery -->
    <script th:src="@{/js/jquery-3.6.0.min.js}"></script>
    <!-- Bootstrap JS -->
    <script th:src="@{/js/bootstrap.bundle.min.js}"></script>
    <!-- 自定义JS -->
    <script th:src="@{/js/common.js}"></script>
    <th:block th:replace="${js}" />
</body>
</html>
```

### 3. 页面模板示例

#### 商品详情页 (product/detail.html)
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      th:replace="layout/portal-main :: main(~{::title}, ~{::head-link}, ~{::main-content}, ~{::script})">
<head>
    <title>商品详情 - [[${product.productName}]]</title>
</head>
<body>
    <main th:fragment="main-content">
        <!-- 面包屑 -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/">首页</a></li>
                <li class="breadcrumb-item"><a href="#">[[${category.categoryName}]]</a></li>
                <li class="breadcrumb-item active">[[${product.productName}]]</li>
            </ol>
        </nav>

        <!-- 商品主体 -->
        <div class="product-detail">
            <div class="row">
                <!-- 商品图片 -->
                <div class="col-md-5">
                    <div class="product-images">
                        <img th:src="${product.mainImage}" class="main-image">
                        <div class="thumbnail-list">
                            <img th:each="image : ${product.albumImages.split(',')}"
                                 th:src="${image}"
                                 class="thumbnail">
                        </div>
                    </div>
                </div>

                <!-- 商品信息 -->
                <div class="col-md-7">
                    <h1 class="product-title">[[${product.productName}]]</h1>
                    <p class="product-subtitle">[[${product.productSubTitle}]]</p>

                    <!-- 价格 -->
                    <div class="price-section">
                        <span class="price">¥[[${minPrice}]]</span>
                        <span class="original-price">¥[[${maxPrice}]]</span>
                    </div>

                    <!-- SKU选择 -->
                    <div class="sku-section">
                        <div th:each="attr : ${saleAttributes}">
                            <label>[[${attr.attrName}]]:</label>
                            <div class="sku-options">
                                <span th:each="value : ${attr.attrValues}"
                                      th:class="'sku-option ' + (${selectedSku} == ${value.id} ? 'active' : '')"
                                      th:data-sku-id="${value.id}"
                                      th:text="${value.attrValue}"></span>
                            </div>
                        </div>
                    </div>

                    <!-- 购买数量 -->
                    <div class="quantity-section">
                        <label>数量:</label>
                        <input type="number" id="quantity" value="1" min="1" max="${stock}">
                        <span>库存: [[${stock}]]件</span>
                    </div>

                    <!-- 购买按钮 -->
                    <div class="action-buttons">
                        <button type="button" class="btn btn-primary" id="add-to-cart">加入购物车</button>
                        <button type="button" class="btn btn-danger" id="buy-now">立即购买</button>
                        <button type="button" class="btn btn-outline-secondary" id="add-favorite">
                            <i class="far fa-heart"></i> 收藏
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- 商品详情Tab -->
        <div class="product-tabs mt-4">
            <ul class="nav nav-tabs" id="productTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" data-bs-toggle="tab" href="#detail">商品详情</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="tab" href="#specs">规格参数</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" data-bs-toggle="tab" href="#reviews">商品评价([[${reviewCount}]])</a>
                </li>
            </ul>

            <div class="tab-content">
                <div class="tab-pane fade show active" id="detail">
                    <div th:utext="${product.productDesc}"></div>
                </div>
                <div class="tab-pane fade" id="specs">
                    <!-- 规格参数列表 -->
                </div>
                <div class="tab-pane fade" id="reviews">
                    <!-- 评价列表 -->
                </div>
            </div>
        </div>
    </main>

    <script th:fragment="script">
    <script>
        // SKU选择
        $('.sku-option').click(function() {
            $(this).siblings('.sku-option').removeClass('active');
            $(this).addClass('active');

            // 更新价格和库存
            var skuId = $(this).data('sku-id');
            updateSkuInfo(skuId);
        });

        // 加入购物车
        $('#add-to-cart').click(function() {
            var skuId = getSelectedSkuId();
            var quantity = $('#quantity').val();

            $.ajax({
                url: '/cart/add',
                type: 'POST',
                data: { skuId: skuId, quantity: quantity },
                success: function(response) {
                    if (response.code === 200) {
                        Swal.fire({
                            icon: 'success',
                            title: '成功',
                            text: '已加入购物车',
                            timer: 1500
                        });
                    }
                }
            });
        });

        // 立即购买
        $('#buy-now').click(function() {
            var skuId = getSelectedSkuId();
            var quantity = $('#quantity').val();
            window.location.href = '/order/confirm?skuId=' + skuId + '&quantity=' + quantity;
        });

        // 收藏
        $('#add-favorite').click(function() {
            var spuId = [[${product.id}]];
            $.ajax({
                url: '/favorite/add',
                type: 'POST',
                data: { spuId: spuId },
                success: function(response) {
                    if (response.code === 200) {
                        $('#add-favorite i').removeClass('far').addClass('fas');
                        Swal.fire({
                            icon: 'success',
                            title: '成功',
                            text: '已添加收藏',
                            timer: 1500
                        });
                    }
                }
            });
        });
    </script>
    </script>
</body>
</html>
```

### 4. 公共片段示例

#### 分页片段 (fragments/pagination.html)
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<div th:fragment="pagination(page, url)" class="pagination-wrapper">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <li class="page-item" th:class="${page.pageNum > 1} ? '' : 'disabled'">
                <a class="page-link" th:href="@{${url}(pageNum=${page.pageNum - 1}, pageSize=${page.pageSize})}"
                   th:text="上一页"></a>
            </li>

            <li class="page-item" th:each="i : ${#numbers.sequence(1, page.totalPages)}"
                th:class="${page.pageNum == i} ? 'active' : ''">
                <a class="page-link" th:href="@{${url}(pageNum=${i}, pageSize=${page.pageSize})}"
                   th:text="${i}"></a>
            </li>

            <li class="page-item" th:class="${page.pageNum < page.totalPages} ? '' : 'disabled'">
                <a class="page-link" th:href="@{${url}(pageNum=${page.pageNum + 1}, pageSize=${page.pageSize})}"
                   th:text="下一页"></a>
            </li>
        </ul>

        <div class="pagination-info">
            共 [[${page.total}]] 条记录，第 [[${page.pageNum}]]/[[${page.totalPages}]] 页
        </div>
    </nav>
</div>
</html>
```

---

## 六、Controller开发规范

### 1. 控制器命名规范
- 前台商城：`XxxController`
- 商家后台：`MerchantXxxController`
- 平台管理：`PlatformXxxController`

### 2. Controller方法命名规范
```java
// 查询列表
@GetMapping
public String list(...) { }

// 查询详情
@GetMapping("/{id}")
public String detail(@PathVariable Long id, Model model) { }

// 跳转到新增/编辑页
@GetMapping("/add")
public String toAdd(Model model) { }

@GetMapping("/edit/{id}")
public String toEdit(@PathVariable Long id, Model model) { }

// 保存
@PostMapping("/save")
@ResponseBody
public Result<Void> save(@Valid @RequestBody XxxDTO dto) { }

// 更新
@PutMapping("/update")
@ResponseBody
public Result<Void> update(@Valid @RequestBody XxxDTO dto) { }

// 删除
@DeleteMapping("/delete/{id}")
@ResponseBody
public Result<Void> delete(@PathVariable Long id) { }
```

### 3. Controller示例

```java
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 商品列表页
     */
    @GetMapping("/list")
    public String list(@RequestParam(required = false) Long categoryId,
                      @RequestParam(required = false) Long brandId,
                      @RequestParam(required = false) String keyword,
                      @RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "20") Integer pageSize,
                      Model model) {

        // 构建查询条件
        ProductQuery query = new ProductQuery();
        query.setCategoryId(categoryId);
        query.setBrandId(brandId);
        query.setKeyword(keyword);
        query.setPageNum(pageNum);
        query.setPageSize(pageSize);

        // 查询商品列表
        PageResult<ProductVO> pageResult = productService.queryPage(query);

        // 查询分类列表
        List<CategoryVO> categoryList = categoryService.listAll();

        // 查询品牌列表
        List<BrandVO> brandList = brandService.listAll();

        // 设置模型数据
        model.addAttribute("page", pageResult);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("brandList", brandList);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("brandId", brandId);
        model.addAttribute("keyword", keyword);

        return "product/list";
    }

    /**
     * 商品详情页
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        // 查询商品SPU
        ProductSpuVO product = productService.getSpuById(id);

        // 查询SKU列表
        List<ProductSkuVO> skuList = productService.getSkuListBySpuId(id);

        // 查询销售属性
        List<SaleAttributeVO> saleAttributes = productService.getSaleAttributes(id);

        // 查询商品评价
        PageResult<ReviewVO> reviewPage = reviewService.queryBySpuId(id, 1, 10);

        // 设置模型数据
        model.addAttribute("product", product);
        model.addAttribute("skuList", skuList);
        model.addAttribute("saleAttributes", saleAttributes);
        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("minPrice", skuList.stream().map(ProductSkuVO::getPrice).min(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
        model.addAttribute("maxPrice", skuList.stream().map(ProductSkuVO::getPrice).max(BigDecimal::compareTo).orElse(BigDecimal.ZERO));
        model.addAttribute("stock", skuList.stream().map(ProductSkuVO::getStock).reduce(0, Integer::sum));
        model.addAttribute("reviewCount", reviewPage.getTotal());

        return "product/detail";
    }
}
```

---

## 七、Service开发规范

### 1. Service接口定义
```java
public interface ProductService {

    /**
     * 分页查询商品
     */
    PageResult<ProductVO> queryPage(ProductQuery query);

    /**
     * 根据ID查询商品SPU
     */
    ProductSpuVO getSpuById(Long id);

    /**
     * 根据SPU ID查询SKU列表
     */
    List<ProductSkuVO> getSkuListBySpuId(Long spuId);

    /**
     * 查询销售属性
     */
    List<SaleAttributeVO> getSaleAttributes(Long spuId);

    /**
     * 保存商品
     */
    Long save(ProductDTO dto);

    /**
     * 更新商品
     */
    void update(ProductDTO dto);

    /**
     * 删除商品
     */
    void delete(Long id);

    /**
     * 上架/下架
     */
    void changeShelvesStatus(Long id, Integer status);
}
```

### 2. Service实现类
```java
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductSpuRepository productSpuRepository;
    private final ProductSkuRepository productSkuRepository;

    @Override
    public PageResult<ProductVO> queryPage(ProductQuery query) {
        // 构建条件
        Specification<ProductSpu> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query.getCategoryId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoryId"), query.getCategoryId()));
            }

            if (query.getBrandId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("brandId"), query.getBrandId()));
            }

            if (StringUtils.isNotBlank(query.getKeyword())) {
                predicates.add(criteriaBuilder.like(root.get("productName"), "%" + query.getKeyword() + "%"));
            }

            predicates.add(criteriaBuilder.equal(root.get("isDeleted"), 0));
            predicates.add(criteriaBuilder.equal(root.get("isShelves"), 1));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 分页查询
        Pageable pageable = PageRequest.of(query.getPageNum() - 1, query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime"));
        Page<ProductSpu> page = productSpuRepository.findAll(spec, pageable);

        // 转换为VO
        List<ProductVO> list = page.getContent().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult<>(list, page.getTotalElements(),
                query.getPageNum(), query.getPageSize());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long save(ProductDTO dto) {
        // 保存SPU
        ProductSpu spu = new ProductSpu();
        BeanUtils.copyProperties(dto, spu);
        spu.setIsShelves(0);
        spu.setSales(0);
        spu.setAuditStatus(0);
        productSpuRepository.save(spu);

        // 保存SKU
        if (CollectionUtils.isNotEmpty(dto.getSkuList())) {
            for (ProductSkuDTO skuDTO : dto.getSkuList()) {
                ProductSku sku = new ProductSku();
                BeanUtils.copyProperties(skuDTO, sku);
                sku.setSpuId(spu.getId());
                sku.setSales(0);
                productSkuRepository.save(sku);
            }
        }

        return spu.getId();
    }
}
```

---

## 八、前后端数据交互

### 1. 模型数据传递
```java
// Controller中设置模型数据
@GetMapping("/list")
public String list(Model model) {
    List<CategoryVO> categoryList = categoryService.listAll();
    model.addAttribute("categoryList", categoryList);
    return "product/list";
}
```

### 2. Thymeleaf接收数据
```html
<!-- 循环列表 -->
<th:block th:each="category : ${categoryList}">
    <a th:href="@{/product/list(categoryId=${category.id})}"
       th:text="${category.categoryName}"></a>
</th:block>

<!-- 条件判断 -->
<div th:if="${user != null}">
    欢迎你，[[${user.nickname}]]
</div>

<!-- 格式化时间 -->
<span th:text="${#temporals.format(product.createTime, 'yyyy-MM-dd HH:mm:ss')}"></span>
```

### 3. AJAX请求
```javascript
// 加入购物车
$.ajax({
    url: '/cart/add',
    type: 'POST',
    data: {
        skuId: skuId,
        quantity: quantity
    },
    success: function(response) {
        if (response.code === 200) {
            Swal.fire({
                icon: 'success',
                title: '成功',
                text: response.message
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: '失败',
                text: response.message
            });
        }
    }
});
```

### 4. 表单提交
```html
<form th:action="@{/product/save}" method="post" id="productForm">
    <!-- 表单字段 -->
    <input type="text" name="productName" th:value="${product?.productName}">
    <button type="submit">保存</button>
</form>
```

---

## 九、安全规范

### 1. 用户认证
- **登录认证**: 使用Session存储用户信息
- **记住我**: Cookie持久化登录状态
- **退出登录**: 清除Session

### 2. 权限控制
- **买家权限**: 浏览商品、下单、评价
- **商家权限**: 商品管理、订单处理
- **平台权限**: 用户管理、商家审核

### 3. 数据安全
- **密码加密**: BCrypt加密存储
- **敏感信息**: 脱敏显示
- **CSRF防护**: 启用CSRF Token

### 4. 接口安全
- **防SQL注入**: JPA参数化查询
- **防XSS攻击**: 输入输出过滤
- **防CSRF**: Token验证

---

## 十、性能优化

### 1. 缓存策略
- **商品信息缓存**: Redis缓存热门商品
- **分类缓存**: Redis缓存商品分类
- **用户信息缓存**: Redis缓存登录用户信息

### 2. 数据库优化
- **索引优化**: 合理创建索引
- **查询优化**: 避免N+1查询
- **分页查询**: 使用JPA分页

### 3. 静态资源优化
- **CDN加速**: 静态资源CDN分发
- **图片压缩**: 自动压缩图片
- **资源合并**: CSS/JS合并压缩

---

## 十一、开发优先级

### 第一阶段：基础框架搭建
1. 公共模块开发
2. 数据库设计与初始化
3. 用户认证模块（登录、注册）
4. 布局模板开发

### 第二阶段：前台商城核心功能
1. 首页开发
2. 商品列表页
3. 商品详情页
4. 购物车功能
5. 订单功能（下单、支付）

### 第三阶段：用户中心
1. 用户信息管理
2. 收货地址管理
3. 我的订单
4. 商品评价

### 第四阶段：商家后台
1. 商品管理（SPU/SKU）
2. 订单管理
3. 店铺管理
4. 数据统计

### 第五阶段：平台管理
1. 用户管理
2. 商家管理
3. 系统设置

### 第六阶段：营销功能
1. 优惠券
2. 满减活动
3. 限时折扣

---

## 十二、注意事项

1. **代码质量**: 严格遵循代码规范，保持高代码覆盖率
2. **安全性**: 重视用户数据和支付安全
3. **性能**: 合理使用缓存，优化数据库查询
4. **可维护性**: 清晰的代码结构、完善的注释
5. **扩展性**: 预留扩展接口
6. **用户体验**: 注重前端交互和视觉效果

---

## 总结

本项目是一个功能完善的B2B2C电商系统，采用前后端不分离架构，使用Thymeleaf模板引擎渲染页面。在开发过程中，请严格遵循本提示词中的技术栈、架构设计、功能模块、数据库设计、Thymeleaf开发规范等要求，确保系统的高质量、高性能、高安全性。同时，充分利用Spring Boot 3.5.10和JDK 21的新特性，打造一个现代化、易扩展的开源电商解决方案。
