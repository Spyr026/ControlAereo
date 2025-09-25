# Control-Aéreo

**Proyecto**: Control de tráfico aéreo — backend en Spring Boot (Java/Maven) y frontend en Angular.

> Este README fue generado automáticamente tras un análisis del repositorio `final-individual-15-master`.

---

## Resumen
Aplicación de ejemplo para gestionar **aeropuertos**, **aviones** y **vuelos**. Incluye:

- Backend: Java Spring Boot (Maven) con REST APIs, validación, seguridad básica y JPA.
- Frontend: aplicación Angular (Angular 17) con proxy hacia el backend.
- Tests: JUnit para backend; pruebas E2E/ejemplos de Cypress en `cypress/`.
- Orquestación mínima: `compose.yaml` con servicio `postgres`.
- CI: `Jenkinsfile` con pipeline Maven/NodeJS y hooks para SonarQube (configurable).

---

## Tecnologías principales

- Java 17 (Spring Boot 3.5.5)
- Maven (wrapper incluido `mvnw`)
- Spring Boot starters: Web, Data JPA, Security, Validation, Actuator
- Bases de datos: H2 (por defecto en `application.properties`), driver PostgreSQL incluido
- Angular 17 (frontend)
- Cypress (E2E examples), Karma/Jasmine (unit tests front)
- Docker / Docker Compose (archivo `compose.yaml` para Postgres)

---

## Estructura principal del repositorio

```
final-individual-15-master/
├─ back/            # Backend Spring Boot (Maven)
├─ front/           # Frontend Angular
├─ cypress/         # Tests e2e (ejemplos)
├─ compose.yaml     # Compose: servicio postgres
├─ Jenkinsfile      # Pipeline Jenkins
└─ LICENSE
```

Dentro de `back/` encontrarás la aplicación Spring Boot en `src/main/java/.../Control/Aereo` con controladores, servicios, repositorios y modelos.

---

## Endpoints REST detectados

> Lista generada a partir de los controladores en `back/src/main/java`.

- `GET` `/api/aeropuertos`  _(controller: AeropuertoController.java)_
- `POST` `/api/aeropuertos`  _(controller: AeropuertoController.java)_
- `GET` `/api/aeropuertos/aviones/disponibles`  _(controller: AeropuertoController.java)_
- `GET` `/api/aeropuertos/disponibles`  _(controller: AeropuertoController.java)_
- `DELETE` `/api/aeropuertos/{id}`  _(controller: AeropuertoController.java)_
- `GET` `/api/aeropuertos/{id}`  _(controller: AeropuertoController.java)_
- `PUT` `/api/aeropuertos/{id}`  _(controller: AeropuertoController.java)_
- `PUT` `/api/aeropuertos/{id}/asignarAvion/{avionId}`  _(controller: AeropuertoController.java)_
- `GET` `/api/aeropuertos/{id}/detalle`  _(controller: AeropuertoController.java)_
- `DELETE` `/api/aeropuertos/{id}/eliminarAvion/{avionId}`  _(controller: AeropuertoController.java)_

- `GET` `/api/aviones`  _(controller: AvionController.java)_
- `POST` `/api/aviones`  _(controller: AvionController.java)_
- `DELETE` `/api/aviones/{id}`  _(controller: AvionController.java)_
- `GET` `/api/aviones/{id}`  _(controller: AvionController.java)_
- `PUT` `/api/aviones/{id}`  _(controller: AvionController.java)_
- `GET` `/api/aviones/{id}/detalle`  _(controller: AvionController.java)_

- `GET` `/api/vuelos`  _(controller: VueloController.java)_
- `POST` `/api/vuelos`  _(controller: VueloController.java)_
- `GET` `/api/vuelos/avion/{id}`  _(controller: VueloController.java)_
- `GET` `/api/vuelos/avionesDisponibles`  _(controller: VueloController.java)_
- `DELETE` `/api/vuelos/{id}`  _(controller: VueloController.java)_
- `GET` `/api/vuelos/{id}`  _(controller: VueloController.java)_
- `PUT` `/api/vuelos/{id}`  _(controller: VueloController.java)_

> Nota: Se detectaron también algunas anotaciones `@RequestMapping` genéricas que no incluyen explicitamente el método HTTP en su atributo; en la lista anterior aparecen como llamadas estándar (GET/POST/PUT/DELETE) según las anotaciones específicas encontradas.

---

## Modelos (entidades) — campos detectados

