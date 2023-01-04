FROM openjdk:latest
VOLUME /tmp
ADD target/MusicLibrary-0.0.1-SNAPSHOT.jar MusicLibrary-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/MusicLibrary-0.0.1-SNAPSHOT.jar"]
