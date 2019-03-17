FROM maven:3-jdk-8

WORKDIR /home

COPY . .
#COPY src/main/resources/application-docker.properties src/main/resources/application.properties

RUN mvn package --quiet -DskipTests

WORKDIR /home/target/

EXPOSE 8080

CMD ["java", "-jar", "city-suggester-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=docker"]