# Task Manager REST API

A REST API to create, view, update, search and delete tasks.
Built with **Java 17, Spring Boot 3, Spring Data JPA (Hibernate), MySQL and Maven**.

## Features
- CRUD endpoints for tasks (controller -> service -> repository layers)
- Input validation with clear error messages (Bean Validation)
- Global exception handling with proper HTTP status codes (400, 404, 500)
- Filter by status and search by title
- DTOs to keep the API separate from the database entity
- Unit tests with JUnit 5 and Mockito

## Run it
1. Install Java 17+, Maven and MySQL.
2. Open `src/main/resources/application.properties` and set your MySQL username and password.
   The database `taskdb` is created automatically.
3. Start the app:
   ```
   mvn spring-boot:run
   ```
4. API runs at http://localhost:8080/api/tasks
5. Run the tests: `mvn test`

## Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/tasks | Create a task |
| GET | /api/tasks | List tasks (optional `?status=PENDING` and `?keyword=java`) |
| GET | /api/tasks/{id} | Get one task |
| PUT | /api/tasks/{id} | Update a task |
| DELETE | /api/tasks/{id} | Delete a task |

Status values: `PENDING`, `IN_PROGRESS`, `COMPLETED`

## Sample requests (Postman or curl)
Create:
```
curl -X POST http://localhost:8080/api/tasks -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","description":"Build REST API","dueDate":"2026-11-15"}'
```
Filter:
```
curl "http://localhost:8080/api/tasks?status=PENDING"
```
Update:
```
curl -X PUT http://localhost:8080/api/tasks/1 -H "Content-Type: application/json" \
  -d '{"title":"Learn Spring Boot","status":"COMPLETED"}'
```
Validation error example (empty title) returns 400:
```
{"status":400,"error":"Bad Request","message":"Validation failed","fieldErrors":{"title":"Title is required"}}
```

## Project structure
```
src/main/java/com/prerana/taskmanager
  controller/   TaskController
  service/      TaskService
  repository/   TaskRepository
  model/        Task, TaskStatus
  dto/          TaskRequest, TaskResponse
  exception/    GlobalExceptionHandler, ResourceNotFoundException, ErrorResponse
```
