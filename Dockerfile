FROM maven:3.8.5-openjdk-17-slim
WORKDIR /usr/src/app
COPY . .

ARG DB_H
ARG DB_U
ARG DB_P

ENV DATABASE_HOST = $DB_H
ENV DATABASE_USER = $DB_U
ENV DATABASE_PASSWORD = $DB_P

CMD mvn spring-boot:run