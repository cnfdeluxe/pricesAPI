# Prices API - Inditex

## Descripción

La principal función de esta API REST es proporcionar información sobre los precios de los productos de Inditex en diferentes fechas y franjas horarias según una lista de tarifas aplicables.

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
   http://localhost:8080/swagger-ui/index.html
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

### Explicación de capas
- **application**: Contiene DTOs, excepciones y lógica de servicio.

- **domain**: Modelos y repositorios.

- **infrastructure**: Adaptadores, controladores y utilidades.

- **resources**: Configuración y plantillas.

## Estructura del proyecto
```
src
└── main
    └── java
        └── com.api.prices
            ├── application
            │   ├── dto
            │   │   ├── PriceInputDTO
            │   │   ├── PriceOutputDTO
            │   ├── exception
            │   │   ├── ControllerAdviceHandler
            │   │   ├── PriceNotFoundException
            │   ├── service
            │   │   ├── PriceService
            ├── domain
            │   ├── model
            │   │   ├── Price
            │   ├── repository
            │   │   ├── PriceRepository
            ├── infrastructure
            │   ├── adapter
            │   │   ├── PriceJpaAdapter
            │   ├── rest
            │   │   ├── PriceController
            │   ├── utils
            │   │   ├── ApiResponse
            │   │   ├── ResponseBuilder
            ├── PricesApplication
    ├── resources
    │   ├── static
    │   ├── templates
    │   ├── application.properties

```

## Otras funcionalidades

### Implementación de ResponseBuilder
Se ha implementado una clase personalizada ResponseBuilder para que devuelva respuestas estructuradas y asi lograr que las respuestas sean mas robustas, mantenibles y amigables para los consumidores. 
Se han creado dos clases en Infrastructure -> utils
- **ApiResponse.java**: DTO genérico para todas las respuestas.
- **ResponseBuilder.java**: Clase helper para construir respuestas consistentes.

### Control de Excepciones. ControllerAdvicer
Esta compuesto por ControllerAdviceHandler y PriceNotFoundException. 
- ControllerAdviceHandler se utiliza para capturar excepciones específicas y devolver respuestas HTTP estandarizadas con códigos de estado, mensajes de error y detalles estructurados.
- PriceNotFoundException su función principal es representar errores especificos del dominio ( en este caso, cuando un precio no se encuentra en el sistema)

ControllerAdviceHandler esta integrada con PriceNotFoundException de tal manera que si un controlador o servicio lanza PriceNotFoundException, Spring ejecuta el método handlePriceNotFoundException definido en ControllerAdviceHandler

Ejemplo:
1. El cliente lanza una petición:
GET /api/prices/99 (pero el ID 99 no existe).

2. El servicio lanza una expeción:
```
throw new PriceNotFoundException("Price with ID 99 not found");
```

4. ControllerAdviceHandler captura la excepción y genera la respuesta HTTP con 404.
5. El cliente recibe:
```
{
    "success": false,
    "data": null,
    "message": "Price with ID 99 not found"
} 
```

## Informacion Base Datos H2

Campos:
- ID (Se crea una primary key) -> BIGINT AUTOINCREMENT
- BRAND_ID -> BIGINT
- START_DATE -> TIMESTAMP
- END_DATE -> TIMESTAMP
- PRICE_LIST -> INT
- PRODUCT_ID -> BIGINT
- PRIORITY -> INT
- PRICE -> DECIMAL(10,2)
- CURR -> VARCHAR(3)

El script de BBDD para crear la tabla e insertar los valores se encuentra en Resources -> data.sql y esta definido en el Application.properties:
```
#Propiedades BBDD H2
spring.datasource.url=jdbc:h2:mem:pricesdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update
```


## Tests de la API
### Pruebas Unitarias
Las pruebas unitarias verifican la lógica de negocio del servicio PriceService. Se han implementado en la clase PriceServiceTest utilizando JUnit 5 y Mockito.

- **CASOS DE PRUEBA EN PriceServiceTest**
Los siguientes casos validan la correcta obtención de precios según los criterios de fecha, producto y marca:

1. Consulta a las 10:00 del 14 de junio para el producto 35455 y marca 1 (ZARA).

2. Consulta a las 16:00 del 14 de junio para el producto 35455 y marca 1 (ZARA).

3. Consulta a las 21:00 del 14 de junio para el producto 35455 y marca 1 (ZARA).

4. Consulta a las 10:00 del 15 de junio para el producto 35455 y marca 1 (ZARA).

5. Consulta a las 21:00 del 16 de junio para el producto 35455 y marca 1 (ZARA).

**Objetivo:** Asegurar que se devuelven correctamente los precios con su prioridad más alta según las reglas de negocio.

### Pruebas de Integración
Las pruebas de integración validan el correcto funcionamiento del PriceController y la interacción con el servicio y repositorio. Se han implementado en la clase PriceControllerTest utilizando Spring Boot Test y MockMvc.

- **CASOS DE PRUEBA EN PriceControllerTest**
1. Validación de respuesta HTTP 200 para consultas exitosas.

2. Validación de estructura de respuesta JSON.

3. Manejo de errores con respuestas HTTP 404 cuando no hay precio disponible.

4. Validación de integración con el repositorio de datos.

**Objetivo:** Garantizar que los endpoints REST respondan correctamente con los datos esperados.



Para ejecutar las pruebas, usa el siguiente comando en la raíz del proyecto:
```
mvn test
```

Para utilizar JaCoCo(Dependencias añadidas en el pom.xml para su funcionamiento) para medir la cobertura de código. Para generar un reporte, ejecuta:
```
mvn clean verify
```


## Contacto

Si tienes dudas o quieres contribuir, contacta conmigo a través de Linkedin: www.linkedin.com/in/cristian-navarro-fernández-6044937a 

Design by: Cristian Navarro Fernandez
