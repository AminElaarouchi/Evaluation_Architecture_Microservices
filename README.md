# Évaluation — Architecture Microservices

Solution des trois exercices de l’[évaluation du cours Architecture Microservices](https://mliaedu.toubkalit.com/student/courses/7/labs/160) : gestion de stock, gestion de projets et état civil. Les exercices utilisent Java 17, Maven, JPA/Hibernate et MySQL. Les tests d’intégration exécutent les mêmes services avec H2 en mémoire, sans base MySQL à installer.

## Structure

| Module | Entités | Services et requêtes |
| --- | --- | --- |
| `commun` | — | `IDao<T>`, transactions CRUD, `HibernateUtil` et chargement de `application.properties` |
| `stock` | `Categorie`, `Produit`, `Commande`, `LigneCommande` | Produits par catégorie, commandés entre deux dates ou dans une commande ; requête nommée `prix > 100 DH` ; contrôle du stock lors de l’ajout, la modification et la suppression d’une ligne |
| `projets` | `Projet`, `Tache`, `Employe`, `EmployeTache` | Tâches et projets d’un employé ; tâches planifiées et réalisées d’un projet ; requête nommée `prix > 1000 DH` ; réalisations entre deux dates ; coût global et jours réalisés |
| `etat-civil` | `Homme`, `Femme`, `Mariage` | Épouses entre deux dates, mariages détaillés, femme la plus âgée, nombre d’enfants via requête native nommée, femmes mariées au moins deux fois via requête nommée, hommes mariés à exactement quatre femmes via Criteria API |

Chaque module métier a ses propres classes dans `ma.projet.classes` et `ma.projet.service`. Les packages communs `ma.projet.dao` et `ma.projet.util` se trouvent dans `commun`. Cette séparation permet de conserver les noms de packages demandés sans collision entre les entités des trois exercices.

## Exécuter les tests

Depuis la racine de ce dépôt :

```powershell
mvn test
```

Pour un seul exercice :

```powershell
mvn -pl stock -am test
mvn -pl projets -am test
mvn -pl etat-civil -am test
```

Les tests H2 créent leurs tables et leurs données, puis vérifient les requêtes, les associations, le CRUD et les règles de stock. Le test d’état civil crée **10 femmes et 5 hommes**, comme demandé. Le dernier `mvn -B test` a réussi : **3 tests d’intégration, 0 échec**.

## Configuration MySQL

Chaque module métier possède `src/main/resources/application.properties` et pointe vers sa propre base (`evaluation_stock`, `evaluation_projets`, `evaluation_etat_civil`). Le paramètre JDBC `createDatabaseIfNotExist=true` crée la base si l’utilisateur MySQL a les droits requis. Adaptez le serveur et les identifiants au besoin avec `DB_URL`, `DB_USER` et `DB_PASSWORD` ; ne commitez pas de mot de passe. `HibernateUtil.fromApplicationProperties(...)` ouvre la `SessionFactory` avec les entités passées en argument. Les tests utilisent H2 et ne modifient pas MySQL.

## Choix de modélisation

- Une ligne de commande est une entité distincte avec une quantité. Son prix est celui du produit associé. Les changements de quantité mettent à jour le stock dans la même transaction.
- Un projet possède un gestionnaire (`Employe`) ; `EmployeTache` conserve les dates réelles. Le coût global est la somme des prix des tâches planifiées, et les jours réalisés comptent les deux dates incluses.
- `Mariage.nombreEnfants` représente le nombre d’enfants associé à une union. La requête native « entre deux dates » filtre les unions par **date de début**, car l’énoncé textuel ne fournit pas d’entité `Enfant` avec une date de naissance.
- Une épouse présente dans l’intervalle est liée à un mariage dont les dates chevauchent cet intervalle. La requête Criteria compte les femmes distinctes épousées avec une date de début dans l’intervalle.

## Importer dans Eclipse

**File > Import > Maven > Existing Maven Projects**, puis choisir le dossier racine `Evaluation_Architecture_Microservices`.
