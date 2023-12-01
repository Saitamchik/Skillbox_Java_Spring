FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/default-students.txt src/main/resources/default-students.txt
ENTRYPOINT ["java","-Dspring.profiles.active=default","-jar","/app.jar"]