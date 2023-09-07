FROM maven:3-openjdk-17-slim AS build
COPY . .
RUN mvn clean package

FROM gcr.io/distroless/java17-debian11
COPY --from=build target/phishing-detector-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]