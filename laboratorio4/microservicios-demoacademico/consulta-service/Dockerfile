# ============================================================
# Etapa 1: build (Maven + JDK 21)
# ============================================================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Dependencias en una capa propia: se reutiliza mientras el pom.xml no cambie
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

COPY src src
RUN mvn -q -B package -DskipTests

# ============================================================
# Etapa 2: runtime (solo JRE 21, usuario sin privilegios)
# ============================================================
FROM eclipse-temurin:21-jre
WORKDIR /app
RUN useradd --system --no-create-home appuser
COPY --from=build /build/target/consulta-service-0.0.1-SNAPSHOT.jar app.jar
USER appuser
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