#### Aeropuerto (`Aeropuerto.java`)
- `id`: `Long`
- `nombre`: `String`
- `codigoIATA`: `String`
- `ciudad`: `String`
- `maximoAviones`: `int`

#### Avion (`Avion.java`)
- `id`: `Long`
- `matricula`: `String`
- `modelo`: `String`
- `aerolinea`: `String`
- `capacidad`: `int`
- `estado`: `EstadoAvion` (enum)
- `aeropuerto`: `Aeropuerto` (relación)

#### Vuelo (`Vuelo.java`)
- `id`: `Long`
- `numeroVuelo`: `String`
- `aeropuertoOrigen`: `Aeropuerto`
- `aeropuertoDestino`: `Aeropuerto`
- `avion`: `Avion`
- `estado`: `EstadoVuelo` (enum)
- `fechaSalida`: `LocalDate`
- `fechaLlegada`: `LocalDate`
- `horaSalida`: `LocalTime`
- `horaLlegada`: `LocalTime`

---

## Cómo ejecutar (entorno de desarrollo)

### Requisitos mínimos
- Java 17
- Maven (si no usas `./mvnw`)
- Node.js (preferible v18+) y npm
- Docker & Docker Compose (opcional para Postgres)

### Ejecutar la base de datos Postgres (opcional)
El `compose.yaml` incluye un servicio básico `postgres`. Para levantarlo:

```bash
# desde la raíz del repo
docker compose -f compose.yaml up -d
# o con docker-compose (si lo usas):
# docker-compose -f compose.yaml up -d
```

> Por defecto la aplicación usa **H2 en memoria** (ver `back/src/main/resources/application.properties`). Postgres está disponible si configuras las propiedades de `spring.datasource.url` para apuntar al contenedor `postgres`.

### Backend (Spring Boot)

```bash
# desde la raíz del repo
cd back
# construir
./mvnw clean package
# ejecutar (usa H2 por defecto)
./mvnw spring-boot:run
# o con jar
java -jar target/Control-Aereo-0.0.1-SNAPSHOT.jar
```

Variables útiles para producción/usar Postgres (ejemplo):
- `SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/mydatabase`
- `SPRING_DATASOURCE_USERNAME=myuser`
- `SPRING_DATASOURCE_PASSWORD=secret`

Puedes exportarlas en el entorno o configurarlas en un `application-prod.properties`.

### Frontend (Angular)

```bash
cd front
npm install
npm start   # ejecuta `ng serve` y arranca en http://localhost:4200
```

El frontend usa `proxy.conf.json` para redirigir llamadas API a `localhost:8080` (o donde corra el backend).

---

## Tests

### Backend

```bash
cd back
./mvnw test
```

### Frontend

```bash
cd front
npm test
```

### Cypress (E2E)
El repo incluye ejemplos de Cypress en la carpeta `cypress/` y configuración en `front/cypress.config.js` y `cypress.config.js` en raíz.
Ejecutar (desde la raíz o desde `front` según tu instalación):

```bash
npx cypress open    # interfaz interactiva
npx cypress run     # ejecución headless
```

---

## CI / Jenkins

El `Jenkinsfile` incluido provee un pipeline que usa herramientas Maven y JDK configuradas en Jenkins, ejecuta build, tests y tiene placeholders para SonarQube. Revisa y adapta los nombres de herramientas (`Maven3.8.7`, `Jdk17`) y la URL de `SONAR_HOST_URL` a tu entorno.

---

## Futuras Acualizaciones

- Añadir **Dockerfile** para backend y frontend para permitir empaquetado y despliegue con `docker compose` completo.
- Externalizar la configuración sensible (secrets) y no dejar contraseñas en ficheros de propiedades.
- Añadir **OpenAPI / Swagger** para documentar automáticamente los endpoints. También facilitaría generar clientes.
- Añadir tests E2E específicos de la aplicación (los actuales son ejemplos genéricos de Cypress).
- Mejorar el manejo de paginación/filtros en endpoints de listado si se espera gran volumen.
- Añadir un script `make` o `run.sh` para facilitar puesta en marcha en local.

---

## Licencia
Ver archivo `LICENSE` en la raíz.

---

## ¿Qué hace falta para "terminar"?
- Verificar que las rutas de `proxy.conf.json` y la URL del backend coinciden.
- Decidir si se quiere usar Postgres en desarrollo (actualmente la app arranca con H2 por defecto).
- Crear Dockerfiles para back/front si se desea contenedizar todo.

---

