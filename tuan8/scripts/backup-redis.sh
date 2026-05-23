#!/bin/bash

# Redis Backup Script (Linux/Mac)
# Exports all Redis data to JSON file

REDIS_HOST=${REDIS_HOST:-localhost}
REDIS_PORT=${REDIS_PORT:-6379}
REDIS_PASSWORD=${REDIS_PASSWORD:-}
REDIS_DB=${REDIS_DB:-0}
BACKUP_DIR=${BACKUP_DIR:-.}
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_FILE="$BACKUP_DIR/redis-backup_$TIMESTAMP.json"

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${YELLOW}🔴 Redis Backup Script${NC}"
echo "Host: $REDIS_HOST:$REDIS_PORT"
echo "DB: $REDIS_DB"
echo "Output: $BACKUP_FILE"
echo ""

# Build redis-cli command
REDIS_CMD="redis-cli -h $REDIS_HOST -p $REDIS_PORT -n $REDIS_DB"
if [ -n "$REDIS_PASSWORD" ]; then
  REDIS_CMD="$REDIS_CMD -a $REDIS_PASSWORD"
fi

# Check Redis connection
echo -e "${YELLOW}Testing Redis connection...${NC}"
if ! $REDIS_CMD ping > /dev/null 2>&1; then
  echo -e "${RED}✗ Cannot connect to Redis${NC}"
  exit 1
fi
echo -e "${GREEN}✓ Connected to Redis${NC}"
echo ""

# Get all keys
echo -e "${YELLOW}Fetching all keys...${NC}"
KEYS=$($REDIS_CMD KEYS "*")

if [ -z "$KEYS" ]; then
  echo -e "${RED}✗ No keys found in Redis${NC}"
  exit 1
fi

# Count keys
KEY_COUNT=$(echo "$KEYS" | wc -l)
echo -e "${GREEN}✓ Found $KEY_COUNT keys${NC}"
echo ""

# Start JSON export
echo -e "${YELLOW}Exporting data...${NC}"

# Create backup directory if not exists
mkdir -p "$BACKUP_DIR"

# Start JSON object
echo "{" > "$BACKUP_FILE"
echo '  "timestamp": "'$(date)'\",' >> "$BACKUP_FILE"
echo '  "host": "'$REDIS_HOST:$REDIS_PORT'",' >> "$BACKUP_FILE"
echo '  "db": '$REDIS_DB',' >> "$BACKUP_FILE"
echo '  "keys": {' >> "$BACKUP_FILE"

# Export each key
FIRST=true
echo "$KEYS" | while read key; do
  if [ -z "$key" ]; then
    continue
  fi
  
  VALUE=$($REDIS_CMD GET "$key" | sed 's/$/\\/' | sed 's/"/\\"/g')
  TTL=$($REDIS_CMD TTL "$key")
  
  if [ "$FIRST" = false ]; then
    echo "," >> "$BACKUP_FILE"
  fi
  
  printf '    "%s": {"value": "%s", "ttl": %d}' "$key" "$VALUE" "$TTL" >> "$BACKUP_FILE"
  FIRST=false
  
  echo -e "${GREEN}✓ Exported: $key${NC}"
done

# Close JSON object
echo "" >> "$BACKUP_FILE"
echo "  }" >> "$BACKUP_FILE"
echo "}" >> "$BACKUP_FILE"

echo ""
echo -e "${GREEN}✓ Backup completed!${NC}"
echo "File: $BACKUP_FILE"
echo "Keys exported: $KEY_COUNT"
echo ""
