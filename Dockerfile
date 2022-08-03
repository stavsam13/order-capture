FROM openjdk:11-jdk
LABEL maintaner = "stavros.net"
ADD target/backend-technical-test-2.0.0-SNAPSHOT.jar springboot-docker-tui.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","springboot-docker-tui.jar"]
#COPY ${JAR_FILE} app.jar
