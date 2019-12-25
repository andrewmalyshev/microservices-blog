#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
#MAINTAINER Andrii Malyshev
#COPY pom.xml /build/
#COPY src/ /build/src/
#WORKDIR /build/
#RUN mvn package
#FROM openjdk:8-jre-alpine
#WORKDIR /app
#COPY --from=MAVEN_BUILD /build/target/discovery-service-0.0.1-SNAPSHOT.jar /app/
#ENTRYPOINT ["java", "-jar", "discovery-service-0.0.1-SNAPSHOT.jar"]
#EXPOSE 8761
FROM openjdk:8
MAINTAINER Andrii Malyshev
ADD target/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "/discovery-service.jar"]
EXPOSE 8761