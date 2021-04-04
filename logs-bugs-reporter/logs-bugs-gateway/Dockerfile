FROM openjdk:14.0.2
EXPOSE 9191
ADD ./target/logs-bugs-gataway-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]