FROM adoptopenjdk:11-jre-hotspot
MAINTAINER onkar.s.abhishek@gmail.com
WORKDIR /employee-inventory/app
COPY target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","./app.jar"]