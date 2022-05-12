FROM maven:3.8.5-openjdk-18-slim
WORKDIR /usr/src/app
COPY . .
ARG DB_H
ENV DATABASE_HOST = $DB_H
CMD mvn spring-boot:run