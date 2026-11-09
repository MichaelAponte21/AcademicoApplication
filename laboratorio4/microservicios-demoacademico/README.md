# Microservicios demo-academico (Lab 04)

Dos microservicios Spring Boot 3.2.5 (Java 21) que implementan autenticación con JWT entre servicios:

| Servicio | Puerto | Responsabilidad |
|---|---|---|
| `auth-service` | 8081 | Usuarios en H2 con contraseñas cifradas con BCrypt. Genera y valida tokens JWT. |
| `consulta-service` | 8082 | CRUD de estudiantes. Un filtro valida el token contra `auth-service` (vía OpenFeign) antes de dar acceso a `/api/estudiantes`. |

```
Cliente ─POST /auth/login──────────────────────────▶ auth-service   (devuelve el JWT)
Cliente ─GET /api/estudiantes + Bearer <token>─────▶ consulta-service
                                                       └─GET /auth/validate─▶ auth-service
```

El proyecto base del que parte `consulta-service` está en `../demo-academico`.

## Ejecutar localmente

Requisitos: Java 21 y Maven.

```bash
cd auth-service && mvn clean package -DskipTests && java -jar target/auth-service-0.0.1-SNAPSHOT.jar
```

En otra terminal:

```bash
cd consulta-service && mvn clean package -DskipTests && java -jar target/consulta-service-0.0.1-SNAPSHOT.jar
```

Levante primero `auth-service`: `consulta-service` lo busca en `http://localhost:8081` (propiedad `services.auth.url`).

## Ejecutar con Docker Compose

Requisito: Docker. Los Dockerfiles son multi-stage (compilan con Maven dentro de Docker), así que no hace falta compilar antes.

```bash
docker compose up -d --build
```

```bash
docker ps
```

Para detenerlo:

```bash
docker compose down
```

Dentro de Docker, `consulta-service` llama a `auth-service` por el nombre del servicio (`SERVICES_AUTH_URL=http://auth-service:8081`), no por `localhost`.

## URLs

| Recurso | URL |
|---|---|
| Swagger de auth-service | http://localhost:8081/swagger-ui.html |
| Swagger de consulta-service | http://localhost:8082/swagger-ui.html |
| H2 Console de auth-service | http://localhost:8081/h2-console (JDBC URL `jdbc:h2:mem:authdb`) |
| H2 Console de consulta-service | http://localhost:8082/h2-console (JDBC URL `jdbc:h2:mem:demoacademico`) |

En ambas consolas H2: usuario `sa`, contraseña vacía.

## Usuarios de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `Admin2026*` | ADMIN |
| `docente` | `Docente2026*` | DOCENTE |
| `estudiante1` | `Estu2026*` | ESTUDIANTE |

## Flujo correcto con el botón Authorize

1. Abra el Swagger de **auth-service** y ejecute `POST /auth/login`:
   ```json
   { "username": "admin", "password": "Admin2026*" }
   ```
2. Copie **solo** el valor del campo `token` (sin comillas, sin llaves y sin la palabra `Bearer`).
3. Opcional: compruébelo en `GET /auth/validate` pegándolo en el parámetro `token`; debe responder `"valid": true`.
4. Abra el Swagger de **consulta-service** y pulse **Authorize** (arriba a la derecha).
5. Pegue el token **sin** el prefijo `Bearer` (Swagger lo agrega solo), pulse **Authorize** y luego **Close**.
6. Ejecute `GET /api/estudiantes`. Swagger envía `Authorization: Bearer <token>` y la respuesta es 200.

| Situación | Respuesta de `GET /api/estudiantes` |
|---|---|
| Token válido cargado en Authorize | 200 con la página de estudiantes |
| Sin autorizar | 401 `Token no enviado o formato inválido` |
| Token incorrecto o vencido (1 hora) | 401 `Token inválido o expirado` |

`POST /api/estudiantes` (crear) es público y no requiere token.
