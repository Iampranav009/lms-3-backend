@echo off
echo ========================================
echo    Java Library Management API Server
echo ========================================
echo.
echo Starting Java API Server...
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK 8 or higher
    echo Download from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

REM Compile Java files
echo Compiling Java files...
javac LibraryManagement.java SimpleJavaAPI.java
if %errorlevel% neq 0 (
    echo ERROR: Failed to compile Java files
    echo Please check for compilation errors
    pause
    exit /b 1
)

echo.
echo ✅ Java files compiled successfully!
echo.
echo 🚀 Starting Java API Server...
echo 📡 Server will run on: http://localhost:8000
echo.
echo 💡 Your web application will automatically detect and use the Java API
echo 🔄 Press Ctrl+C to stop the server
echo.

REM Start the Java API server
java SimpleJavaAPI

pause
