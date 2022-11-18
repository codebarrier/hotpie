# Specify a base image
FROM maven:3.8.6-openjdk-18 as built

WORKDIR /usr/code

# Install some depenendencies

COPY ./ ./
RUN mvn clean install -DskipTests

#stage 2
FROM openjdk:18.0.2.1-jdk
ARG JAR_FILE=/usr/code/target/*.jar
COPY --from=built ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

