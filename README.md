PoC Medhead - Service de gestion des lits d'urgence
==========

Ce PoC a pour objectif de démontrer la faisabilité d'un service de gestion des lits d'urgence pour les hôpitaux.
Il doit permettre à l'utilisateur de choisir une spécialité médicale, et de trouver l'hopital le plus proche qui a un lit disponible pour cette spécialité.


## Table des matières

- [CI](doc/CI.md)
- [Versionning](doc/VERSIONNING.md)
- [Documentation Backend](./emergency-bed-service/README.md)
- [Documentation Frontend](./front-end/README.md)
- [Lancer le projet](#lancer-le-projet)



## Lancer le projet

### Avec docker compose (pour les tests)

Lancer tout le projet avec docker compose (base de données, api gateway, backend et frontend)
```bash
docker compose up -d
```

### Pour le développement

1. Lancer la base de données et l'api gateway avec docker compose
```bash
docker compose up -d database
docker compose up -d api-gateway
```

2. Lancer le backend
```bash
cd emergency-bed-service
./gradlew bootRun
```

3. Lancer le frontend
```bash
cd front-end
npm install
npm run dev
```