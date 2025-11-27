# Rental Car Angular UI

A lightweight Angular dashboard for managing cars, reservations, and monthly reports in the Rental-Car project.

## Key commands

```bash
cd frontend
npm install
npm start          # ng serve with a proxy to the backend on :8080
npm run build      # produces artifacts in dist/
```

Development assumes the Java backend is running at `http://localhost:8080`. The `proxy.conf.json` file forwards the `/car` and `/reservation` APIs to avoid CORS issues.

## Environments

- `src/environments/environment.ts` → used during development.
- `src/environments/environment.prod.ts` → set the real backend URL for production.
