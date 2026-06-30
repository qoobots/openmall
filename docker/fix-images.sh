#!/bin/bash

echo "=== OpenMall Docker 镜像修复脚本 ==="

# 1. 清理损坏的镜像
echo "清理可能损坏的镜像..."
docker image prune -f

# 2. 拉取指定的稳定版本镜像
echo "拉取稳定的MySQL镜像..."
docker pull mysql:8.0-debian

echo "拉取稳定的Redis镜像..."
docker pull redis:7.0-alpine

# 3. 验证镜像是否可用
echo "验证镜像..."
docker run --rm mysql:8.0-debian mysql --version
docker run --rm redis:7.0-alpine redis-server --version

echo "镜像修复完成！现在可以尝试运行 docker-compose up"