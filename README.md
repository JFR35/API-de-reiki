# API REST Spring Boot con Arquitectura Hexagonal

Este repositorio contiene una API REST desarrollada con Spring Boot que implementa los siguientes patrones y prácticas:

* **Arquitectura Hexagonal (Puertos y Adaptadores):** Separación clara de la lógica de negocio del framework y las dependencias externas.
* **Data Transfer Objects (DTOs):** Uso de DTOs para la transferencia de datos entre capas y la exposición en la API.
* **Manejo de Excepciones Personalizadas:** Definición y uso de excepciones específicas del dominio para un mejor control y respuesta de errores.
* **Pruebas Unitarias:** Tests enfocados en la lógica individual de los componentes (servicios, casos de uso).
* **Pruebas de Integración:** Tests que verifican la interacción entre diferentes partes de la aplicación (controladores, servicios, repositorios).

## Arquitectura Hexagonal

El proyecto sigue una arquitectura hexagonal, también conocida como puertos y adaptadores. Esto se traduce en las siguientes capas principales:

* **Dominio (Core):** Contiene la lógica de negocio pura, entidades y casos de uso (interactores). No depende de ningún framework o tecnología externa. Define interfaces (puertos) para interactuar con el mundo exterior.
* **Aplicación (Casos de Uso/Servicios de Aplicación):** Contiene la lógica específica de la aplicación que orquesta el dominio para cumplir con los requisitos del sistema. También define interfaces para los adaptadores.
* **Infraestructura (Adaptadores):** Implementa los puertos definidos por el dominio y la capa de aplicación para interactuar con el mundo exterior. Esto incluye:
    * **Adaptadores de Presentación (API REST):** Controladores Spring REST que reciben las peticiones y responden. Utilizan DTOs para la entrada y salida de datos.
    * **Adaptadores de Persistencia:** Implementaciones de repositorios (usando Spring Data JPA, por ejemplo) para interactuar con la base de datos.
    * **Adaptadores Externos:** Integraciones con otros servicios o APIs.

## Data Transfer Objects (DTOs)

Se utilizan DTOs para:

* Recibir los datos de las peticiones API (request bodies).
* Enviar los datos en las respuestas API (response bodies).
* Transferir datos entre las capas de la aplicación, desacoplando la representación externa de las entidades del dominio.

Esto permite una mayor flexibilidad y evita exponer directamente la estructura interna de las entidades.

## Manejo de Excepciones Personalizadas

Se han definido excepciones específicas del dominio para representar diferentes situaciones de error que pueden ocurrir en la lógica de negocio. Esto permite:

* Un manejo de errores más semántico y específico.
* Proporcionar mensajes de error más informativos a los clientes de la API.
* Implementar un manejo centralizado de excepciones a través de `@ExceptionHandler` en los controladores para generar respuestas HTTP adecuadas (códigos de estado y cuerpos de error).

## Pruebas

El proyecto incluye diferentes tipos de pruebas para garantizar la calidad y el correcto funcionamiento de la API:

* **Pruebas Unitarias (en la carpeta `src/test/java`):** Se utilizan frameworks como JUnit y Mockito para probar unidades de código individuales (métodos de servicios, casos de uso, etc.) de forma aislada, utilizando mocks para simular las dependencias.
* **Pruebas de Integración (en la carpeta `src/test/java` o en una carpeta separada como `src/test/integration`):** Estas pruebas verifican la interacción entre varios componentes de la aplicación. Por ejemplo:
    * Probar los endpoints de la API (controladores) en conjunto con los servicios y la capa de persistencia (a menudo utilizando un contexto de Spring más completo y una base de datos en memoria como H2).
    * Verificar el correcto funcionamiento del manejo de excepciones y las respuestas de error de la API.

## Cómo ejecutar la aplicación

1.  Asegúrate de tener instalado Java y Maven o Gradle.
2.  Clona este repositorio.
3.  Navega al directorio raíz del proyecto desde tu terminal.
4.  Ejecuta el comando:
    * **Maven:** `mvn spring-boot:run`
    * **Gradle:** `gradle bootRun`

La API estará disponible en el puerto configurado (por defecto suele ser el 8080).

## Cómo ejecutar las pruebas

1.  Navega al directorio raíz del proyecto desde tu terminal.
2.  Ejecuta el comando:
    * **Maven:** `mvn test`
    * **Gradle:** `gradle test`

Se ejecutarán todas las pruebas unitarias y de integración definidas en el proyecto.

## Estructura del Proyecto (Ejemplo)

├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── application/        # Casos de Uso / Servicios de Aplicación
│   │   │       ├── domain/             # Lógica de Negocio, Entidades, Puertos
│   │   │       ├── infrastructure/     # Adaptadores
│   │   │       │   ├── api/            # Adaptadores de Presentación (Controladores, DTOs)
│   │   │       │   ├── persistence/    # Adaptadores de Persistencia (Repositorios)
│   │   │       │   └── external/       # Adaptadores Externos
│   │   │       └── exceptions/         # Excepciones Personalizadas
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/example/
│               ├── application/        # Tests Unitarios de la capa de Aplicación
│               ├── domain/             # Tests Unitarios de la capa de Dominio
│               └── infrastructure/     # Tests de Integración
│                   └── api/            # Tests de Integración de la API
├── pom.xml  
