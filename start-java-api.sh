#!/bin/bash

echo "========================================"
echo "   Java Library Management API Server"
echo "========================================"
echo
echo "Starting Java API Server..."
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java JDK 8 or higher"
    echo "Ubuntu/Debian: sudo apt install openjdk-11-jdk"
    echo "macOS: brew install openjdk@11"
    exit 1
fi

# Compile Java files
echo "Compiling Java files..."
javac LibraryManagement.java SimpleJavaAPI.java
if [ $? -ne 0 ]; then
    echo "ERROR: Failed to compile Java files"
    echo "Please check for compilation errors"
    exit 1
fi

echo
echo "✅ Java files compiled successfully!"
echo
echo "🚀 Starting Java API Server..."
echo "📡 Server will run on: http://localhost:8000"
echo
echo "💡 Your web application will automatically detect and use the Java API"
echo "🔄 Press Ctrl+C to stop the server"
echo

# Start the Java API server
java SimpleJavaAPI
