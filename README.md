# LMS 3 — Backend

The Java backend / API for a Library Management System — the backend half of
the LMS whose frontend lives in `lms-3-frontend`.

## Screenshots

No screenshots are stored in this repo, and no live site is linked in the repo.
This is an API service, so screenshots are not applicable — see the API
endpoints in `lms-3-frontend`'s README for how the frontend uses it.

## What it does

- Library management API — books, students, issue / return
- Java REST API used by the `lms-3-frontend` static frontend, which falls back
  to Supabase when the Java API is not available

## Tech Stack

- Java
- MySQL (see `database-clean.sql`)
- Docker (`Dockerfile`), deployable via `render.yaml`

## Getting Started

Follow `DEPLOYMENT_GUIDE.md` in this repo for the full deployment and run
instructions. Start scripts are provided in the repo:

```bash
# Windows
start-java-api.bat
# macOS / Linux
./start-java-api.sh
```

## Project Structure

```
├── LibraryManagement.java
├── SimpleJavaAPI.java
├── database-clean.sql
├── Dockerfile
├── render.yaml
├── DEPLOYMENT_GUIDE.md
├── start-java-api.sh
└── start-java-api.bat
```
