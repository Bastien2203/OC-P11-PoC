# Versionning


- Nommage des commits : [Conventional Commits](https://www.conventionalcommits.org/fr/v1.0.0-beta.3/)

```
<type>[optional scope]: <description>

[optional body]

[optional footer]
```

- Gestion des branches inspirée de [Gitflow](https://www.atlassian.com/fr/git/tutorials/comparing-workflows/gitflow-workflow)

```mermaid
gitGraph
    commit id: "Init"
    branch develop
    checkout develop
    commit id: "Début dev"
    
    %% Création d'une feature
    branch feat/feature-name
    checkout feat/feature-name
    commit id: "Code feature"
    checkout develop
    merge feat/feature-name
    
    %% Mise en production
    checkout main
    merge develop
      
```