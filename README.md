# Hospital - Gestion d'un système hospitalier

Application de gestion hospitalière conçue pour centraliser le suivi des patients, du personnel médical, des rendez-vous et des prescriptions au sein d'un établissement de santé.

---

## Stack technique

| Catégorie | Technologies |
| :--- | :--- |
| **Langage** | Java 21 |
| **Framework** | Spring Boot 3.3.5 |
| **Persistance** | MongoDB (Spring Data MongoDB) |
| **Sécurité** | Spring Security, JJWT (JSON Web Token) |
| **Mapping & Outillage** | MapStruct, SpringDoc OpenAPI (Swagger UI) |
| **Tests & Qualité** | JUnit 5, Testcontainers, JaCoCo, SonarQube |
| **Gestionnaire de build** | Apache Maven |
| **Client API** | Bruno |
| **Conteneurisation** | Docker |

---

## Fonctionnalités

L'accès à l'API est protégé et segmenté selon les rôles utilisateurs.

### Espace public (Non authentifié)
* **Authentification :** Point d'entrée sécurisé pour la connexion des utilisateurs.
* **Sécurité JWT :** Génération d'un jeton porteur (*bearer token*) requis pour l'ensemble des routes protégées.

### Espace médical et administratif (Authentifié)
* **Dossiers patients (CRUD) :**
  * Création, consultation, mise à jour et suppression des dossiers.
  * Recherche dynamique par nom et pagination des résultats.
* **Gestion du personnel soignant (CRUD) :**
  * Administration des fiches médecins.
  * Recherche par nom avec affichage paginé.
* **Consultations médicales (CRUD) :**
  * Planification et suivi des consultations.
  * Historique complet paginé par patient.
  * Gestion des pièces jointes (téléversement sécurisé de comptes-rendus et examens médicaux).
* **Prescriptions et médicaments :**
  * Référentiel complet des médicaments.
  * Module d'ordonnance associant médicaments et posologies à chaque consultation.

### Socle technique transverse
* **Gestion de la concurrence (*Optimistic Locking*) :** Mécanisme de contrôle de version sur les documents pour empêcher l'écrasement simultané de données (retourne une réponse `409 Conflict`).
* **Audit et traçabilité :** Journalisation systématique des opérations métier via un aspect AOP.
* **Documentation OpenAPI :** Documentation interactive synchronisée via Swagger UI.

---

## Installation et déploiement local

### Prérequis
* Java 21 (JDK)
* Docker et Docker Compose
* Bruno *(optionnel, pour l'exécution des collections d'API)*

---

### 1. Clonage et dépendances

```bash
git clone <URL_DU_DEPOT>
cd hospital
mvn clean dependency:resolve
```

---

### 2. Configuration des environnements

Créez le fichier `src/main/resources/application-dev.properties` :

```properties
spring.application.name=hospital

# Database
spring.data.mongodb.uri=mongodb://localhost:27017/<DB>

# File upload size limits for multipart requests
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Secret key for security-related features (JWT, encryption, etc.)
jwt.secret.key=<SECRET_KEY>
# Duration in seconds
jwt.token.duration=3600

# Location of files uploaded
file.storage.path=files

# Create a default admin user
app.default.username=admin
app.default.password=<ADMIN_PW>

springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
spring.web.resources.add-mappings=true

logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG
```

Créez également le fichier `src/test/resources/application-test.properties` pour l'exécution des tests isolés :

```properties
spring.application.name=hospital-test

# Database
spring.data.mongodb.uri=mongodb://localhost:27017/<DB>-test

spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Secret key for security-related features (JWT, encryption, etc.)
jwt.secret.key=<SECRET_KEY>
# Duration in seconds
jwt.token.duration=3600

# Create a default admin user
app.default.username=test
app.default.password=test

# Location of files uploaded
file.storage.path=files-test
```

---

### 3. Démarrage de la base de données

Lancez l'instance MongoDB locale dans un conteneur :

```bash
docker run -d -p 27017:27017 --name mongodb mongo:latest
```

---

### 4. Lancement de l'application

Démarrez le serveur de développement :

```bash
mvn spring-boot:run
```

L'API est accessible sur `http://localhost:8080`.

---

## Documentation de l'API (Swagger UI)

Une fois l'application démarrée, l'interface Swagger UI est consultable directement dans le navigateur :

```text
http://localhost:8080/swagger-ui/index.html
```

Pour exécuter des requêtes sur les endpoints protégés depuis l'interface :
1. Connectez-vous via l'endpoint `/api/auth/login`.
2. Cliquez sur le bouton **Authorize** en haut à droite.
3. Renseignez la valeur : `Bearer <votre_token_jwt>`.

---

## Tests fonctionnels avec Bruno

Les requêtes de test sont centralisées dans le dossier `bruno` à la racine du projet (`bruno/An hospital API/`).

1. Ouvrez **Bruno**.
2. Sélectionnez **Open Collection**.
3. Pointez sur le dossier `bruno/An hospital API`.
4. Exécutez en priorité la requête du dossier `Authentication` pour instancier la variable de token JWT requise par les autres dossiers.

---

## Tests et analyse de code (SonarQube & JaCoCo)

### Exécution des tests unitaires et génération du rapport de couverture

```bash
mvn clean test
```

Le rapport de couverture JaCoCo est compilé dans le dossier `target/site/jacoco/jacoco.xml`.

### Analyse statique avec SonarQube

1. **Démarrer le serveur SonarQube local :**
   ```bash
   docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:community
   ```

2. **Accès au tableau de bord :**
  * Rendez-vous sur `http://localhost:9000` (identifiants par défaut : `admin` / `admin`).
  * Créez un projet manuellement et générez un jeton d'analyse (*Project Analysis Token*).

3. **Lancer le scanner :**
   Exécutez l'analyse directement via le plugin Maven en passant votre jeton :
   ```bash
   mvn clean verify sonar:sonar -Dsonar.host.url="http://localhost:9000" -Dsonar.token="<VOTRE_TOKEN>"
   ```