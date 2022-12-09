FROM openjdk:8-jdk-alpine
VOLUME /data/tmp
ADD server-integral-app-1.0.0.jar integral-app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/integral-app.jar"]
RUN echo 'Asia/Shanghai' >/etc/timezone