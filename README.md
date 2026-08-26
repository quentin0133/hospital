# Hospital - Gestion d'un système hospitalier

Ce projet est une application de gestion hospitalière développée pour gérer un hôpital, permettant la gestion des patients, médecins, rendez-vous et services médicaux.

## Technologies utilisées

- Langage : Java 21
- Back-end : Spring boot 3.4.5
- Base de données : MariaDB (SQL)
- ORM : JPA Hibernate
- Dépendance : Spring Web, Spring Security, Spring Data JPA, Mapstruct, JWT, Swagger UI,
- Gestionnaire de dépendance : Maven
- Autres : Docker, Sonarqube, JUnit, Bruno

---

## Fonctionnalités de l'application

L'application est structurée pour gérer les processus d'un établissement de santé, avec un accès strictement protégé et tracé :

### Espace Public (Non connecté)
* **Authentification :** Point d'entrée sécurisé permettant de se connecter (Login) avec vérification des identifiants.
* **Sécurité :** Génération d'un token JWT nécessaire pour accéder à l'ensemble des autres routes de l'API.

### Espace Médical & Administratif (Connecté)
* **Gestion des Patients (CRUD) :** Création, lecture, modification et suppression des dossiers patients.
    * Système de pagination intégré.
    * Recherche dynamique par nom de patient.
* **Gestion des Médecins (CRUD) :** Création, lecture, modification et suppression des profils médecins.
    * Pagination et recherche par nom de médecin.
* **Gestion des Consultations (CRUD) :** Création, suivi et mise à jour des rendez-vous médicaux.
    * Historique : Récupération paginée de toutes les consultations liées à un patient spécifique.
    * Pièces jointes : Upload sécurisé de fichiers (comptes-rendus, imagerie) directement rattachés à une consultation.
* **Prescriptions & Médicaments :**
    * Catalogue de médicaments (Création, lecture, mise à jour, suppression).
    * Module de prescription automatique : Ajout de médicaments à une consultation existante (avec définition des quantités) via une table de liaison.

### Fonctionnalités Techniques Transverses
* **Gestion de la concurrence (Optimistic Locking) :** Système de versioning empêchant l'écrasement des données si deux utilisateurs modifient la même ressource simultanément (Renvoie une erreur *409 Conflict*).
* **Traçabilité :** Mécanisme de logging automatique sur toutes les actions métier de l'API.
* **Documentation Interactive :** Contrat d'API documenté et explorable en temps réel via Swagger UI (OpenAPI).

---

## Installation et déploiement (Local)

### Prérequis
- Java 21
- MariaDB
- Maven

### Optionnelle :
- Bruno
- Docker

### Étapes d'installation

1. **Cloner le dépôt :**
```cmd
git clone [URL_DU_DEPOT]
cd [NOM_DU_DOSSIER]
```

2. **Installer les dépendances via la commande ou IDE :**
```cmd
mvn dependency:resolve
```

3. **Configurer le projet :**
   Créer application-dev.properties, puis utiliser le modèle en dessous.
```cmd
spring.application.name=hospital

# Database infos
spring.datasource.url=jdbc:mariadb://localhost:3307/hospital
spring.datasource.username=[USUERNAME]
spring.datasource.password=[PWD]

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# File upload size limits for multipart requests
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Secret key for security-related features (JWT, encryption, etc.)
jwt.secret.key=[SECRET]
# Duration in seconds
jwt.token.duration=3600

# Create a default admin user
app.default.username=[USERNAME_ADMIN]
app.default.password=[PWD_ADMIN]

# Location of files uploaded
file.storage.path=files

springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
spring.web.resources.add-mappings=true
```

Pour les tests, ajouter application-test.properties
```cmd
spring.application.name=hospital

# Database infos
spring.datasource.url=jdbc:mariadb://localhost:3307/hospital_test
spring.datasource.username=[USERNAME]
spring.datasource.password=[PWD]

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Script SQL for inserting data
spring.sql.init.data-locations=classpath:db/test/data.sql
spring.sql.init.mode=always
spring.jpa.defer-datasource-initialization=true

# File upload size limits for multipart requests
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Secret key for security-related features (JWT, encryption, etc.)
jwt.secret.key=[SECRET]
# Duration in seconds
jwt.token.duration=3600

# Create a default admin user
app.default.username=test
app.default.password=test

# Location of files uploaded
file.storage.path=files-test
```

4. **Créer les bases de données (par défaut : hospital et hospital_test)**

Vous devriez pouvoir lancer l'application et ses tests.

### Suivre la dette technique avec Sonarqube
**Lancer Sonarqube avec Docker :**
```cmd
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:community
```

Rendez sur l'URL http://localhost:9000, l'identifiant sera admin et le mot de passe admin.

Une fois connecté, il va fallloir générer un token pour lancer l'analyse du projet.

On doit aller dans My Account > Security > Generate Tokens Générer un token d'application, copier dans un endroit sans le partager Revenez dans l'accueil et créer un projet localement, il vous demandera le token.

```
sonar.host.url=http://host.docker.internal:9000
sonar.token=[TOKEN]

sonar.projectKey=[PROJECT-KEY]
sonar.projectName=[PROJECT-NAME]
sonar.projectVersion=1.0.0
sonar.sources=src/main/java
sonar.tests=src/test/java
sonar.java.binaries=target/classes

sonar.exclusions=**/config/**, **/HospitalApplication.java, **/*MapperImpl.java
sonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

**Générer un rapport de test (avec Jacoco) :**
```cmd
mvn clean test
```

**Lancer une analyse :**
```cmd
docker run --rm -v "$(pwd):/usr/src" sonarsource/sonar-scanner-cli
```

S'il n'y a eu aucun souci, les analyses seront consultables sur l'interface de Sonarqube