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

## Si të konfigurosh Angular Router

Router-i është vendosur në `src/app/app-routing.module.ts` dhe lidhet me `CarsComponent`, `ReservationsComponent` dhe `ReportsComponent`. Udhëzimet më poshtë mbulojnë shtimin e rrugëve të reja, parametrat, lazy loading dhe fallback-in 404.

### 1) Shto një rrugë të thjeshtë

```ts
// src/app/app-routing.module.ts
const routes: Routes = [
  { path: '', redirectTo: 'cars', pathMatch: 'full' },
  { path: 'cars', component: CarsComponent },
  { path: 'reservations', component: ReservationsComponent },
  { path: 'reports', component: ReportsComponent },
  { path: 'settings', component: SettingsComponent } // shembull
];
```

Sigurohu që komponenti i ri (`SettingsComponent`) është deklaruar dhe eksportuar nga moduli i tij (p.sh. `app.module.ts`).

### 2) Navigim me `routerLink`

Vendos lidhjet në `app.component.html` (ose në një navbar të dedikuar) dhe stilo rrugën aktive me `routerLinkActive`:

```html
<a routerLink="/cars" routerLinkActive="active">Cars</a>
<a routerLink="/reservations" routerLinkActive="active">Reservations</a>
<a routerLink="/reports" routerLinkActive="active">Reports</a>
<a routerLink="/settings" routerLinkActive="active">Settings</a>

<router-outlet></router-outlet>
```

### 3) Rrugë me parametra (detaje)

Për një faqe detaji përdor `:id` në rrugë dhe lexo parametrin me `ActivatedRoute`:

```ts
{ path: 'cars/:id', component: CarDetailsComponent }
```

```ts
// brenda CarDetailsComponent
constructor(private route: ActivatedRoute) {}

ngOnInit() {
  const id = this.route.snapshot.paramMap.get('id');
  // thirr API-n për të ngarkuar makinën
}
```

### 4) Lazy loading për module të ndara

Kur UI rritet, ngarko module në mënyrë dinamike për të zvogëluar bundle-in fillestar:

```ts
const routes: Routes = [
  { path: '', redirectTo: 'cars', pathMatch: 'full' },
  { path: 'cars', component: CarsComponent },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
  }
];
```

`AdminModule` duhet të ketë `RouterModule.forChild([...])` dhe një `router-outlet` të vetin në komponentin shell.

### 5) Rrugë të mbrojtura (guards)

Për të kufizuar aksesin përdor një guard:

```bash
ng generate guard auth/auth
```

```ts
// src/app/auth/auth.guard.ts
canActivate(): boolean {
  return !!localStorage.getItem('token');
}
```

Regjistroje në rrugë:

```ts
{ path: 'reports', component: ReportsComponent, canActivate: [AuthGuard] }
```

### 6) Fallback/404 dhe rifreskimi

Mbaji rrugët më specifike në fillim dhe shto gjithmonë një rrugë të fundit për path-et e gabuara:

```ts
{ path: '**', redirectTo: 'cars' }
```

Çdo ndryshim në `app-routing.module.ts` rifreskohet automatikisht nga `npm start`; nuk ka nevojë për restart manual nëse dev server-i është aktiv.
