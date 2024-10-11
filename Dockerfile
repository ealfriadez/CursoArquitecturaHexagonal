ARG MS_NAME=students_service

FROM openjdk:17 AS build-image-stage

ARG MS_NAME

WORKDIR /app/$MS_NAME

COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

# install dependecies
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring.boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN  ./mvnw clean package -DskipTests

FROM openjdk:17

ARG MS_NAME=students_service

WORKDIR /app

COPY --from=build-image-stage /app/$MS_NAME/target/CursoArquitecturaHexagonal-0.0.1-SNAPSHOT.jar .

EXPOSE 8087

CMD ["java", "-jar", "CursoArquitecturaHexagonal-0.0.1-SNAPSHOT.jar" ]