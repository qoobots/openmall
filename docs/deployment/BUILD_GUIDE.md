# OpenMall 构建指南

本文档详细说明如何构建和运行 OpenMall 项目。

## 前置条件

在开始之前，请确保您的开发环境满足以下要求：

- **JDK**: 21 或更高版本
- **Maven**: 4.0.0 或更高版本
- **MySQL**: 8.0 或更高版本
- **Redis**: 6.0 或更高版本
- **IDE**: IntelliJ IDEA 或 Eclipse (推荐 IntelliJ IDEA)

## 1. 环境配置

### 1.1 安装 JDK 21

```bash
# 验证 JDK 版本
java -version
```

确保输出显示 Java 21。

### 1.2 安装 Maven 4.0.0+

```bash
# 验证 Maven 版本
mvn -version
```

### 1.3 安装 MySQL 8.0+

```bash
# 创建数据库
mysql -u root -p

CREATE DATABASE IF NOT EXISTS openmall DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 1.4 安装 Redis 6.0+

```bash
# 启动 Redis
redis-server
```

## 2. 项目构建

### 2.1 克隆项目

```bash
git clone https://github.com/qoobot-com/openmall.git
cd openmall
```

### 2.2 导入数据库

```bash
# 导入数据库初始化脚本
mysql -u root -p openmall < db/schema.sql
```

### 2.3 修改配置文件

编辑各模块的 `application.yml` 文件，修改数据库和 Redis 连接信息：

**openmall-portal/src/main/resources/application.yml**

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
```

**openmall-merchant/src/main/resources/application.yml**

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
      database: 1
```

**openmall-platform/src/main/resources/application.yml**

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
      database: 2
```

### 2.4 编译项目

```bash
# 清理并编译
mvn clean install

# 跳过测试编译
mvn clean install -DskipTests
```

## 3. 运行项目

### 3.1 方式一：使用 Maven 命令

```bash
# 启动前台商城
cd openmall-portal
mvn spring-boot:run

# 启动商家后台
cd ../openmall-merchant
mvn spring-boot:run

# 启动平台管理
cd ../openmall-platform
mvn spring-boot:run
```

### 3.2 方式二：使用 IDE 运行

在 IDE 中分别找到以下启动类并运行：

1. **openmall-portal**: `com.qoobot.openmall.portal.PortalApplication`
2. **openmall-merchant**: `com.qoobot.openmall.merchant.MerchantApplication`
3. **openmall-platform**: `com.qoobot.openmall.platform.PlatformApplication`

### 3.3 方式三：打包后运行

```bash
# 打包项目
mvn clean package -DskipTests

# 运行打包后的 JAR 文件
java -jar openmall-portal/target/openmall-portal-10.3.0-SNAPSHOT.jar
java -jar openmall-merchant/target/openmall-merchant-10.3.0-SNAPSHOT.jar
java -jar openmall-platform/target/openmall-platform-10.3.0-SNAPSHOT.jar
```

## 4. 访问系统

启动成功后，可以通过以下地址访问各模块：

| 模块 | 访问地址 | 说明 |
|------|---------|------|
| 前台商城 | http://localhost:8081 | 买家购物界面 |
| 商家后台 | http://localhost:8082/merchant | 商家管理界面 |
| 平台管理 | http://localhost:8083/platform | 平台管理界面 |

## 5. 默认账号

### 平台管理员
- 用户名: `admin`
- 密码: `admin123`

### 测试买家
- 用户名: `buyer1`
- 密码: `admin123`

### 测试商家
- 用户名: `merchant1`
- 密码: `admin123`

## 6. 常见问题

### 6.1 编译失败

**问题**: Maven 编译时出现错误

**解决方案**:
```bash
# 清理 Maven 缓存
mvn clean

# 重新编译
mvn install -U
```

### 6.2 数据库连接失败

**问题**: 启动时提示数据库连接失败

**解决方案**:
1. 检查 MySQL 服务是否启动
2. 检查 `application.yml` 中的数据库配置是否正确
3. 确认数据库用户名和密码正确
4. 确认数据库已创建

### 6.3 Redis 连接失败

**问题**: 启动时提示 Redis 连接失败

**解决方案**:
1. 检查 Redis 服务是否启动
2. 检查 `application.yml` 中的 Redis 配置是否正确
3. 确认 Redis 端口（默认 6379）未被占用

### 6.4 端口被占用

**问题**: 启动时提示端口已被占用

**解决方案**:
```bash
# Windows 查看端口占用
netstat -ano | findstr 8081

# Linux/Mac 查看端口占用
lsof -i:8081

# 修改 application.yml 中的端口号
server:
  port: 8084  # 修改为其他端口
```

## 7. 开发调试

### 7.1 启用调试模式

在 `application.yml` 中添加：

```yaml
logging:
  level:
    com.qoobot.openmall: debug
    org.springframework.security: debug
```

### 7.2 热部署

添加 spring-boot-devtools 依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### 7.3 运行单元测试

```bash
# 运行所有测试
mvn test

# 运行指定模块测试
cd openmall-portal
mvn test
```

## 8. 部署

### 8.1 打包

```bash
mvn clean package -DskipTests
```

### 8.2 部署到服务器

```bash
# 上传 JAR 文件到服务器
scp openmall-portal/target/openmall-portal-10.3.0-SNAPSHOT.jar user@server:/path/to/deploy/

# 使用 nohup 后台运行
nohup java -jar openmall-portal-10.3.0-SNAPSHOT.jar > app.log 2>&1 &
```

### 8.3 使用 Docker 部署

创建 `Dockerfile`:

```dockerfile
FROM openjdk:21-jdk-slim
VOLUME /tmp
ADD openmall-portal-10.3.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

构建并运行：

```bash
docker build -t openmall-portal .
docker run -p 8081:8081 openmall-portal
```

## 9. 性能优化建议

### 9.1 数据库优化
- 为常用查询字段添加索引
- 使用连接池优化连接管理
- 定期清理过期数据

### 9.2 缓存优化
- 使用 Redis 缓存热点数据
- 设置合理的缓存过期时间
- 使用缓存预热策略

### 9.3 应用优化
- 启用 Virtual Threads（JDK 21特性）
- 使用异步处理提高吞吐量
- 合理配置线程池

## 10. 更多资源

- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Thymeleaf 官方文档](https://www.thymeleaf.org/)
- [Spring Security 官方文档](https://spring.io/projects/spring-security)
- [MySQL 官方文档](https://dev.mysql.com/doc/)
- [Redis 官方文档](https://redis.io/documentation)
