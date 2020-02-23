# build
FROM adoptopenjdk/openjdk11:jdk-11.0.4_11-alpine

# nginx
RUN apk update && apk add nginx

RUN rm -rf /etc/nginx/conf.d/default.conf
RUN mkdir -p /kiworkshop/logs/nginx/
RUN mkdir -p /kiworkshop/service/static

COPY mother-api/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

# common
RUN mkdir -p /usr/local/community/
RUN mkdir -p /usr/local/community/resources

WORKDIR /usr/local/community/resources

COPY common common/
COPY config config/
COPY gradle gradle/
COPY build.gradle gradlew gradlew.bat lombok.config settings.gradle ./

###########################################

# api
COPY mother-api mother-api/

# build
RUN ./gradlew :mother-api:bootJar
RUN mv mother-api/build/libs/mother-api-0.0.1-SNAPSHOT.jar ../
WORKDIR /usr/local/community

# remove redundant resources
RUN rm -rf resources/

ENTRYPOINT [ "sh", "-c", "nohup nginx -g 'daemon off;' &\
export $(echo $application_env) > /dev/null &&\
java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$(echo $PROFILE) -jar mother-api-0.0.1-SNAPSHOT.jar" ]
