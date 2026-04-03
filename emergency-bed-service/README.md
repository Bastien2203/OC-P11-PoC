Bed Emergency Service Poc
=========================



## Scénario 

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



## Tests unitaires et intégration

- Unitaire: spring boot test avec MockMvc pour simuler services externes
- Intégration : Spring boot test avec TestContainers pour utiliser de vrais services (base de données) 


## Tests de stress

```shell
jmeter -n -t stress-test-plan.jmx -l stress-results.jtl -e -o stress-report
```