FROM openjdk:17-jdk-slim
WORKDIR /usr/src/app

COPY ./build/libs/poupa-online-api-0.0.1.jar ./api.jar
COPY ./prod.env ./.env

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "./api.jar", "--spring.profiles.active=prod"]
