Bed Emergency Service Poc
=========================






## Tests unitaires et intégration

- Unitaire: spring boot test avec MockMvc pour simuler services externes
- Intégration : Spring boot test avec TestContainers pour utiliser de vrais services (base de données) 


## Tests de stress

```shell
jmeter -n -t stress-test-plan.jmx -l stress-results.jtl -e -o stress-report
```