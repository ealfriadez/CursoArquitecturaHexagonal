ARG MS_NAME=students_service

FROM openjdk:17 AS build-image-stage

ARG MS_NAME

WORKDIR /app/${MS_NAME}





FROM openjdk:17