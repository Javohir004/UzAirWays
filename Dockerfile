FROM openjdk:17-jdk-alpine

COPY . .

RUN ./gradlew build

ENTRYPOINT ["java", "-jar", "UzAirWays-0.0.1-SNAPSHOT.jar"]
