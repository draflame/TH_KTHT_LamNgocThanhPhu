@echo off
REM 🚀 Script chạy tất cả services một lần (Windows)
REM Usage: start-all.bat

cls
echo ==========================================
echo 🚀 Travel Booking System - Start All
echo ==========================================
echo.

REM Hàm để chạy service trong background
setlocal enabledelayedexpansion

echo 📦 Starting services...
echo.

REM User Service
cd user-service
echo [1/6] User Service (Người 3) - Port 8081
call npm install >nul 2>&1
start /B cmd /c npm start
cd ..
timeout /t 2 /nobreak >nul

REM Tour Service
cd tour-service
echo [2/6] Tour Service (Người 4) - Port 8082
call npm install >nul 2>&1
start /B cmd /c npm start
cd ..
timeout /t 2 /nobreak >nul

REM Booking Service
cd booking-payment-service
echo [3/6] Booking Service (Người 5) - Port 8083
call npm install >nul 2>&1
start /B cmd /c npm run booking
timeout /t 2 /nobreak >nul
cd ..

REM Payment Service
cd booking-payment-service
echo [4/6] Payment Service (Người 5) - Port 8084
start /B cmd /c npm run payment
cd ..
timeout /t 2 /nobreak >nul

REM Orchestrator Service
cd orchestrator-service
echo [5/6] Orchestrator Service (Người 2) - Port 8080
call npm install >nul 2>&1
start /B cmd /c npm start
cd ..
timeout /t 2 /nobreak >nul

REM Frontend
cd frontend
echo [6/6] Frontend (Người 1) - Port 3000
call npm install >nul 2>&1
start /B cmd /c npm start
cd ..

echo.
echo ==========================================
echo ✅ All services started!
echo ==========================================
echo.
echo 🌐 Services available at:
echo    Frontend:      http://localhost:3000
echo    Orchestrator:  http://localhost:8080
echo    User Service:  http://localhost:8081
echo    Tour Service:  http://localhost:8082
echo    Booking:       http://localhost:8083
echo    Payment:       http://localhost:8084
echo.
echo 💡 Login with: user1 / 123456
echo.
echo Close this window to stop all services
echo ==========================================

pause
