@echo off
setlocal

echo === OpenMall Docker Image Fix Script ===

REM 1. 清理损坏的镜像
echo Cleaning potentially corrupted images...
docker image prune -f

REM 2. 拉取指定的稳定版本镜像
echo Pulling stable MySQL image...
docker pull mysql:8.0-debian

echo Pulling stable Redis image...
docker pull redis:7.0-alpine

REM 3. 验证镜像是否可用
echo Verifying images...
docker run --rm mysql:8.0-debian mysql --version
docker run --rm redis:7.0-alpine redis-server --version

echo Image fix completed! You can now try running docker-compose up

pause