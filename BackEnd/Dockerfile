# Usa imagen de Java 17
FROM eclipse-temurin:17-jdk

# Crea el directorio de la app
WORKDIR /app

# Copia el JAR generado por Gradle
COPY build/libs/*.jar app.jar

# Expone el puerto dinámico (Render asigna uno y lo pasa como $PORT)
EXPOSE 8080

# Ejecuta la app
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
