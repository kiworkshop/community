# build
FROM adoptopenjdk/openjdk11:jdk-11.0.4_11-alpine

# nginx
RUN apk update && apk add nginx=1.16.1-r2 && rm -rf /etc/nginx/conf.d/default.conf &&\
    mkdir -p /kiworkshop/logs/nginx/ &&\
    mkdir -p /kiworkshop/service/static

COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

# boot
RUN mkdir -p /usr/local/community/ &&\
    mkdir -p /usr/local/community/resources

WORKDIR /usr/local/community/resources

COPY app app/
COPY config config/
COPY core core/
COPY db-migration db-migration/
COPY domain domain/
COPY gradle gradle/
COPY in-system-available in-system-available/
COPY module module/

COPY build.gradle gradlew lombok.config settings.gradle ./

###########################################

# build
RUN ./gradlew :app:app-auth:bootJar &&\
    ./gradlew :app:app-monolith:bootJar &&\
    mv app/app-auth/build/libs/app-auth-0.0.1-SNAPSHOT.jar ../ &&\
    mv app/app-monolith/build/libs/app-monolith-0.0.1-SNAPSHOT.jar ../ &&\
    cd .. && rm -rf resources/

WORKDIR /usr/local/community

ENTRYPOINT [ "sh", "-c", "nohup nginx -g 'daemon off;' &\
export $(echo $application_env) > /dev/null &&\
nohup java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$(echo $PROFILE) -jar app-auth-0.0.1-SNAPSHOT.jar &\
nohup java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$(echo $PROFILE) -jar app-monolith-0.0.1-SNAPSHOT.jar" ]
