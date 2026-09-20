@echo off
setlocal enabledelayedexpansion

if not exist .env (
    echo [ERROR] .env file not found!
    exit /b 1
)

for /f "usebackq delims=" %%x in (".env") do (
    set "line=%%x"
    if not "!line:~0,1!"=="#" (
        set "%%x"
    )
)

if "%REVISION%"=="" (
    echo [ERROR] REVISION variable is not set in the .env file!
    exit /b 1
)

set "IMAGE_ID="
for /f "delims=" %%i in ('docker images -q "saturday:%REVISION%" 2^>nul') do set "IMAGE_ID=%%i"

if "%IMAGE_ID%"=="" (
    echo Image not found. Loading from archive: saturday-%REVISION%.tar...
    docker load -i "saturday-%REVISION%.tar"

    if errorlevel 1 (
        echo [ERROR] Failed to load image via docker load!
        exit /b 1
    )
)

echo Starting containers...
docker compose up -d

if errorlevel 1 (
    echo [ERROR] Failed to start docker compose!
    exit /b 1
)

echo [SUCCESS] Script executed successfully.
endlocal
