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
    `stock_warning` INT DEFAULT 0 COMMENT '库存预警阈值',
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
-- 用户优惠券表
-- ========================================

DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `coupon_id` BIGINT NOT NULL COMMENT '优惠券ID',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态（0：未使用，1：已使用，2：已过期）',
    `use_time` DATETIME COMMENT '使用时间',
    `order_no` VARCHAR(32) COMMENT '关联订单号',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_user_coupon_user_id` (`user_id`),
    INDEX `idx_user_coupon_coupon_id` (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户优惠券表';

-- ========================================
-- 退款申请表
-- ========================================

DROP TABLE IF EXISTS `refund_order`;
CREATE TABLE `refund_order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_no` VARCHAR(32) NOT NULL COMMENT '原订单号',
    `refund_no` VARCHAR(32) NOT NULL UNIQUE COMMENT '退款单号',
    `user_id` BIGINT NOT NULL COMMENT '申请人ID',
    `shop_id` BIGINT NOT NULL COMMENT '店铺ID',
    `refund_amount` DECIMAL(10,2) NOT NULL COMMENT '退款金额',
    `refund_reason` VARCHAR(500) COMMENT '退款原因',
    `refund_type` TINYINT NOT NULL COMMENT '退款类型（1：仅退款，2：退货退款）',
    `refund_status` TINYINT NOT NULL DEFAULT 0 COMMENT '退款状态（0：待审核，1：已同意，2：已拒绝，3：退款中，4：已完成）',
    `refuse_reason` VARCHAR(500) COMMENT '拒绝原因',
    `audit_time` DATETIME COMMENT '审核时间',
    `refund_time` DATETIME COMMENT '退款完成时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_refund_order_no` (`order_no`),
    INDEX `idx_refund_user_id` (`user_id`),
    INDEX `idx_refund_shop_id` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款申请表';

-- ========================================
-- 系统菜单表
-- ========================================

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID',
    `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
    `menu_path` VARCHAR(200) COMMENT '路由路径',
    `menu_icon` VARCHAR(100) COMMENT '菜单图标',
    `permission` VARCHAR(200) COMMENT '权限标识',
    `menu_type` TINYINT NOT NULL COMMENT '类型（1：目录，2：菜单，3：按钮）',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `is_visible` TINYINT DEFAULT 1 COMMENT '是否可见',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_menu_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';

-- ========================================
-- 系统角色表
-- ========================================

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
    `role_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    `role_desc` VARCHAR(200) COMMENT '角色描述',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态（0：禁用，1：启用）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

-- ========================================
-- 角色菜单关联表
-- ========================================

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    UNIQUE KEY `uk_role_menu` (`role_id`, `menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';

-- ========================================
-- 用户角色关联表
-- ========================================

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- ========================================
-- 系统参数配置表
-- ========================================

DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `config_key` VARCHAR(100) NOT NULL UNIQUE COMMENT '参数键名',
    `config_value` TEXT COMMENT '参数值',
    `config_name` VARCHAR(100) COMMENT '参数名称',
    `config_desc` VARCHAR(500) COMMENT '参数描述',
    `config_type` TINYINT NOT NULL DEFAULT 1 COMMENT '类型（1：系统参数，2：业务参数）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统参数配置表';

-- ========================================
-- 库存出入库记录表
-- ========================================

DROP TABLE IF EXISTS `stock_log`;
CREATE TABLE `stock_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `sku_id` BIGINT NOT NULL COMMENT 'SKU ID',
    `change_type` TINYINT NOT NULL COMMENT '变更类型（1：入库，2：出库，3：订单锁定，4：订单释放，5：退货入库）',
    `change_count` INT NOT NULL COMMENT '变更数量',
    `before_stock` INT NOT NULL COMMENT '变更前库存',
    `after_stock` INT NOT NULL COMMENT '变更后库存',
    `related_no` VARCHAR(32) COMMENT '关联单号',
    `remark` VARCHAR(500) COMMENT '备注',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `create_by` BIGINT NOT NULL,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `update_by` BIGINT NOT NULL,
    `is_deleted` TINYINT NOT NULL DEFAULT 0,
    `version` INT NOT NULL DEFAULT 0,
    INDEX `idx_stock_log_sku_id` (`sku_id`),
    INDEX `idx_stock_log_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存出入库记录表';

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

-- 插入系统菜单
INSERT INTO `sys_menu` (`id`, `parent_id`, `menu_name`, `menu_path`, `menu_icon`, `permission`, `menu_type`, `sort_order`, `create_by`, `update_by`) VALUES
(1, 0, '系统管理', '', 'fa-cog', 'system', 1, 99, 1, 1),
(2, 1, '用户管理', '/platform/user', 'fa-users', 'system:user', 2, 1, 1, 1),
(3, 1, '商家管理', '/platform/merchant', 'fa-store', 'system:merchant', 2, 2, 1, 1),
(4, 1, '角色管理', '/platform/role', 'fa-user-shield', 'system:role', 2, 3, 1, 1),
(5, 1, '菜单管理', '/platform/menu', 'fa-bars', 'system:menu', 2, 4, 1, 1),
(6, 1, '参数配置', '/platform/config', 'fa-cogs', 'system:config', 2, 5, 1, 1);

-- 插入系统角色
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `role_desc`, `create_by`, `update_by`) VALUES
(1, '超级管理员', 'admin', '系统超级管理员，拥有所有权限', 1, 1),
(2, '运营人员', 'operator', '平台运营人员', 1, 1);

-- 插入角色菜单关联（超级管理员拥有所有菜单）
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`, `create_by`, `update_by`) VALUES
(1, 1, 1, 1), (1, 2, 1, 1), (1, 3, 1, 1), (1, 4, 1, 1), (1, 5, 1, 1), (1, 6, 1, 1);

-- 插入用户角色关联
INSERT INTO `sys_user_role` (`user_id`, `role_id`, `create_by`, `update_by`) VALUES
(1, 1, 1, 1);

-- 插入系统参数
INSERT INTO `sys_config` (`config_key`, `config_value`, `config_name`, `config_desc`, `config_type`, `create_by`, `update_by`) VALUES
('upload.max_size', '10', '上传文件大小限制(MB)', '上传文件大小限制(MB)', 1, 1, 1),
('upload.allowed_types', 'jpg,png,gif,webp', '允许上传的文件类型', '允许上传的文件类型', 1, 1, 1),
('page.default_size', '20', '默认分页大小', '默认分页大小', 1, 1, 1),
('order.auto_cancel_minutes', '30', '订单自动取消时间(分钟)', '未付款订单超时自动取消时间', 2, 1, 1),
('order.auto_confirm_days', '15', '订单自动确认收货天数', '发货后自动确认收货天数', 2, 1, 1);
