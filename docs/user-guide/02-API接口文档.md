# OpenMall API 接口文档

## 1. 文档信息

| 属性 | 内容 |
|------|------|
| 文档名称 | OpenMall API 接口文档 |
| 版本号 | v1.0.0 |
| 创建日期 | 2026-06-30 |
| Base URL | http://localhost:8081 |

## 2. 通用说明

### 2.1 响应格式

```json
{ "code": 200, "message": "操作成功", "data": {} }
```

### 2.2 分页响应

```json
{
    "code": 200, "message": "操作成功",
    "data": { "records": [], "total": 100, "size": 10, "current": 1, "pages": 10 }
}
```

### 2.3 认证方式

Spring Security Session 认证，AJAX 请求需携带 CSRF Token：

```javascript
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$.ajax({ headers: { 'X-CSRF-TOKEN': token } });
```

## 3. 前台商城 API

### 3.1 用户认证

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /login | 登录页面 |
| POST | /login | 登录提交 |
| GET | /register | 注册页面 |
| POST | /register | 注册提交 |
| GET | /logout | 退出登录 |

### 3.2 商品接口

#### 商品列表

```
GET /api/products?page=1&size=20&categoryId=1&keyword=手机&sort=createTime&order=desc
```

响应：

```json
{
    "code": 200,
    "data": {
        "records": [{
            "id": 1, "spuName": "iPhone 15",
            "minPrice": 5999.00, "maxPrice": 8999.00,
            "mainImage": "/images/product/1.jpg", "salesVolume": 1000
        }],
        "total": 50, "size": 20, "current": 1, "pages": 3
    }
}
```

#### 商品详情

```
GET /api/products/{id}
```

响应包含 SPU 信息 + SKU 列表。

### 3.3 购物车接口

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /api/cart | 购物车列表 |
| POST | /api/cart/add | 加入购物车 `{"skuId":1,"quantity":1}` |
| POST | /api/cart/update | 更新数量 `{"cartItemId":1,"quantity":2}` |
| POST | /api/cart/delete/{id} | 删除商品 |
| POST | /api/cart/check | 选中/取消 `{"cartItemId":1,"checked":true}` |

### 3.4 订单接口

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /order/checkout | 结算页面 |
| POST | /api/order/create | 创建订单 |
| GET | /api/order/list?status=0 | 订单列表 |
| GET | /api/order/{id} | 订单详情 |
| POST | /api/order/cancel/{id} | 取消订单 |
| POST | /api/order/confirm/{id} | 确认收货 |

状态参数：0=待付款, 1=待发货, 2=待收货, 3=已完成, 4=已取消

### 3.5 收货地址接口

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /api/user/address | 地址列表 |
| POST | /api/user/address | 新增地址 |
| POST | /api/user/address/{id} | 更新地址 |
| POST | /api/user/address/delete/{id} | 删除地址 |
| POST | /api/user/address/default/{id} | 设为默认 |

### 3.6 收藏接口

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /api/user/favorites | 收藏列表 |
| POST | /api/user/favorite/{spuId} | 添加收藏 |
| POST | /api/user/favorite/remove/{spuId} | 取消收藏 |

### 3.7 用户信息

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /api/user/profile | 获取个人信息 |
| POST | /api/user/profile | 更新个人信息 |

## 4. 商家后台 API

### 4.1 控制台

```
GET /merchant/api/dashboard
```

### 4.2 商品管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /merchant/api/products | 商品列表 |
| POST | /merchant/api/products | 新增商品 |
| GET | /merchant/api/products/{id} | 商品详情 |
| PUT | /merchant/api/products/{id} | 更新商品 |
| POST | /merchant/api/products/{id}/shelf | 上架 |
| POST | /merchant/api/products/{id}/unshelf | 下架 |
| DELETE | /merchant/api/products/{id} | 删除 |

### 4.3 订单管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /merchant/api/orders | 订单列表 |
| GET | /merchant/api/orders/{id} | 订单详情 |
| POST | /merchant/api/orders/{id}/deliver | 发货 |
| POST | /merchant/api/orders/{id}/note | 添加备注 |

### 4.4 优惠券管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /merchant/api/coupons | 列表 |
| POST | /merchant/api/coupons | 创建 |
| DELETE | /merchant/api/coupons/{id} | 删除 |

## 5. 平台管理 API

### 5.1 用户管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /platform/api/users | 用户列表 |
| GET | /platform/api/users/{id} | 用户详情 |
| POST | /platform/api/users/{id}/status | 修改状态 |

### 5.2 商家管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET | /platform/api/merchants | 商家列表 |
| GET | /platform/api/merchants/{id} | 商家详情 |
| POST | /platform/api/merchants/{id}/approve | 审核通过 |
| POST | /platform/api/merchants/{id}/reject | 审核拒绝 |

### 5.3 分类与品牌管理

| 方法 | URL | 说明 |
|------|-----|------|
| GET/POST | /platform/api/categories | 分类列表/新增 |
| PUT/DELETE | /platform/api/categories/{id} | 更新/删除 |
| GET/POST | /platform/api/brands | 品牌列表/新增 |
| PUT/DELETE | /platform/api/brands/{id} | 更新/删除 |

## 6. 错误码参考

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |
| 1001 | 用户名或密码错误 |
| 1002 | 账号已被冻结 |
| 1004 | 用户名已存在 |
| 2001 | 商品不存在 |
| 2002 | 商品已下架 |
| 2003 | 库存不足 |
| 3001 | 购物车为空 |
| 4001 | 订单不存在 |
| 4002 | 订单状态异常 |

## 7. 版本历史

| 版本 | 日期 | 作者 | 变更说明 |
|------|------|------|----------|
| v1.0.0 | 2026-06-30 | - | 初稿 |
