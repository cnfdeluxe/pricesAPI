# Prices API - Inditex

## Descripción

Esta API proporciona información sobre los precios de los productos de Inditex en diferentes fechas y franjas horarias según una lista de tarifas aplicables.

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot** (Framework principal)
- **Spring Data JPA** 
- **Hibernate** 
- **Base de datos H2 en memoria**
- **Maven** (Gestor de dependencias)
- **Swagger** (Documentación de la API)
- **JUnit & Mockito** (Pruebas unitarias y de integración)
- **Postman** (Pruebas sobre el endpoint)
- **Jacoco** (Test de cobertura de código)
 
## Dependencias Spring Boot
- **Spring Web**
- **Spring Data JPA** (Framework principal)
- **Lombok**
- **H2 Database**
- **Spring Boot Dev Tools**

## Instalación y Configuración

1. **Clonar el repositorio**
   ```sh
   git clone https://github.com/cnfdeluxe/pricesAPI.git
   cd pricesAPI
   ```
2. **Configurar base de datos**
   - Modificar `application.properties` con las credenciales correctas.
   - Modificar `data.sql` para realizar cambios en la tabla Prices o añadir mas datos.

3. **Compilar y ejecutar la API**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

4. **Acceder a la documentación Swagger**
   ```
   http://localhost:8080/swagger-ui.html
   ```

## Endpoints principales

### Obtener precio de un producto en una fecha y franja horaria

```http
GET /api/prices?product_id=35455&brand_id=1&date_application=2020-06-14T15:00:00
```

**Parámetros:**
- `date` (Fecha en formato `yyyy-MM-dd'T'HH:mm:ss`)
- `productId` (ID del producto)
- `brandId` (ID de la marca)

**Ejemplo de respuesta:**
```json
{
    "status": 200,
    "success": true,
    "data": {
        "product_id": 35455,
        "brand_id": 1,
        "price": 25.45,
        "currency": "EUR",
        "start_date": "2020-06-14T15:00:00",
        "end_date": "2020-06-14T18:30:00",
        "price_list": 2
    },
    "message": null
}
```

## Arquitectura

La API sigue una **arquitectura hexagonal** basada en **Domain-Driven Design (DDD)**. La estructura del proyecto se organiza en capas bien definidas para garantizar una separación clara entre el dominio, la aplicación y la infraestructura.

### Implementación de ResponseBuilder
Se ha implementado una clase personalizada ResponseBuilder para que devuelva respuestas estructuradas y asi lograr que las respuestas sean mas robustas, mantenibles y amigables para los consumidores. 
Se han creado dos clases en Infrastructure -> utils
- **ApiResponse.java**: DTO genérico para todas las respuestas.
- **ResponseBuilder.java**: Clase helper para construir respuestas consistentes.


```
prices-api/
├── src/main/java/com/inditex/prices/
│   ├── application/       # Casos de uso y lógica de aplicación
│   │   ├── service/       # Servicios de aplicación
│   │   ├── dto/           # Data Transfer Objects
│   ├── domain/           # Lógica de dominio pura
│   │   ├── model/        # Entidades de dominio
│   │   ├── repository/   # Interfaces de repositorio
│   │   ├── service/      # Servicios de dominio
│   ├── infrastructure/   # Adaptadores y capa de persistencia
│   │   ├── adapter/      # Implementaciones de repositorios
│   │   ├── persistence/  # Configuración de JPA/Hibernate
│   │   ├── rest/         # Controladores y API REST
├── src/test/java/com/inditex/prices/  # Pruebas unitarias e integración
├── pom.xml               # Dependencias y configuración Maven
└── README.md             # Documentación del proyecto
```

### Explicación de capas
- **Application:** Contiene los casos de uso y lógica de aplicación.
- **Domain:** Implementa la lógica de negocio y las reglas de dominio.
- **Infrastructure:** Contiene la interacción con bases de datos, API externas y otros adaptadores.

## Pruebas

Para ejecutar las pruebas unitarias y de integración:
```sh
mvn test
```

## Contacto

Si tienes dudas o quieres contribuir, contacta conmigo a través de [LinkedIn] www.linkedin.com/in/cristian-navarro-fernández-6044937a 
