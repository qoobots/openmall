#!/bin/bash

# OpenMall Docker Build and Deployment Script

set -e

# Color definitions
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Project root directory
PROJECT_ROOT=$(cd "$(dirname "$0")/.." && pwd)
DOCKER_DIR="$PROJECT_ROOT/docker"

echo -e "${GREEN}=== OpenMall Docker Deployment Script ===${NC}"

# Check Docker environment
check_docker() {
    echo -e "${YELLOW}Checking Docker environment...${NC}"
    if ! command -v docker &> /dev/null; then
        echo -e "${RED}Error: Docker not found, please install Docker first${NC}"
        exit 1
    fi
    
    if ! command -v docker-compose &> /dev/null; then
        echo -e "${RED}Error: docker-compose not found, please install docker-compose first${NC}"
        exit 1
    fi
    
    echo -e "${GREEN}✓ Docker environment check passed${NC}"
}

# Build project
build_project() {
    echo -e "${YELLOW}Starting Java project build...${NC}"
    cd "$PROJECT_ROOT"
    
    # Clean and build project
    mvn clean package -DskipTests
    
    echo -e "${GREEN}✓ Project build completed${NC}"
}

# Build Docker images
build_images() {
    echo -e "${YELLOW}Starting Docker image build...${NC}"
    cd "$DOCKER_DIR"
    
    # Build base image
    docker build -t openmall/base:latest -f Dockerfile.base .
    
    # Build service images
    docker build -t openmall/portal:latest -f Dockerfile ..
    docker build -t openmall/merchant:latest -f Dockerfile.merchant ..
    docker build -t openmall/platform:latest -f Dockerfile.platform ..
    
    echo -e "${GREEN}✓ Docker image build completed${NC}"
}

# Start services
start_services() {
    echo -e "${YELLOW}Starting Docker services...${NC}"
    cd "$DOCKER_DIR"
    
    # Stop existing services
    docker-compose down
    
    # Start all services
    docker-compose up -d
    
    echo -e "${GREEN}✓ Docker services started successfully${NC}"
    
    # Display service status
    echo -e "${YELLOW}Service status:${NC}"
    docker-compose ps
}

# View logs
show_logs() {
    echo -e "${YELLOW}Viewing service logs...${NC}"
    cd "$DOCKER_DIR"
    
    if [ "$1" ]; then
        docker-compose logs -f "$1"
    else
        docker-compose logs -f
    fi
}

# Stop services
stop_services() {
    echo -e "${YELLOW}Stopping Docker services...${NC}"
    cd "$DOCKER_DIR"
    
    docker-compose down
    
    echo -e "${GREEN}✓ Docker services stopped${NC}"
}

# Clean up resources
cleanup() {
    echo -e "${YELLOW}Cleaning up Docker resources...${NC}"
    cd "$DOCKER_DIR"
    
    # Stop and remove containers
    docker-compose down -v
    
    # Remove images
    docker rmi openmall/portal:latest openmall/merchant:latest openmall/platform:latest 2>/dev/null || true
    
    # Clean unused resources
    docker system prune -f
    
    echo -e "${GREEN}✓ Docker resource cleanup completed${NC}"
}

# Show help information
show_help() {
    echo "OpenMall Docker Deployment Script"
    echo ""
    echo "Usage: $0 [option]"
    echo ""
    echo "Options:"
    echo "  build     Build project and Docker images"
    echo "  start     Start all services"
    echo "  stop      Stop all services"
    echo "  restart   Restart all services"
    echo "  logs      View service logs"
    echo "  logs [service]  View specific service logs"
    echo "  status    View service status"
    echo "  cleanup   Clean up all resources"
    echo "  help      Show this help information"
    echo ""
    echo "Examples:"
    echo "  $0 build      # Build project"
    echo "  $0 start      # Start services"
    echo "  $0 logs portal # View portal service logs"
}

# Main logic
case "$1" in
    build)
        check_docker
        build_project
        build_images
        ;;
    start)
        check_docker
        start_services
        ;;
    stop)
        check_docker
        stop_services
        ;;
    restart)
        check_docker
        stop_services
        start_services
        ;;
    logs)
        check_docker
        show_logs "$2"
        ;;
    status)
        check_docker
        cd "$DOCKER_DIR"
        docker-compose ps
        ;;
    cleanup)
        check_docker
        cleanup
        ;;
    help|"")
        show_help
        ;;
    *)
        echo -e "${RED}Unknown option: $1${NC}"
        show_help
        exit 1
        ;;
esac