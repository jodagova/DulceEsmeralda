# ---- Etapa de compilacion ----
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Cache de dependencias: primero solo el wrapper y el pom
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B -ntp dependency:go-offline

# Codigo y empaquetado
COPY src/ src/
RUN ./mvnw -B -ntp clean package -DskipTests

# ---- Etapa de ejecucion ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/app.jar app.jar

# Perfil por defecto para la demo (H2 en memoria). Se puede sobrescribir en Render.
ENV SPRING_PROFILES_ACTIVE=dev
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
