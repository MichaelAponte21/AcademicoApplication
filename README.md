# demo-academico

API REST académica de Ingeniería de Software III: estudiantes, programas, asignaturas, grupos y matrículas.

**Stack:** Java 21 · Spring Boot 3.2.5 (Web, Data JPA, Validation) · H2 en memoria · springdoc-openapi (Swagger) · JavaFaker

## Cómo ejecutar

### Con Maven

```bash
./mvnw spring-boot:run
```

O compilando el `.jar`:

```bash
./mvnw clean package
java -jar target/demo-academico-0.0.1-SNAPSHOT.jar
```

### Con Docker

```bash
docker compose up -d --build
```

Para detenerlo:

```bash
docker compose down
```

## URLs

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI (JSON) | http://localhost:8080/v3/api-docs |
| Consola H2 | http://localhost:8080/h2-console |

Datos de conexión de la consola H2:

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:demoacademico` |
| User Name | `sa` |
| Password | *(vacío)* |

## Datos iniciales (seeder)

Al arrancar, si `app.seed.enabled=true` y las tablas están vacías, se siembran datos con Faker. Las cantidades se configuran en `application.yaml`:

```yaml
app:
  seed:
    enabled: true
    cantidad-estudiantes: 50
    cantidad-programas: 3
    cantidad-asignaturas: 10
    cantidad-grupos: 20
```

En Docker se pueden cambiar con las variables `APP_SEED_ENABLED`, `APP_SEED_CANTIDAD_ESTUDIANTES`, `APP_SEED_CANTIDAD_PROGRAMAS`, `APP_SEED_CANTIDAD_ASIGNATURAS` y `APP_SEED_CANTIDAD_GRUPOS` (ver `docker-compose.yml`).

## Estructura por módulos

```
co.edu.demoacademico
├── common
│   ├── api          ApiResponse, ResponseBuilder
│   ├── exception    BusinessException, NotFoundException
│   └── controller   GlobalExceptionHandler
├── config           DataSeeder, DataSeederAcademico
├── estudiantes      model · dto · repository · service · handler · controller · port
├── programas        model · dto · repository · service · handler · controller · port
├── asignaturas      model · dto · repository · service · handler · controller · port
├── grupos           model · dto · repository · service · handler · controller · port
└── matriculas       model · dto · repository · service · handler · controller
```

Cada módulo sigue el estilo DTO + Handler + Service + Controller y responde siempre con `ApiResponse`.

## Ports creados

Un **Port** es la interfaz pública de un módulo: los demás módulos la usan en lugar de inyectar su repositorio.

| Port | Módulo dueño | Implementación | Lo usa |
|---|---|---|---|
| `EstudianteQueryPort` | estudiantes | `EstudianteServiceImpl` | `MatriculaServiceImpl` |
| `GrupoQueryPort` | grupos | `GrupoServiceImpl` | `MatriculaServiceImpl` |
| `ProgramaQueryPort` | programas | `ProgramaServiceImpl` | `AsignaturaHandler` |
| `AsignaturaQueryPort` | asignaturas | `AsignaturaServiceImpl` | `GrupoHandler` |

```java
public interface EstudianteQueryPort {
    Estudiante obtenerPorId(Long id);
}
```

**Regla:** el módulo `matriculas` no inyecta `EstudianteRepository` ni `GrupoRepository`; solo usa `EstudianteQueryPort` y `GrupoQueryPort`. Para comprobarlo:

```bash
grep -rn "estudiantes.repository\|grupos.repository" src/main/java/co/edu/demoacademico/matriculas
```

El comando no debe mostrar resultados.

## Endpoints

| Recurso | Endpoints |
|---|---|
| Estudiantes | `POST`, `GET` (paginado), `GET /{id}`, `GET /buscar?email=`, `PUT /{id}`, `DELETE /{id}` en `/api/estudiantes` |
| Programas | `POST`, `GET` (paginado), `GET /{id}`, `PUT /{id}`, `DELETE /{id}` en `/api/programas` |
| Asignaturas | `POST`, `GET`, `DELETE /{id}` en `/api/asignaturas` |
| Grupos | `POST`, `GET` (paginado), `DELETE /{id}` en `/api/grupos` |
| Matrículas | `POST`, `GET` (paginado) en `/api/matriculas` |

### Reglas de la matrícula

| Regla | Respuesta |
|---|---|
| El estudiante o el grupo no existen | `404` |
| El estudiante ya está matriculado en ese grupo | `400` |
| El grupo no tiene cupo disponible | `400` |

Ejemplo:

```json
POST /api/matriculas
{
  "estudianteId": 1,
  "grupoId": 1
}
```
