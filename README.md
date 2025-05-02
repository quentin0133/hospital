# 🏥 Hospital - Gestion d'un système hospitalier

Ce projet est une application de gestion hospitalière développée dans le cadre d’un exercice pédagogique autour des technologies **Java EE**, **Spring Boot**, et **Oracle Database**.  
Il vise à simuler un système de gestion interne pour un hôpital, permettant la gestion des patients, médecins, rendez-vous et services médicaux.

L’objectif principal est de manipuler les concepts de :
- **Architecture en couches (MVC)** avec Spring Boot
- **Persistance des données** via JPA et une base **Oracle**
- **Gestion des relations complexes** entre les entités (OneToMany, ManyToOne, etc.)
- **Exposition de services RESTful**

---

## 🔧 Tech Stack

- **Java 17+**
- **Spring Boot 3.4.5**
- Spring Web
- Spring Data JPA
- Spring Security
- Lombok
- Maven
- MapStruct
- MariaDB

---

## 📁 Structure du projet

```bash
src
└── main
    ├── java
    │   └── fr.cfa.hospital
    │       ├── consultation
    │       ├── service
    │       ├── repository
    │       ├── model
    │       └── config
    └── resources
        └── application.properties
```