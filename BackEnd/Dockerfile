FROM gradle:8.4-jdk17 AS builder
WORKDIR /app
COPY . .
WORKDIR /app/ElBuenSabor-backend/BackEnd
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/ElBuenSabor-backend/BackEnd/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
