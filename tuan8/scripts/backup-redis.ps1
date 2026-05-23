# Redis Backup Script for Windows (PowerShell)
# Exports all Redis data to JSON file

param(
    [string]$RedisHost = "localhost",
    [int]$RedisPort = 6379,
    [string]$RedisPassword = "",
    [int]$RedisDB = 0,
    [string]$BackupDir = "."
)

$Timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
$BackupFile = Join-Path $BackupDir "redis-backup_$Timestamp.json"

Write-Host "🔴 Redis Backup Script" -ForegroundColor Yellow
Write-Host "Host: $($RedisHost):$RedisPort"
Write-Host "DB: $RedisDB"
Write-Host "Output: $BackupFile"
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
        Write-Host "✗ Cannot connect to Redis" -ForegroundColor Red
        exit 1
    }
} catch {
    Write-Host "✗ Redis CLI not found or error occurred" -ForegroundColor Red
    exit 1
}
Write-Host ""

# Get all keys
Write-Host "Fetching all keys..." -ForegroundColor Yellow
$Keys = & $redisCli @cliArgs KEYS "*"

if ($null -eq $Keys -or $Keys.Count -eq 0) {
    Write-Host "✗ No keys found in Redis" -ForegroundColor Red
    exit 1
}

$KeyCount = $Keys.Count
Write-Host "✓ Found $KeyCount keys" -ForegroundColor Green
Write-Host ""

# Create backup directory if not exists
if (-not (Test-Path $BackupDir)) {
    New-Item -ItemType Directory -Path $BackupDir | Out-Null
}

Write-Host "Exporting data..." -ForegroundColor Yellow

# Build JSON
$json = @{
    timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    host = "$($RedisHost):$RedisPort"
    db = $RedisDB
    keys = @{}
}

foreach ($key in $Keys) {
    $value = & $redisCli @cliArgs GET $key
    $ttl = & $redisCli @cliArgs TTL $key
    
    $json.keys[$key] = @{
        value = $value
        ttl = $ttl
    }
    
    Write-Host "✓ Exported: $key" -ForegroundColor Green
}

# Save to file
$json | ConvertTo-Json -Depth 10 | Out-File -FilePath $BackupFile -Encoding UTF8

Write-Host ""
Write-Host "✓ Backup completed!" -ForegroundColor Green
Write-Host "File: $BackupFile"
Write-Host "Keys exported: $KeyCount"
Write-Host ""
