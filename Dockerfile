FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war
RUN sed -i 's/port="8080"/port="7860"/g' /usr/local/tomcat/conf/server.xml

EXPOSE 7860
CMD ["catalina.sh", "run"]
