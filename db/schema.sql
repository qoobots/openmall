-- ========================================
-- OpenMall B2B2C 电商平台数据库初始化脚本
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `openmall` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `openmall`;

-- ========================================
-- 用户相关表
-- ========================================

-- 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '0:未删除 1:已删除',
    `version` INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    INDEX `idx_user_username` (`username`),
    INDEX `idx_user_mobile` (`mobile`),
    INDEX `idx_user_type` (`user_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 商家表
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `company_name` VARCHAR(200) NOT NULL COMMENT '公司名称',
    `business_license` VARCHAR(100) COMMENT '营业执照号',
    `contact_name` VARCHAR(50) NOT NULL COMMENT '联系人姓名',
    `contact_phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `shop_id` BIGINT COMMENT '店铺ID',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '审核状态（0：待审核，1：通过，2：拒绝）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_merchant_user_id` (`user_id`),
    INDEX `idx_merchant_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家表';

-- 店铺表
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `merchant_id` BIGINT NOT NULL COMMENT '商家ID',
    `shop_name` VARCHAR(100) NOT NULL COMMENT '店铺名称',
    `shop_logo` VARCHAR(255) COMMENT '店铺Logo',
    `shop_desc` VARCHAR(500) COMMENT '店铺简介',
    `shop_type` TINYINT NOT NULL COMMENT '店铺类型（1：旗舰店，2：专卖店，3：专营店）',
    `service_phone` VARCHAR(20) COMMENT '客服联系电话',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_shop_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='店铺表';

-- 用户收货地址表
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `consignee_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
    `consignee_phone` VARCHAR(20) NOT NULL COMMENT '收货人手机',
    `province` VARCHAR(50) NOT NULL COMMENT '省份',
    `city` VARCHAR(50) NOT NULL COMMENT '城市',
    `district` VARCHAR(50) NOT NULL COMMENT '区县',
    `detail_address` VARCHAR(200) NOT NULL COMMENT '详细地址',
    `is_default` TINYINT NOT NULL DEFAULT 0 COMMENT '是否默认',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_address_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户收货地址表';

-- ========================================
-- 商品相关表
-- ========================================

-- 商品分类表
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID',
    `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `level` TINYINT NOT NULL COMMENT '分类级别',
    `icon` VARCHAR(255) COMMENT '分类图标',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `is_show` TINYINT NOT NULL DEFAULT 1 COMMENT '是否显示',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_category_parent_id` (`parent_id`),
    INDEX `idx_category_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 品牌表
DROP TABLE IF EXISTS `brand`;
CREATE TABLE `brand` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `brand_name` VARCHAR(100) NOT NULL COMMENT '品牌名称',
    `brand_logo` VARCHAR(255) COMMENT '品牌Logo',
    `brand_desc` VARCHAR(500) COMMENT '品牌描述',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `is_show` TINYINT NOT NULL DEFAULT 1 COMMENT '是否显示',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='品牌表';

-- 商品SPU表
DROP TABLE IF EXISTS `product_spu`;
CREATE TABLE `product_spu` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_spu_shop_id` (`shop_id`),
    INDEX `idx_spu_category_id` (`category_id`),
    INDEX `idx_spu_brand_id` (`brand_id`),
    INDEX `idx_spu_is_shelves` (`is_shelves`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品SPU表';

-- 商品SKU表
DROP TABLE IF EXISTS `product_sku`;
CREATE TABLE `product_sku` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `spu_id` BIGINT NOT NULL COMMENT 'SPU ID',
    `sku_name` VARCHAR(200) COMMENT 'SKU名称',
    `specs` TEXT COMMENT '规格属性（JSON）',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `stock` INT NOT NULL COMMENT '库存',
    `sales` INT NOT NULL DEFAULT 0 COMMENT '销量',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_sku_spu_id` (`spu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品SKU表';

-- ========================================
-- 订单相关表
-- ========================================

-- 订单主表
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
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
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_order_user_id` (`user_id`),
    INDEX `idx_order_shop_id` (`shop_id`),
    INDEX `idx_order_status` (`order_status`),
    INDEX `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单主表';

-- 订单明细表
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
    `sku_id` BIGINT NOT NULL COMMENT 'SKU ID',
    `product_name` VARCHAR(200) NOT NULL COMMENT '商品名称',
    `specs` TEXT COMMENT '规格属性',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `quantity` INT NOT NULL COMMENT '数量',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '小计金额',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_order_item_order_no` (`order_no`),
    INDEX `idx_order_item_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- ========================================
-- 购物车相关表
-- ========================================

-- 购物车表
DROP TABLE IF EXISTS `cart_item`;
CREATE TABLE `cart_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `sku_id` BIGINT NOT NULL COMMENT 'SKU ID',
    `quantity` INT NOT NULL COMMENT '数量',
    `is_selected` TINYINT NOT NULL DEFAULT 1 COMMENT '是否选中',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    UNIQUE KEY `uk_cart_user_sku` (`user_id`, `sku_id`),
    INDEX `idx_cart_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';

-- ========================================
-- 优惠券相关表
-- ========================================

-- 优惠券表
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `shop_id` BIGINT COMMENT '店铺ID',
    `coupon_name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    `coupon_type` TINYINT NOT NULL COMMENT '优惠券类型',
    `discount_amount` DECIMAL(10,2) COMMENT '优惠金额',
    `min_consume` DECIMAL(10,2) NOT NULL COMMENT '最低消费',
    `total_count` INT NOT NULL COMMENT '总数量',
    `remain_count` INT NOT NULL COMMENT '剩余数量',
    `start_time` DATETIME NOT NULL COMMENT '开始时间',
    `end_time` DATETIME NOT NULL COMMENT '结束时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_coupon_shop_id` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='优惠券表';

-- ========================================
-- 商品收藏表
-- ========================================

-- 商品收藏表
DROP TABLE IF EXISTS `user_favorite`;
CREATE TABLE `user_favorite` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `spu_id` BIGINT NOT NULL COMMENT 'SPU ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    UNIQUE KEY `uk_favorite_user_spu` (`user_id`, `spu_id`),
    INDEX `idx_favorite_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品收藏表';

-- ========================================
-- 初始化测试数据
-- ========================================

-- 插入默认管理员用户（密码：admin123，BCrypt加密）
INSERT INTO `user` (`username`, `password`, `nickname`, `user_type`, `status`, `create_by`, `update_by`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', 3, 0, 1, 1);

-- 插入测试买家用户
INSERT INTO `user` (`username`, `password`, `nickname`, `mobile`, `user_type`, `status`, `create_by`, `update_by`) VALUES
('buyer1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试买家', '13800138001', 1, 0, 1, 1);

-- 插入测试商家用户
INSERT INTO `user` (`username`, `password`, `nickname`, `mobile`, `user_type`, `status`, `create_by`, `update_by`) VALUES
('merchant1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '测试商家', '13800138002', 2, 0, 1, 1);

-- 插入商品分类
INSERT INTO `category` (`parent_id`, `category_name`, `level`, `sort_order`, `is_show`, `create_by`, `update_by`) VALUES
(0, '手机数码', 1, 1, 1, 1, 1),
(0, '家用电器', 1, 2, 1, 1, 1),
(0, '服装鞋包', 1, 3, 1, 1, 1),
(1, '手机', 2, 1, 1, 1, 1),
(1, '平板电脑', 2, 2, 1, 1, 1),
(2, '大家电', 2, 1, 1, 1, 1),
(2, '小家电', 2, 2, 1, 1, 1);

-- 插入品牌
INSERT INTO `brand` (`brand_name`, `brand_desc`, `sort_order`, `is_show`, `create_by`, `update_by`) VALUES
('苹果', 'Apple Inc.', 1, 1, 1, 1),
('华为', 'Huawei Technologies', 2, 1, 1, 1),
('小米', 'Xiaomi Corporation', 3, 1, 1, 1);
