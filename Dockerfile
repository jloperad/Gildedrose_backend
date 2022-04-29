FROM maven:3.8.5-openjdk-18-slim
WORKDIR /usr/src/app
COPY . .
CMD mvn spring-boot:run