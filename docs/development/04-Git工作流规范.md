# OpenMall Git 工作流规范

## 1. 文档信息

| 属性 | 内容 |
|------|------|
| 文档名称 | OpenMall Git 工作流规范 |
| 版本号 | v1.0.0 |
| 创建日期 | 2026-06-30 |

## 2. 分支模型

### 2.1 主要分支

```
main
  │
  ├── develop
  │     │
  │     ├── feature/xxx
  │     ├── fix/xxx
  │     ├── docs/xxx
  │     └── refactor/xxx
  │
  └── hotfix/xxx
```

| 分支 | 用途 | 生命周期 |
|------|------|----------|
| `main` | 生产环境代码 | 永久 |
| `develop` | 开发主线 | 永久 |
| `feature/*` | 新功能开发 | 临时，合并后删除 |
| `fix/*` | Bug 修复 | 临时，合并后删除 |
| `docs/*` | 文档更新 | 临时，合并后删除 |
| `refactor/*` | 代码重构 | 临时，合并后删除 |
| `hotfix/*` | 紧急修复 | 临时，合并后删除 |
| `release/*` | 版本发布 | 临时，发布后删除 |

### 2.2 分支命名规范

```
feature/<模块>-<功能描述>     # feature/order-refund
feature/<issue编号>-<描述>    # feature/123-add-coupon
fix/<issue编号>-<描述>        # fix/456-login-error
hotfix/<版本>-<描述>          # hotfix/10.3.1-payment-fix
release/<版本号>              # release/10.3.0
```

## 3. 开发流程

### 3.1 功能开发流程

```bash
# 1. 从 develop 创建功能分支
git checkout develop
git pull origin develop
git checkout -b feature/order-refund

# 2. 开发并提交
git add .
git commit -m "feat(order): 添加订单退款功能"

# 3. 推送到远程
git push origin feature/order-refund

# 4. 创建 Pull Request 到 develop

# 5. 代码审查通过后合并

# 6. 删除功能分支
git branch -d feature/order-refund
```

### 3.2 Bug 修复流程

```bash
# 1. 从 develop 创建修复分支
git checkout develop
git checkout -b fix/456-login-error

# 2. 修复并提交
git add .
git commit -m "fix(auth): 修复登录页面重定向错误

问题描述：登录成功后未正确跳转到目标页面
解决方案：修正 redirect URL 拼接逻辑

Closes #456"

# 3. 创建 PR 到 develop
```

### 3.3 紧急修复流程

```bash
# 1. 从 main 创建 hotfix 分支
git checkout main
git checkout -b hotfix/10.3.1-payment-fix

# 2. 修复并提交
git add .
git commit -m "fix(payment): 修复支付回调金额校验问题"

# 3. 合并到 main 和 develop
git checkout main
git merge hotfix/10.3.1-payment-fix
git tag -a v10.3.1 -m "Hotfix: 支付回调金额校验"
git push origin main --tags

git checkout develop
git merge hotfix/10.3.1-payment-fix
git push origin develop

# 4. 删除 hotfix 分支
git branch -d hotfix/10.3.1-payment-fix
```

## 4. Commit 规范

### 4.1 格式

```
<type>(<scope>): <subject>

<body>

<footer>
```

### 4.2 Type 类型

| 类型 | 说明 |
|------|------|
| `feat` | 新功能 |
| `fix` | Bug 修复 |
| `docs` | 文档变更 |
| `style` | 代码格式（不影响功能） |
| `refactor` | 代码重构 |
| `perf` | 性能优化 |
| `test` | 测试相关 |
| `chore` | 构建/工具变更 |
| `ci` | CI 配置变更 |

### 4.3 Scope 范围

| 范围 | 说明 |
|------|------|
| `portal` | 前台商城模块 |
| `merchant` | 商家后台模块 |
| `platform` | 平台管理模块 |
| `common` | 公共模块 |
| `auth` | 认证相关 |
| `order` | 订单相关 |
| `product` | 商品相关 |
| `user` | 用户相关 |
| `cart` | 购物车相关 |
| `payment` | 支付相关 |
| `docker` | Docker 相关 |
| `config` | 配置相关 |
| `docs` | 文档相关 |
| `build` | 构建相关 |

### 4.4 示例

```bash
# 新功能
feat(order): 添加订单退款功能
feat(product): 支持商品多规格选择
feat(merchant): 添加销售数据统计报表

# Bug 修复
fix(auth): 修复登录后页面重定向错误
fix(cart): 修复购物车数量计算精度问题
fix(order): 修复并发下单库存扣减问题

# 文档
docs(api): 更新商品接口文档
docs(readme): 补充部署说明

# 重构
refactor(user): 重构用户服务层
refactor(common): 提取公共异常处理

# 性能
perf(product): 优化商品列表查询性能
perf(order): 添加订单查询缓存

# 测试
test(order): 补充订单模块单元测试
test(auth): 添加登录功能集成测试
```

## 5. 合并策略

### 5.1 PR 合并要求

| 要求 | 说明 |
|------|------|
| CI 通过 | 所有自动化检查必须通过 |
| 代码审查 | 至少 1 位 Maintainer 审查通过 |
| 无冲突 | 与目标分支无合并冲突 |
| 测试覆盖 | 新增代码需有相应测试 |

### 5.2 合并方式

```bash
# 推荐：Squash Merge
git merge --squash feature/xxx
git commit -m "feat(order): 添加订单退款功能 (#123)"
```

## 6. Tag 规范

### 6.1 版本号

采用语义化版本：`MAJOR.MINOR.PATCH`

| 变更类型 | 版本号变化 |
|----------|------------|
| 不兼容的 API 修改 | MAJOR +1 |
| 向下兼容的功能新增 | MINOR +1 |
| 向下兼容的问题修复 | PATCH +1 |

### 6.2 Tag 创建

```bash
# 正式版本
git tag -a v10.3.0 -m "Release v10.3.0"
git push origin v10.3.0

# 预发布版本
git tag -a v10.3.0-rc1 -m "Release Candidate 1 for v10.3.0"
```

## 7. 常用命令

```bash
# 查看分支
git branch -a

# 同步远程分支
git fetch origin
git pull origin develop

# 暂存当前修改
git stash
git stash pop

# 查看提交历史
git log --oneline --graph --all

# 撤销修改
git checkout -- <file>          # 撤销工作区修改
git reset HEAD <file>           # 撤销暂存区修改
git reset --soft HEAD~1         # 撤销最近一次 commit（保留修改）
```

## 8. .gitignore 规范

```gitignore
# IDE
.idea/
*.iml
.vscode/
.project
.classpath
.settings/

# Maven
target/
*.jar
*.war

# Log
logs/
*.log

# OS
.DS_Store
Thumbs.db

# Temp
*.tmp
*.bak
*.swp
*~

# Env
.env
.env.local
application-local.yml
```

## 9. 版本历史

| 版本 | 日期 | 作者 | 变更说明 |
|------|------|------|----------|
| v1.0.0 | 2026-06-30 | - | 初稿 |
