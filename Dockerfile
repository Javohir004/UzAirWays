FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 3000
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]