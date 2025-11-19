# Rental Car Platform

This repository hosts the Spring Boot backend and the Angular dashboard for the rental car system. Use the backend to expose the REST API (cars, reservations, balances) and the Angular client under `frontend/` for a browser UI.

## Backend
1. Install Java 17+ and Maven (or rely on the provided wrapper).
2. From the repository root run:
   ```bash
   ./mvnw spring-boot:run
   ```
3. The API becomes available on `http://localhost:8080`.

### API documentation (Swagger UI)
The project uses `springdoc-openapi`. Once the backend is running you can browse the interactive documentation at:

`http://localhost:8080/swagger-ui/index.html`

### Tests
Run the backend test suite with:
```bash
./mvnw test
```

## Frontend (Angular)
1. Change into the `frontend/` directory and install dependencies:
   ```bash
   cd frontend
   npm install
   ```
2. Start the dev server with:
   ```bash
   npm start
   ```
   This proxies API calls to `http://localhost:8080` via `proxy.conf.json`.
3. Build the production bundle with:
   ```bash
   npm run build
   ```
