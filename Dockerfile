FROM openjdk:11.0.11-jre-slim

WORKDIR /app

ENV SERVER_PORT 8080

EXPOSE 8080

COPY ./build/libs/articles-backend-1.0.0-SNAPSHOT.jar /app/articles-backend-1.0.0-SNAPSHOT.jar

ENTRYPOINT [ "java" ]
CMD [ "-Dfile.encoding=UTF-8", "-Duser.timezone=UTC", "-XX:+UseG1GC", "-jar", "/app/articles-backend-1.0.0-SNAPSHOT.jar" ]
