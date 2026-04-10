# OC-P11-PoC


## Start project

Run database with docker
```bash
docker compose up -d database
```

Run backend with java 21 and gradle
```bash
cd emergency-bed-service
./gradlew bootRun
```

Run frontend with node 22 
```bash
cd front-end
npm install
npm start # or `npm run dev`
```



## Back end

- [README Backend](./emergency-bed-service/README.md)

## Front end

- [README Frontend](./front-end/README.md)







## Tests


### Tests E2E

**Scénario**
Le système d'intervention d'urgence en temps réel est destiné à suggérer l'hôpital le
plus proche offrant un lit disponible et possédant la spécialisation attendue (voir les
Données de référence sur les spécialités) sur la base d’une banque de données
d'informations récentes sur les hôpitaux.
Par exemple, SUPPOSONS trois hôpitaux, comme suit :

| Hopital                | Lits disponibles | Spécialités                  |
|------------------------|------------------|------------------------------|
| Hôpital Fred Brooks    | 2                | Cardiologie, immunologie     |
| Hôpital Julia Crusher  | 0                | Cardiologie                  |
| Hôpital Beverly Bashir | 5                | Immunologie, neuropathologie |


ET un patient nécessitant des soins en cardiologie,
QUAND un patient demande des soins en cardiologie ET que l'urgence est localisée
près de l'hôpital Fred Brooks,
ALORS l'hôpital Fred Brooks devrait être proposé,
ET un événement devrait être publié pour réserver un lit.




---------

## Checklist

- [ ] Spécification OpenAPI des contrats de service
- [ ] Conteneurisation des services
- [ ] Conception pilotée par le domaine

- [ ] Tests E2E
- [ ] Tests de charge
- [ ] Tests d'intégration
- [ ] Tests unitaires
- [ ] Tests de sécurités

- [ ] CI/CD automatisé

- [ ] Documentation de la PoC
- [ ] Une présentation de la PoC, détaillant le comportement pris en charge, les connaissances acquises et les recommandations futures.


