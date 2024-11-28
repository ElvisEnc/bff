#!/bin/bash
set -m
# Configurar parámetros de Java
PARAMS="${JAVA_EXTRA_ENVS} -XX:+UnlockExperimentalVMOptions -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom"
# Iniciar aplicación
java $PARAMS -jar /app/spring-boot-application.jar
# Extender la aplicación
[[ -z "${@}" ]] || exec "${@}"
