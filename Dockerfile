FROM openjdk:21

VOLUME /tmp

EXPOSE 8080

ADD ./target/OrceJa-0.0.1-SNAPSHOT.jar orceJa.jar

ENTRYPOINT ["java", "-jar", "/orceJa.jar"]