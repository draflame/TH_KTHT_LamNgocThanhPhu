# Redis Data Initialization Script for Windows (PowerShell)
# This script imports sample data into Redis

param(
    [string]$RedisHost = "localhost",
    [int]$RedisPort = 6379,
    [string]$RedisPassword = "",
    [int]$RedisDB = 0
)

Write-Host "🔴 Redis Data Initialization Script" -ForegroundColor Yellow
Write-Host "Host: $($RedisHost):$RedisPort"
Write-Host "DB: $RedisDB"
Write-Host ""

# Build redis-cli command
$redisCli = "redis-cli"
$cliArgs = @("-h", $RedisHost, "-p", $RedisPort, "-n", $RedisDB)

if (-not [string]::IsNullOrEmpty($RedisPassword)) {
    $cliArgs += @("-a", $RedisPassword)
}

# Check Redis connection
Write-Host "Testing Redis connection..." -ForegroundColor Yellow
try {
    $result = & $redisCli @cliArgs PING 2>$null
    if ($result -eq "PONG") {
        Write-Host "✓ Connected to Redis" -ForegroundColor Green
    } else {
        Write-Host "✗ Cannot connect to Redis at $($RedisHost):$RedisPort" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "✗ Redis CLI not found. Install Redis or add it to PATH." -ForegroundColor Red
    exit 1
}
Write-Host ""

# Function to set key-value pair
function Set-RedisKey {
    param(
        [string]$Key,
        [string]$Value,
        [int]$TTL = 0
    )
    
    if ($TTL -eq 0) {
        & $redisCli @cliArgs SET $Key $Value | Out-Null
    } else {
        & $redisCli @cliArgs SET $Key $Value EX $TTL | Out-Null
    }
}

# 30 days in seconds
$TTL = 2592000

# ===== PRODUCTS =====
Write-Host "Importing Products..." -ForegroundColor Yellow

Set-RedisKey "product:P001" '{"id":"P001","name":"Laptop Dell XPS 13","price":1299.99,"category":"Electronics"}' $TTL
Write-Host "✓ P001 - Laptop Dell XPS 13 (1299.99)" -ForegroundColor Green

Set-RedisKey "product:P002" '{"id":"P002","name":"iPhone 15 Pro","price":999.99,"category":"Electronics"}' $TTL
Write-Host "✓ P002 - iPhone 15 Pro (999.99)" -ForegroundColor Green

Set-RedisKey "product:P003" '{"id":"P003","name":"Samsung Galaxy Watch","price":349.99,"category":"Wearables"}' $TTL
Write-Host "✓ P003 - Samsung Galaxy Watch (349.99)" -ForegroundColor Green

Set-RedisKey "product:P004" '{"id":"P004","name":"AirPods Pro","price":249.99,"category":"Audio"}' $TTL
Write-Host "✓ P004 - AirPods Pro (249.99)" -ForegroundColor Green

Set-RedisKey "product:P005" '{"id":"P005","name":"Sony WH-1000XM5 Headphones","price":399.99,"category":"Audio"}' $TTL
Write-Host "✓ P005 - Sony WH-1000XM5 Headphones (399.99)" -ForegroundColor Green

Write-Host ""

# ===== INVENTORY =====
Write-Host "Importing Inventory..." -ForegroundColor Yellow

Set-RedisKey "inventory:P001" '{"productId":"P001","productName":"Laptop Dell XPS 13","stock":50}' $TTL
Write-Host "✓ P001 Inventory: 50 units" -ForegroundColor Green

Set-RedisKey "inventory:P002" '{"productId":"P002","productName":"iPhone 15 Pro","stock":100}' $TTL
Write-Host "✓ P002 Inventory: 100 units" -ForegroundColor Green

Set-RedisKey "inventory:P003" '{"productId":"P003","productName":"Samsung Galaxy Watch","stock":75}' $TTL
Write-Host "✓ P003 Inventory: 75 units" -ForegroundColor Green

Set-RedisKey "inventory:P004" '{"productId":"P004","productName":"AirPods Pro","stock":200}' $TTL
Write-Host "✓ P004 Inventory: 200 units" -ForegroundColor Green

Set-RedisKey "inventory:P005" '{"productId":"P005","productName":"Sony WH-1000XM5 Headphones","stock":60}' $TTL
Write-Host "✓ P005 Inventory: 60 units" -ForegroundColor Green

Write-Host ""

# Verify data
Write-Host "Verifying imported data..." -ForegroundColor Yellow

$productCount = @(& $redisCli @cliArgs KEYS "product:*").Count
$inventoryCount = @(& $redisCli @cliArgs KEYS "inventory:*").Count

Write-Host "✓ Products in Redis: $productCount" -ForegroundColor Green
Write-Host "✓ Inventory records in Redis: $inventoryCount" -ForegroundColor Green

Write-Host ""
Write-Host "✓ All data imported successfully!" -ForegroundColor Green
Write-Host ""

Write-Host "Sample Redis keys:" -ForegroundColor Yellow
& $redisCli @cliArgs KEYS "*" | Select-Object -First 10

Write-Host ""
Write-Host "Sample product data:" -ForegroundColor Yellow
& $redisCli @cliArgs GET "product:P001"

Write-Host ""
