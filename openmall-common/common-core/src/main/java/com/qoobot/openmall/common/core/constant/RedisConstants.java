package com.qoobot.openmall.common.core.constant;

/**
 * Redis Key 常量
 */
public class RedisConstants {
    // 用户会话
    public static final String USER_SESSION_KEY = "openmall:user:session:";
    
    // 商品缓存
    public static final String PRODUCT_SPU_KEY = "openmall:product:spu:";
    public static final String PRODUCT_SKU_KEY = "openmall:product:sku:";
    public static final String CATEGORY_LIST_KEY = "openmall:category:list";
    
    // 购物车
    public static final String CART_KEY = "openmall:cart:user:";
    
    // 验证码
    public static final String CAPTCHA_KEY = "openmall:captcha:";
    
    // 分布式锁
    public static final String LOCK_PREFIX = "openmall:lock:";
    
    // 过期时间（秒）
    public static final long USER_SESSION_EXPIRE = 30 * 60 * 60; // 30分钟
    public static final long PRODUCT_CACHE_EXPIRE = 60 * 60; // 1小时
    public static final long CAPTCHA_EXPIRE = 5 * 60; // 5分钟
}
