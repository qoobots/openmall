@echo off
setlocal
echo === OpenMall Docker Deployment Script ===

if "%1"=="" goto :help
if "%1"=="build" goto :build
if "%1"=="start" goto :start
if "%1"=="stop" goto :stop
if "%1"=="restart" goto :restart
if "%1"=="logs" goto :logs
if "%1"=="status" goto :status
if "%1"=="cleanup" goto :cleanup
if "%1"=="help" goto :help

echo Error: Unknown option %1
goto :help

:build
echo Starting project and Docker image build...
cd ..
call mvn clean package -DskipTests
if errorlevel 1 (
    echo Project build failed
    exit /b 1
)
cd docker
docker build -t openmall/portal:latest -f Dockerfile ..
docker build -t openmall/merchant:latest -f Dockerfile.merchant ..
docker build -t openmall/platform:latest -f Dockerfile.platform ..
echo Build completed
exit /b 0

:start
echo Starting Docker services...
docker-compose down
docker-compose up -d
echo Services started successfully
docker-compose ps
exit /b 0

:stop
echo Stopping Docker services...
docker-compose down
echo Services stopped
exit /b 0

:restart
call :stop
call :start
exit /b 0

:logs
echo Viewing service logs...
if "%2"=="" (
    docker-compose logs -f
) else (
    docker-compose logs -f %2
)
exit /b 0

:status
echo Service status:
docker-compose ps
exit /b 0

:cleanup
echo Cleaning up Docker resources...
docker-compose down -v
docker rmi openmall/portal:latest openmall/merchant:latest openmall/platform:latest 2>nul
docker system prune -f
echo Resource cleanup completed
exit /b 0

:help
echo OpenMall Docker Deployment Script
echo.
echo Usage: deploy.bat [option]
echo.
echo Options:
echo   build     Build project and Docker images
echo   start     Start all services
echo   stop      Stop all services
echo   restart   Restart all services
echo   logs      View service logs
echo   logs [service]  View specific service logs
echo   status    View service status
echo   cleanup   Clean up all resources
echo   help      Show this help information
echo.
echo Examples:
echo   deploy.bat build
echo   deploy.bat start
echo   deploy.bat logs portal
exit /b 0