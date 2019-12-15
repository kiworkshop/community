FROM adoptopenjdk/openjdk11:jdk-11.0.4_11-alpine

RUN mkdir -p /usr/local/community/

COPY ./mother-api/build/libs/mother-api-0.0.1-SNAPSHOT.jar /usr/local/community/

WORKDIR /usr/local/community/

EXPOSE 8080

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "mother-api-0.0.1-SNAPSHOT.jar" ]
