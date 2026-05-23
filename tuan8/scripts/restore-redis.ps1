# Redis Restore Script for Windows (PowerShell)
# Imports Redis data from JSON backup file

param(
    [Parameter(Mandatory=$true)]
    [string]$BackupFile,
    [string]$RedisHost = "localhost",
    [int]$RedisPort = 6379,
    [string]$RedisPassword = "",
    [int]$RedisDB = 0,
    [switch]$Force
)

Write-Host "🔴 Redis Restore Script" -ForegroundColor Yellow
Write-Host "Host: $($RedisHost):$RedisPort"
Write-Host "DB: $RedisDB"
Write-Host "Backup file: $BackupFile"
Write-Host ""

# Check backup file exists
if (-not (Test-Path $BackupFile)) {
    Write-Host "✗ Backup file not found: $BackupFile" -ForegroundColor Red
    exit 1
}

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

# Warn about data loss
if (-not $Force) {
    Write-Host "⚠️  WARNING: This will overwrite existing data in Redis!" -ForegroundColor Yellow
    $response = Read-Host "Continue? (y/N)"
    if ($response -notmatch '^[Yy]$') {
        Write-Host "Cancelled."
        exit 0
    }
}

Write-Host ""
Write-Host "Restoring data..." -ForegroundColor Yellow

# Read backup file
try {
    $backup = Get-Content $BackupFile -Raw | ConvertFrom-Json
} catch {
    Write-Host "✗ Error reading backup file: $_" -ForegroundColor Red
    exit 1
}

# Restore each key
$restoreCount = 0
foreach ($keyName in $backup.keys.PSObject.Properties.Name) {
    $keyData = $backup.keys.$keyName
    $value = $keyData.value
    $ttl = $keyData.ttl
    
    try {
        if ($ttl -gt 0) {
            & $redisCli @cliArgs SET $keyName $value EX $ttl | Out-Null
        } else {
            & $redisCli @cliArgs SET $keyName $value | Out-Null
        }
        Write-Host "✓ Restored: $keyName" -ForegroundColor Green
        $restoreCount++
    } catch {
        Write-Host "✗ Error restoring $keyName : $_" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "✓ Restore completed!" -ForegroundColor Green
Write-Host "Keys restored: $restoreCount"
Write-Host ""
