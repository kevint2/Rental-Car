# Rental Car Angular UI

A lightweight Angular dashboard për menaxhimin e makinave, rezervimeve dhe raporteve mujore të projektit Rental-Car.

## Komandat kryesore

```bash
cd frontend
npm install
npm start          # ng serve me proxy drejt backend-it në :8080
npm run build      # prodhon artefaktet në dist/
```

Zhvillimi supozon që backend-i Java të jetë duke punuar në `http://localhost:8080`. Proxy `proxy.conf.json` rimer API-t `/car` dhe `/reservation` për të shmangur CORS.

## Mjediset

- `src/environments/environment.ts` → përdoret gjatë zhvillimit.
- `src/environments/environment.prod.ts` → vendosni URL-në reale të backend-it në prodhim.
