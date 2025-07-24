FROM amazoncorretto:21-alpine-jdk
LABEL authors="Avinash Kumar"

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-XX:+UseG1GC", "-jar","/app.jar"]