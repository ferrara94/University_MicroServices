# 🎓 University Microservices 
## Overview

**University Microservice** is a collection of Spring Boot-based microservices designed to model key entities within the university world. Each microservice focuses on a limited set of entities, providing a modular architecture for handling university-related processes.
Whether you're managing courses, students, or faculty, these microservices help streamline and organize various university functions through independent services.

Need to manage a course or student data? 💡 This repo will help you!

---

## 📚 Documentation & Architecture

You’ll find helpful resources to better understand and test the microservices:
- **postman_collection**: A Postman collection with ready-to-use API requests for testing the services.
- **documentation.pdf**: Contains architectural overviews, service relationships, and additional implementation details to guide development and integration.
- **architecture.pdf**

---

## 🗄️ Databases
Once you have run the shell scripts in their respective folder locations using **./nameScript.sh**, you need to create the related application databases.

For example, to run the Students Web Services, you must first create the University database and set up the necessary tables.

Each microservice may require its own database with specific tables, so make sure to follow the instructions for each service accordingly.

---
## Technologies Used

- [ ] Java 11
- [ ] Spring Data
- [ ] Spring Security (Basic Auth, JWT Auth)
- [ ] Spring Actuator
- [ ] Spring Cloud (configuration)
- [ ] json format
- [ ] Slf4j
- [ ] JUnit 4/5
- [ ] PostgreSQL database
- [ ] Spring Web Modules
- [ ] Tomcat (provided by SpringBoot)
- [ ] Docker 
- [ ] Bash 

---
## Microservices

The following microservices are part of the University Microservices architecture:

 - **Students**: Manages student-related services and data.
 - **Users Credentials**: Handles user authentication and credential management.
 - **Course**: Manages courses, their details, and enrollment processes.
 - **UniversityAuthServerJWT**: Provides authentication services with JWT for secure access to other microservices.

Each microservice can be developed, deployed, and tested independently.



