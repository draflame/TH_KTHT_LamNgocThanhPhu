#!/bin/bash

# 🚀 Script chạy tất cả services một lần
# Usage: bash start-all.sh

echo "=========================================="
echo "🚀 Travel Booking System - Start All"
echo "=========================================="
echo ""

# Hàm để chạy service trong background
run_service() {
    local name=$1
    local dir=$2
    local port=$3
    local cmd=$4
    
    echo "📦 Starting $name (Port $port)..."
    cd $dir
    npm install >/dev/null 2>&1
    npm $cmd &
    echo "   ✅ Started"
    cd ..
    sleep 2
}

# Chạy các services
run_service "User Service (Người 3)" "user-service" "8081" "start"
run_service "Tour Service (Người 4)" "tour-service" "8082" "start"
run_service "Booking Service (Người 5)" "booking-payment-service" "8083" "run booking"
run_service "Payment Service (Người 5)" "booking-payment-service" "8084" "run payment"
run_service "Orchestrator Service (Người 2)" "orchestrator-service" "8080" "start"
run_service "Frontend (Người 1)" "frontend" "3000" "start"

echo ""
echo "=========================================="
echo "✅ All services started!"
echo "=========================================="
echo ""
echo "🌐 Services available at:"
echo "   Frontend:      http://localhost:3000"
echo "   Orchestrator:  http://localhost:8080"
echo "   User Service:  http://localhost:8081"
echo "   Tour Service:  http://localhost:8082"
echo "   Booking:       http://localhost:8083"
echo "   Payment:       http://localhost:8084"
echo ""
echo "💡 Login with: user1 / 123456"
echo ""
echo "Type Ctrl+C to stop all services"
echo "=========================================="

# Giữ script chạy
wait
