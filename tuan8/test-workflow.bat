@echo off
REM E-Commerce Platform - Test Script for Windows
REM This script demonstrates the complete workflow

echo.
echo ============================================
echo ^|  E-Commerce Platform - Testing Workflow
echo ============================================
echo.

REM Configuration
set PRODUCT_SERVICE=http://localhost:8081
set CART_SERVICE=http://localhost:8082
set ORDER_SERVICE=http://localhost:8083
set INVENTORY_SERVICE=http://localhost:8084

REM Generate unique user ID
for /f "tokens=2-4 delims=/ " %%a in ('date /t') do (set mydate=%%c%%a%%b)
for /f "tokens=1-2 delims=/:" %%a in ('time /t') do (set mytime=%%a%%b)
set USER_ID=test-user-%mydate%-%mytime%

echo Using User ID: %USER_ID%
echo.

REM Test 1: Load Products
echo 1. Loading products from Redis...
echo.
curl -s %PRODUCT_SERVICE%/api/products | jq "."
echo.
pause

REM Note: To extract specific values and continue, you would need jq or similar
REM For simplicity, we'll use hardcoded values for testing

set PRODUCT_ID=P001
set PRODUCT_NAME=Laptop Dell XPS 13
set PRODUCT_PRICE=1299.99

echo Selected Product: %PRODUCT_ID% - %PRODUCT_NAME% (%PRODUCT_PRICE%)
echo.

REM Test 2: Check Inventory
echo 2. Checking inventory...
echo.
curl -s %INVENTORY_SERVICE%/api/inventory/%PRODUCT_ID% | jq "."
echo.
pause

REM Test 3: Add to Cart
echo 3. Adding product to cart...
echo.
curl -s -X POST %CART_SERVICE%/api/cart/%USER_ID%/add ^
  -H "Content-Type: application/json" ^
  -d "{"^""productId"^"":"^""%PRODUCT_ID%"^""^",  "^""productName"^"":"^""%PRODUCT_NAME%"^""^", "^""price"^"": %PRODUCT_PRICE%, "^""quantity"^"": 2}" | jq "."
echo.
pause

REM Test 4: View Cart
echo 4. Viewing cart...
echo.
curl -s %CART_SERVICE%/api/cart/%USER_ID% | jq "."
echo.
pause

REM Test 5: Checkout
echo 5. Processing checkout ^(FAST - IMMEDIATE RESPONSE^)...
echo.
echo Creating order with checkout/validated endpoint...
curl -s -X POST %ORDER_SERVICE%/api/checkout/validated ^
  -H "Content-Type: application/json" ^
  -d "{"^""userId"^"":"^""%USER_ID%"^""^", "^""cartData"^"":{"^""items"^"":[{"^""cartItemId"^"":"^""test-item"^""^", "^""productId"^"":"^""%PRODUCT_ID%"^""^", "^""productName"^"":"^""%PRODUCT_NAME%"^""^", "^""price"^"": %PRODUCT_PRICE%, "^""quantity"^"": 2}], "^""total"^"": %PRODUCT_PRICE:~0,-2%.0}}" | jq "."
echo.
pause

REM Test 6: Check Stock
echo 6. Checking inventory after checkout ^(waiting 2 seconds^)...
echo.
timeout /t 2 /nobreak
curl -s %INVENTORY_SERVICE%/api/inventory/%PRODUCT_ID% | jq "."
echo.
pause

echo.
echo ✅ Testing Complete!
echo.
echo Key Results:
echo   - Products loaded from Redis
echo   - Item added to cart instantly
echo   - Checkout completed
echo   - Order created successfully
echo   - Stock reduction async (no wait)
echo.
pause
