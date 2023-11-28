FROM eclipse-temurin:17-jdk
VOLUME /tmp
# Ejecuta un clean y un build del tipo bootJar con Gradle.
CMD ["./gradlew", "clean", "bootJar"]
# Copia el jar y lo renombra en el path de la imagen.
COPY build/libs/*.jar /bff/bff.jar
# Copia las properties de la applicación.
COPY build/resources/main/application.properties bff/config/application.properties
EXPOSE 8080 5005
ENTRYPOINT ["java", "-jar","bff/bff.jar"]