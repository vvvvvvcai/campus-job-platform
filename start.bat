@echo off
chcp 65001 >nul
title 一键启动 - 校园就业平台

echo ============================================
echo   校园就业综合服务平台 - 一键启动
echo ============================================
echo.

set ROOT=%~dp0

:: ==================== 启动 Redis ====================
echo [1/4] 启动 Redis...
start "Redis" cmd /k "cd /d %ROOT%redis && redis-server.exe redis.windows.conf"
timeout /t 2 /nobreak >nul
echo       Redis 已启动
echo.

:: ==================== 启动 Nacos ====================
echo [2/4] 启动 Nacos...
start "Nacos" cmd /k "cd /d %ROOT%nacos\nacos\bin && startup.cmd -m standalone"
echo       等待 Nacos 就绪（约 15 秒）...
timeout /t 15 /nobreak >nul
echo       Nacos 已启动
echo.

:: ==================== 启动后端微服务 ====================
echo [3/4] 启动后端微服务...
start "gateway-service" cmd /k "cd /d %ROOT%campus-job-platform && java -jar gateway-service\target\gateway-service-1.0.0.jar"
timeout /t 3 /nobreak >nul
start "user-service" cmd /k "cd /d %ROOT%campus-job-platform && java -jar user-service\target\user-service-1.0.0.jar"
timeout /t 3 /nobreak >nul
start "company-service" cmd /k "cd /d %ROOT%campus-job-platform && java -jar company-service\target\company-service-1.0.0.jar"
timeout /t 3 /nobreak >nul
start "job-service" cmd /k "cd /d %ROOT%campus-job-platform && java -jar job-service\target\job-service-1.0.0.jar"
timeout /t 3 /nobreak >nul
start "application-service" cmd /k "cd /d %ROOT%campus-job-platform && java -jar application-service\target\application-service-1.0.0.jar"
timeout /t 3 /nobreak >nul
start "recommend-service" cmd /k "cd /d %ROOT%campus-job-platform && java -jar recommend-service\target\recommend-service-1.0.0.jar"
timeout /t 5 /nobreak >nul
echo       6 个微服务已启动
echo.

:: ==================== 启动前端 ====================
echo [4/4] 启动前端...
start "student-frontend" cmd /k "cd /d %ROOT%fronted && npm run dev"
start "admin-frontend" cmd /k "cd /d %ROOT%admin-frontend && npm run dev"
echo       前端已启动
echo.

echo ============================================
echo   全部启动完成！
echo   学生端:  http://localhost:5173
echo   管理端:  http://localhost:5173
echo   网关:    http://localhost:9000
echo   Nacos:   http://localhost:8848/nacos
echo ============================================
echo.
echo 按任意键退出此窗口（服务在其他窗口运行）
pause >nul
