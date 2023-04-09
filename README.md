# API Produits et Utilisateurs
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)</br>
Projet universitaire visant à la création d'une API pouvant gérer des utilisateurs et des produits.</br>
L'API sera consommée par deux autres entités : une IHM et une autre API.

## Quelles actions sont possibles ? 👷

### USERS

#### UPDATE User
```bash
curl -X PUT -H "Authorization: Bearer token" -H "Content-Type: application/json" -d '{"firstname":"Frédéric", "lastname":"EGENSCHEVILLE", "mail":"fredegen@gmail.com", "password":"password", "role":"user"}' http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/api/users/fredegen
```
#### DELETE User
```bash
curl -X DELETE -H "Authorization:Bearer token" http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/api/users/fredegen
```
#### INSERT User
```bash
curl -X POST -H "Authorization: Bearer token" -H "Content-Type: application/json" -d '{"firstname":"Alice","lastname":"Jones","mail":"alicejones@example.com","password":"pa$$word","role":"user","username":"alice23"}’ http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/api/users
```
#### SELECT User
```bash
curl -X GET -H "Authorization:Bearer token" http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/api/users/alice42
```
#### SELECT AllUsers
```bash
curl -X GET -H "Authorization:Bearer token" http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/api/users
```
#### Authenticate
```bash
curl -X POST http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/api/authenticate -H 'Authorization: Bearer token' -d 'username=charlie_brown&password=passw0rd'
```
Dossier comprenant les explications sur l'utilisation et les retours attendus [Rapport API Frédéric EGENSCHEVILLER.docx](https://github.com/R401-Services-web-en-Java/API-Produits-et-Utilisateurs/files/11185897/Rapport.API.Frederic.EGENSCHEVILLER.docx) 📁
