#!/bin/bash

# Redis Restore Script (Linux/Mac)
# Imports Redis data from JSON backup file

REDIS_HOST=${REDIS_HOST:-localhost}
REDIS_PORT=${REDIS_PORT:-6379}
REDIS_PASSWORD=${REDIS_PASSWORD:-}
REDIS_DB=${REDIS_DB:-0}
BACKUP_FILE=${1:-.}

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

if [ ! -f "$BACKUP_FILE" ]; then
  echo -e "${RED}✗ Backup file not found: $BACKUP_FILE${NC}"
  echo "Usage: $0 <backup_file>"
  exit 1
fi

echo -e "${YELLOW}🔴 Redis Restore Script${NC}"
echo "Host: $REDIS_HOST:$REDIS_PORT"
echo "DB: $REDIS_DB"
echo "Backup file: $BACKUP_FILE"
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

# Warn about data loss
echo -e "${YELLOW}⚠️  WARNING: This will overwrite existing data in Redis!${NC}"
read -p "Continue? (y/N) " -n 1 -r
echo
if [[ ! $REPLY =~ ^[Yy]$ ]]; then
  echo "Cancelled."
  exit 1
fi
echo ""

# Parse JSON and restore (simplified approach using jq if available)
if command -v jq &> /dev/null; then
  echo -e "${YELLOW}Restoring data using jq...${NC}"
  jq -r '.keys | to_entries[] | .key' "$BACKUP_FILE" | while read key; do
    VALUE=$(jq -r '.keys["'$key'"].value' "$BACKUP_FILE")
    TTL=$(jq -r '.keys["'$key'"].ttl' "$BACKUP_FILE")
    
    if [ "$TTL" -gt 0 ]; then
      $REDIS_CMD SET "$key" "$VALUE" EX "$TTL" > /dev/null
    else
      $REDIS_CMD SET "$key" "$VALUE" > /dev/null
    fi
    
    echo -e "${GREEN}✓ Restored: $key${NC}"
  done
else
  echo -e "${YELLOW}jq not found. Using grep/awk fallback...${NC}"
  echo -e "${YELLOW}Note: TTL information will be lost${NC}"
  
  grep -oP '"[^"]+"\s*:\s*\{' "$BACKUP_FILE" | grep -v 'timestamp\|host\|db\|keys' | while read line; do
    KEY=$(echo "$line" | grep -oP '"[^"]+"' | head -1 | tr -d '"')
    echo "Processing: $KEY"
  done
fi

echo ""
echo -e "${GREEN}✓ Restore completed!${NC}"
echo ""
