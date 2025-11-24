# 📘 **Huerto Hogar -- Backend (Spring Boot API)**

Este es el backend oficial del proyecto **Huerto Hogar**, una plataforma
diseñada para gestionar productos, usuarios y operaciones relacionadas
con un huerto inteligente / e-commerce de productos de jardinería.\
Este backend provee una API REST completa, con documentación interactiva
vía **Swagger UI**, además de operaciones CRUD para los recursos
principales.

------------------------------------------------------------------------

## 🚀 **Tecnologías Utilizadas**

-   **Java 17**
-   **Spring Boot**
    -   Spring Web
    -   Spring Data JPA
    -   Hibernate
-   **Maven**
-   **Swagger / Springdoc OpenAPI**
-   **H2 / MySQL** (según configuración)
-   **Lombok**
-   **CORS habilitado** para consumir desde React

------------------------------------------------------------------------

## 📁 **Estructura del Proyecto**

``` plaintext
huerto-backend/
 ├─ src/
 │  ├─ main/
 │  │  ├─ java/com/huertohogar/...
 │  │  ├─ resources/
 │  │     ├─ application.properties
 │  │     └─ data.sql (opcional)
 │  └─ test/
 ├─ pom.xml
 └─ README.md
```

------------------------------------------------------------------------

## ⚙️ **Configuración**

### 🔧 Archivo `application.properties` (ejemplo)

``` properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

Si usas MySQL, sería por ejemplo:

``` properties
spring.datasource.url=jdbc:mysql://localhost:3306/huertohogar
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

------------------------------------------------------------------------

## ▶️ **Cómo Ejecutarlo**

### **1. Clonar repositorio**

``` bash
git clone https://github.com/saruo-san/Huerto-Hogar-Backend.git
cd Huerto-Hogar-Backend
```

### **2. Compilar y ejecutar con Maven**

``` bash
mvn clean install
mvn spring-boot:run
```

### **3. Ejecutar .jar (modo producción)**

``` bash
java -jar target/huerto-backend-0.0.1-SNAPSHOT.jar
```

------------------------------------------------------------------------

## 🌐 **Swagger UI -- Documentación de la API**

Una vez corriendo el backend:

📄 **OpenAPI JSON:**

    http://localhost:8080/api-docs

🧩 **Swagger UI (interactivo):**

    http://localhost:8080/swagger-ui.html

------------------------------------------------------------------------

## 🧪 **Endpoints Principales (Resumen)**

-GET      /api/productos        Obtener todos los productos

-GET      /api/productos/{id}   Obtener por ID

-POST     /api/productos        Crear un producto

-PUT      /api/productos/{id}   Actualizar producto

-DELETE   /api/productos/{id}   Eliminar producto

------------------------------------------------------------------------

## 🔐 **CORS (Integración con Frontend React)**

``` java
@CrossOrigin(origins = "*")
```

o config global:

``` java
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedOrigins("*");
        }
    };
}
```

------------------------------------------------------------------------

## 👥 **Autores**

**Javier Muñoz**
**Nicola Osses**
**Jairo Huaman**

**Sección:** 003D\
**Asignatura:** Desarrollo Fullstack\
**Año:** 2025

------------------------------------------------------------------------

## 🔗 **Repositorios Relacionados**

### 🌱 **Frontend (React)**

https://github.com/saruo-san/Huerto-Hogar

### ⚙️ **Backend (Spring Boot)**

https://github.com/saruo-san/Huerto-Hogar-Backend
