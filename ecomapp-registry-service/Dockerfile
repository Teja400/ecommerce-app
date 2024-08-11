FROM openjdk:21-slim

LABEL version="1.0"
LABEL description="Ecomapp Registry Service Image"
COPY ./target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8761