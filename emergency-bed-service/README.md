Backend - Poc medhead
=========================

## Lancer le projet

Prérequis :
- Java 21 + Gradle
- Docker + Docker Compose

1. Lancer la base de données avec docker
```bash
docker compose up -d database
```

2. Lancer l'api gateway
```bash
docker compose up -d api-gateway
```
3. Configurer les variables d'environnement pour le backend

Par défaut, le backend est configuré pour se connecter à une base de données locale.
Si vous utilisez les services docker pour la base de données, les valeurs par défaut devraient fonctionner. Sinon, vous pouvez configurer les variables d'environnement suivantes :

```sh
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/emergency_bed_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

3. Lancer le backend
```bash
./gradlew bootRun
```
ou si on veux lancer en peuplant la base de données avec des données factices :
```sh
./gradlew seedDatabase
```


> Sinon avec intellij : Gradle -> Tasks -> application -> bootRun


## Documentation API 

- Spec openapi disponible ici en local : http://localhost:8080/swagger-ui.html



## Project overview


### Technologies utilisées

Spring boot
- JPA pour la gestion de la base de données
- Spring Web pour les controllers REST
- OpenAPI pour la documentation de l'API
- lombok
- Testcontainers pour les tests d'intégration avec une base de données réelle
- JUnit et MockMvc pour les tests unitaires et d'intégration
- JMeter pour les tests de stress
- PostgreSQL comme base de données relationnelle


### Arborescence du projet
```sh
.
├── HELP.md
├── README.md
├── build
├── build.gradle
├── docker
│   └── Dockerfile
├── gradle
├── gradlew
├── gradlew.bat
├── jmeter.log
├── settings.gradle
├── src
│   ├── main
│   │   ├── java
│   │   │   └── fr
│   │   │       └── medhead
│   │   │           └── emergency_bed_service
│   │   │               ├── DataSeeder.java                     # Classe pour initialiser la base de données avec des factices
│   │   │               ├── EmergencyBedServiceApplication.java # Classe principale de l'application Spring Boot
│   │   │               ├── config
│   │   │               │   └── CorsConfig.java                 # Configuration CORS 
│   │   │               ├── controller                          # Contient les controllers REST pour gérer les requêtes HTTP
│   │   │               │   ├── HospitalController.java
│   │   │               │   └── SpecialityGroupController.java
│   │   │               ├── dto
│   │   │               ├── model
│   │   │               │   ├── Hospital.java
│   │   │               │   ├── Speciality.java
│   │   │               │   └── SpecialityGroup.java
│   │   │               ├── repository                           # Contient les interfaces de repository pour interagir avec la base de données
│   │   │               │   ├── HospitalRepository.java
│   │   │               │   ├── SpecialityGroupRepository.java
│   │   │               │   └── SpecialityRepository.java
│   │   │               └── service                              # Contient la logique métier de l'application
│   │   │                   ├── HospitalRoutingService.java
│   │   │                   ├── HospitalService.java
│   │   │                   └── SpecialityGroupService.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── specialities.json                                # Fichier de données factices pour les spécialités
│   └── test
├── stress-report                                                # Rapport HTML généré après un test de stress avec JMeter
├── stress-results.jtl                                           # Fichier de résultats du test de stress
└── stress-test-plan.jmx                                         # Plan de test de stress pour JMeter
```


## Tests unitaires et intégration

- Unitaire: spring boot test avec MockMvc pour simuler services externes

- Intégration : Spring boot `test` avec TestContainers pour utiliser de vrais services (base de données)
    - Test récuperation des spécialités : [SpecialityGroupIntegrationTest.java](./src/test/java/fr/medhead/emergency_bed_service/controller/SpecialityGroupIntegrationTest.java)
    - Test Hopital disponible le plus proche : [HospitalNearestIntegrationTest.java](./src/test/java/fr/medhead/emergency_bed_service/controller/HospitalNearestIntegrationTest.java)
    - Test Réserver lit dans hopital : [HospitalDecrementIntegrationTest.java](./src/test/java/fr/medhead/emergency_bed_service/controller/HospitalDecrementIntegrationTest.java)


## Tests de stress

Run tests :
```shell
jmeter -n -t stress-test-plan.jmx -l stress-results.jtl -e -o stress-report
```