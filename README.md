# lms-3-backend

Library Management System - Backend

## Description
This is the backend for the Library Management System, providing a Java-based REST API for managing books, students, and book issues.

## Files
- `LibraryManagement.java` - Core library management logic with data structures for books, students, and issues
- `SimpleJavaAPI.java` - REST API server implementation using HttpServer
- `database-clean.sql` - Database schema and initial data setup
- `start-java-api.bat` - Windows startup script
- `start-java-api.sh` - Linux/Mac startup script

## Requirements
- Java 11 or higher
- PostgreSQL (if using database)

## Running the Backend

### Windows
```bash
start-java-api.bat
```

### Linux/Mac
```bash
chmod +x start-java-api.sh
./start-java-api.sh
```

The API will start on `http://localhost:8080`

## API Endpoints
- `GET /api/books` - Get all books
- `GET /api/students` - Get all students
- `GET /api/issues` - Get all book issues
- `POST /api/books` - Add a new book
- `POST /api/students` - Add a new student
- `POST /api/issues` - Issue a book
- `PUT /api/issues/{id}/return` - Return a book

