Front end - Poc medhead
=================================


## Exigences fonctionnelles
Une simple page permettant de sélectionner une spécialité et de saisir la
localisation.


## Lancer le projet

Prerequis :
- Backend + Api Gateway lancé
- Node 22+


1. Renseigner les bonnes variables d'environnement dans un fichier `.env`

Par défaut les valeurs sont celles utilisées pour le développement local :
```sh
VITE_API_BASE_URL=http://localhost:8080/api
VITE_API_GEOCODE_URL=http://localhost:8888/api-geo
```

2. Lancer le frontend
```bash
npm install
npm start # or `npm run dev`
```


## Projet overview

### Technologies utilisées

- Vue 3 comme framework front end
- Vite comme build tool
- Playwright pour les tests e2e
- Tailwind css/DaisyUI pour les styles

### Arborescence du projet

```sh
.
├── README.md
├── docker
│   └── Dockerfile   # Dockerfile pour le front end
├── e2e
│   └── user.spec.ts # Test e2e avec Playwright
├── index.html
├── node_modules
├── package-lock.json
├── package.json
├── playwright.config.ts
├── public
├── src
│   ├── App.vue     # Vue principale de l'application
│   ├── assets      # Dossier pour les ressources statiques (images, styles, etc.)
│   ├── components  # Dossier pour les composants Vue réutilisables 
│   ├── composables # Dossier pour les composables Vue (hooks) qui contiennent la logique métier et les appels API
│   ├── main.js
│   ├── style.css   # Fichier de styles global
│   └── types       # Dossier pour les types TypeScript
├── vite-env.d.ts
└── vite.config.js
```


## E2E tests

Lancer tests e2e avec l'interface : 
```sh
npx playwright test --ui
```

Générer un test e2e avec codegen :
```sh
npx playwright codegen http://localhost:5173
```