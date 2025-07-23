FROM gradle:8.10.2-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:17.0.12_7-jre-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
COPY ./entrypoint.sh /entrypoint.sh
CMD ["/bin/sh", "/entrypoint.sh"]
