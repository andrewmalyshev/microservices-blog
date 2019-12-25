#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
#MAINTAINER Andrii Malyshev
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#RUN mvn package
#FROM openjdk:8-jre-alpine
#WORKDIR /app
#COPY --from=MAVEN_BUILD /build/target/users-service-0.0.1-SNAPSHOT.jar /app/
#ENTRYPOINT ["java", "-jar", "users-service-0.0.1-SNAPSHOT.jar"]
#EXPOSE 8091
FROM openjdk:8
MAINTAINER Andrii Malyshev
ADD target/users-service-0.0.1-SNAPSHOT.jar users-service.jar
ENTRYPOINT ["java", "-jar", "/users-service.jar"]
EXPOSE 8091