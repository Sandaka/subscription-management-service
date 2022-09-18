FROM maven:3.6.1-jdk-8-alpine AS build
RUN mkdir -p /workspace5
WORKDIR /workspace5
COPY pom.xml /workspace5
COPY src /workspace5/src
RUN mvn -f pom.xml clean package
#RUN mvn clean install

FROM openjdk:8-alpine
COPY --from=build /workspace5/target/*.jar subscription-management-service-1.0.0-SNAPSHOT.jar
EXPOSE 8084
ENTRYPOINT ["java","-jar","subscription-management-service-1.0.0-SNAPSHOT.jar"]


#FROM openjdk:8-jdk-alpine
##LABEL maintainer="author@javatodev.com"
#VOLUME /main-app
#ADD build/libs/subscription-management-service-1.0.0-SNAPSHOT.jar app.jar
#EXPOSE 8084
#ENTRYPOINT ["java", "-jar","/app.jar"]


#FROM maven:3.6-jdk-8-alpine as build
#RUN mkdir -p /workspace
#WORKDIR /workspace
#COPY pom.xml /workspace
#COPY src /workspace/src
#RUN mvn -f pom.xml clean install

#FROM openjdk:8
#VOLUME /tmp
##RUN mvn clean install
#ADD target/cloud-eureka-discovery-service-1.0.0-SNAPSHOT.jar cloud-eureka-discovery-service-1.0.0-SNAPSHOT.jar
#EXPOSE 8083
#ENTRYPOINT ["java","-jar","cloud-eureka-discovery-service-1.0.0-SNAPSHOT.jar"]