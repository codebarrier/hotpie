# Specify a base image
FROM maven:3.8.6-openjdk-18 as built
ENV HOME=/usr/code
WORKDIR /usr/code

# Install some depenendencies
ADD pom.xml ./
RUN mvn dependency:resolve

ADD ./ ./
RUN mvn install -DskipTests


#stage 2
FROM openjdk:18.0.2.1-jdk
ARG JAR_FILE=/usr/code/target/*.jar
COPY --from=built ${JAR_FILE} app.jar
VOLUME /store
ENTRYPOINT ["java","-jar","/app.jar"]

