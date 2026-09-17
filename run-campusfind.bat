@echo off
title CampusFind

cd /d "%~dp0"

set "MAVEN_BIN=C:\Program Files\Apache NetBeans\java\maven\bin"

if not exist "%MAVEN_BIN%\mvn.cmd" (
    echo Maven was not found.
    pause
    exit /b 1
)

echo Starting CampusFind...
echo.

call "%MAVEN_BIN%\mvn.cmd" compile exec:java

echo.
pause