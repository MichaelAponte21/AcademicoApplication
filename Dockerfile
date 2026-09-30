# ============================================================
# Etapa 1: COMPILACIÓN (JDK + Maven wrapper)
# ============================================================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /build

# Primero solo lo necesario para resolver dependencias: esta capa se
# cachea y no se repite mientras el pom.xml no cambie.
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN sh ./mvnw -q -B dependency:go-offline

# Después el código fuente. Los tests se omiten aquí (se corren en CI/local).
COPY src src
RUN sh ./mvnw -q -B package -DskipTests

# ============================================================
# Etapa 2: EJECUCIÓN (solo JRE, imagen final más liviana)
# ============================================================
FROM eclipse-temurin:21-jre
ENV TZ="America/Bogota"
WORKDIR /opt/demo-academico

# Usuario sin privilegios: la app no corre como root
RUN useradd --system --no-create-home appuser
COPY --from=build /build/target/*.jar app.jar
USER appuser

EXPOSE 8080
ENTRYPOINT ["java","-Xmx512m","-jar","app.jar"]
