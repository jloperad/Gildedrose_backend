FROM maven:3.8.5-openjdk-18-slim
WORKDIR /usr/src/app
COPY . .
ARG DB_H
ARG DB_U
ARG DB_P

ENV DATABASE_HOST = $DB_H
ENV DATABASE_USER = $DB_U
ENV DATABASE_PASSWORD = $DB_P



RUN mvn spring-boot:run

CMD mvn clean compile test