FROM adoptopenjdk/openjdk11:jdk-11.0.4_11-alpine

RUN mkdir -p /usr/local/community/
RUN mkdir -p /usr/local/community/resources

WORKDIR /usr/local/community/resources

# common
COPY common common/
COPY config config/
COPY gradle gradle/
COPY build.gradle gradlew gradlew.bat lombok.config settings.gradle ./

###########################################

# api
COPY mother-api mother-api/

# build
RUN ./gradlew :mother-api:bootJar
COPY mother-api/build/libs/mother-api-0.0.1-SNAPSHOT.jar ../
WORKDIR /usr/local/community

# remove redundant resources
RUN rm -rf resources/

EXPOSE 8080

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "mother-api-0.0.1-SNAPSHOT.jar" ]
