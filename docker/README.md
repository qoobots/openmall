# OpenMall Docker Configuration

## Directory Structure
```
docker/
├── Dockerfile              # Portal service Dockerfile
├── Dockerfile.merchant     # Merchant service Dockerfile
├── Dockerfile.platform     # Platform service Dockerfile
├── docker-compose.yml      # Docker Compose configuration file
├── deploy.sh              # Linux/Mac deployment script
├── deploy.bat             # Windows deployment script
└── README.md              # This file
```

## Quick Start

### 1. Build and Start Services

**Linux/Mac:**
```bash
cd docker
./deploy.sh build
./deploy.sh start
```

**Windows:**
```cmd
cd docker
deploy.bat build
deploy.bat start
```

### 2. Check Service Status
```bash
./deploy.sh status
# Or
deploy.bat status
```

### 3. View Logs
```bash
./deploy.sh logs          # View all service logs
./deploy.sh logs portal   # View specific service logs
# Or
deploy.bat logs portal
```

### 4. Stop Services
```bash
./deploy.sh stop
# Or
deploy.bat stop
```

## Service Access Addresses

- **Portal Application**: http://localhost:8080
- **Merchant Application**: http://localhost:8081
- **Platform Application**: http://localhost:8082
- **MySQL Database**: localhost:3306
- **Redis Cache**: localhost:6379

## Database Configuration

Default database connection information:
- **Host**: localhost
- **Port**: 3306
- **Database**: openmall
- **Username**: openmall
- **Password**: openmall123
- **Root Password**: root123

## Environment Variables

Customize configuration through environment variables:

```bash
export DB_HOST=mysql
export REDIS_HOST=redis
export SPRING_PROFILES_ACTIVE=docker
```

## Troubleshooting

### 1. Port Conflicts
If ports are occupied, you can modify port mapping in docker-compose.yml:

```yaml
ports:
  - "8080:8080"  # Modify to another port, such as "8083:8080"
```

### 2. Build Failures
Ensure the following are installed:
- Docker
- Docker Compose
- Maven
- JDK 21

### 3. Data Persistence
Data volumes are automatically created and persisted:
- MySQL data stored in `mysql_data` volume
- Redis data stored in `redis_data` volume

### 4. Resource Cleanup
```bash
./deploy.sh cleanup
# Or
deploy.bat cleanup
```

## Custom Configuration

### Modify JVM Parameters
Modify in Dockerfile:
```dockerfile
ENV JAVA_OPTS="-Xmx1g -Xms512m"
```

### Add New Services
1. Create corresponding Dockerfile
2. Add service configuration in docker-compose.yml
3. Update deployment scripts

## Monitoring and Maintenance

### View Resource Usage
```bash
docker stats
```

### View Container Details
```bash
docker inspect openmall-portal
```

### Backup Data
```bash
# Backup MySQL data
docker exec openmall-mysql mysqldump -u openmall -popenmall123 openmall > backup.sql

# Backup Redis data
docker exec openmall-redis redis-cli SAVE
```