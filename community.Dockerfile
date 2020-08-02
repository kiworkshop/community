FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine

ENV ALPINE_MIRROR "http://dl-cdn.alpinelinux.org/alpine"

# install npm, nginx and create directories to build.
RUN echo "${ALPINE_MIRROR}/edge/main" >> /etc/apk/repositories &&\
    apk add --no-cache npm=12.18.3-r0 --repository="http://dl-cdn.alpinelinux.org/alpine/edge/community" &&\
    apk add nginx=1.18.0-r3 && rm -rf /etc/nginx/conf.d/default.conf &&\
    mkdir -p /kiworkshop/logs/nginx/ &&\
    mkdir -p /kiworkshop/service/static &&\
    mkdir -p /usr/local/community/ &&\
    mkdir -p /usr/local/community/resources

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

WORKDIR /usr/local/community/resources

# backend
COPY community-backend .

# frontend
COPY community-frontend frontend

###########################################

# build
RUN ./gradlew :app:app-auth:bootJar &&\
    ./gradlew :app:app-monolith:bootJar &&\
    mv app/app-auth/build/libs/app-auth-0.0.1-SNAPSHOT.jar ../ &&\
    mv app/app-monolith/build/libs/app-monolith-0.0.1-SNAPSHOT.jar ../ &&\
    cd frontend && sh build.sh && cd ../.. &&\
    mv resources/frontend/app/community-admin/out admin &&\
    mv resources/frontend/app/community-frontend frontend &&\
    rm -rf resources/

WORKDIR /usr/local/community

ENTRYPOINT [ "sh", "-c", "export $(echo $application_env) > /dev/null &&\
    nohup java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$(echo $PROFILE) -jar app-auth-0.0.1-SNAPSHOT.jar &\
    export $(echo $application_env) > /dev/null &&\
    nohup java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$(echo $PROFILE) -jar app-monolith-0.0.1-SNAPSHOT.jar &\
    cd frontend && nohup npm run start &\
    nohup nginx -g 'daemon off;'" ]
