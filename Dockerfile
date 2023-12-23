# Stage 1: Build the application
FROM amazoncorretto:17-alpine as builder

WORKDIR /app

COPY ./mvnw ./pom.xml .
COPY ./.mvn ./.mvn

RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

# Stage 2: Create a runtime container
FROM amazoncorretto:17-alpine as runtime

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]