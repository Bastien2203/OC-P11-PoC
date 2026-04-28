Bed Emergency Service Poc
=========================

## Documentation 


- Spec openapi disponible ici en local : http://localhost:8080/swagger-ui.html



## Tests unitaires et intégration

- Unitaire: spring boot test avec MockMvc pour simuler services externes


- Intégration : Spring boot `test` avec TestContainers pour utiliser de vrais services (base de données)
    - Test récuperation des spécialités : [SpecialityGroupIntegrationTest.java](./src/test/java/fr/medhead/emergency_bed_service/controller/SpecialityGroupIntegrationTest.java)
    - Test Hopital disponible le plus proche : [HospitalNearestIntegrationTest.java](./src/test/java/fr/medhead/emergency_bed_service/controller/HospitalNearestIntegrationTest.java)
    - Test Réserver lit dans hopital : [HospitalDecrementIntegrationTest.java](./src/test/java/fr/medhead/emergency_bed_service/controller/HospitalDecrementIntegrationTest.java)


## Tests de stress

```shell
jmeter -n -t stress-test-plan.jmx -l stress-results.jtl -e -o stress-report
```