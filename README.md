# Atipera Recruitment Task

A Spring Boot 3.5 application that integrates with the GitHub API to retrieve a user's non-fork repositories along with their branches and commit hashes.

## Features
- Fetch GitHub repositories excluding forks
- Retrieve repository branches with commit hashes
- Error handling for user not found and API errors
- Swagger UI for API documentation

## Tech Stack
- Java 21
- Spring Boot 3.5
- Gradle (Kotlin DSL)
- RestClient (Spring Web)
- Lombok
- SLF4J for logging

## Requirements
- Java 21
- Gradle 8+
- Internet connection (to access GitHub API)

## Running the Application

```bash
./gradlew bootRun
```

## Testing
Run tests with:
```bash
./gradlew test
```

## Swagger UI
Once the application is running, visit:
```
http://localhost:8080/swagger-ui.html
```

---
