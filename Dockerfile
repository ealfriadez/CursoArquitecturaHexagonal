FROM openjdk:17

WORKDIR /app

COPY ./target/CursoArquitecturaHexagonal-0.0.1-SNAPSHOT.jar .

EXPOSE 8087

CMD ["java", "-jar", "CursoArquitecturaHexagonal-0.0.1-SNAPSHOT.jar" ]