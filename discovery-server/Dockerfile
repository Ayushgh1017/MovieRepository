#BUILD
FROM gradle:8.10-jdk23 AS build
WORKDIR /app
COPY build.gradle settings.gradle gradlew* ./
COPY gradle/ gradle/
COPY src ./src
RUN gradle clean build -x test

#RUN
FROM eclipse-temurin:23-jdk-alpine
WORKDIR /app
COPY --from=build /app/build/libs/discovery-server-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "discovery-server-0.0.1-SNAPSHOT.jar"]