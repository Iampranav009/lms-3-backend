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

## Deployment Options

### Option 1: Deploy to Render (Recommended - Free)
1. Go to [render.com](https://render.com) and sign up
2. Click "New +" → "Web Service"
3. Connect your GitHub repository: `https://github.com/Iampranav009/lms-3-backend`
4. Configure:
   - **Name**: lms-backend
   - **Runtime**: Docker
   - **Branch**: main
5. Click "Create Web Service"
6. Your API will be live at: `https://lms-backend-XXXX.onrender.com`

### Option 2: Deploy to Railway (Easy - Free Tier)
1. Go to [railway.app](https://railway.app)
2. Click "Start a New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Railway will auto-detect the Dockerfile and deploy
5. Get your URL from the Railway dashboard

### Option 3: Deploy to Heroku
1. Install Heroku CLI
2. Run:
```bash
heroku login
heroku create lms-backend
heroku stack:set container
git push heroku main
```

### Option 4: Deploy to AWS/Azure/GCP
Use the provided Dockerfile to deploy as a container service

## Why Not Vercel?
⚠️ **Vercel doesn't support Java applications.** Vercel is designed for:
- Frontend applications (React, Next.js, Vue, etc.)
- Serverless functions (Node.js, Python, Go, Ruby)

Java applications need platforms like Render, Railway, or Heroku that support long-running server processes.

