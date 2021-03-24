FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
COPY ./target/spring-rest-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["./wait-for-it.sh", "mysql-container:3306", "--", "java", "-jar", "app.jar"]
