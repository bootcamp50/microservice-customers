FROM openjdk:11
ADD ./target/microservice-customers-0.0.1-SNAPSHOT.jar aplicacion.jar
ENTRYPOINT ["java", "-jar", "aplicacion.jar"]