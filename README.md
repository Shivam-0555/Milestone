# Milestone Project

## Overview

**MILESTONE** is a hackathon‑style web application that turns real‑life goals into an RPG‑style progression system. Users can create quests, earn XP and coins, level up, and spend rewards in a shop.

The repository is a monorepo containing:
- **backend/** – Spring Boot 3.x API (Java 17) with JWT security, JPA, MySQL persistence.
- **frontend/** – Next.js (React, TypeScript) starter generated with `create‑next‑app`. Tailwind CSS will be added in the next phase.

## Getting Started

### Backend
```bash
cd backend
./mvnw spring-boot:run
```
The API runs on **http://localhost:8080**.

### Frontend
```bash
cd frontend
npm install
npm run dev
```
The UI runs on **http://localhost:3000**.

## Environment Variables
Create a `.env` file (or copy `.env.example`) with the following variables:
```
# Backend
DB_HOST=localhost
DB_PORT=3306
DB_NAME=milestone
DB_USERNAME=root
DB_PASSWORD=yourpassword
JWT_SECRET=YourSuperSecretKey

# Frontend (if needed later)
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

---

*This is the initial scaffold; further features, security, and UI will be added in subsequent phases.*
