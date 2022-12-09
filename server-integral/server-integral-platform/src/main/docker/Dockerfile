FROM openjdk:8-jdk-alpine
VOLUME /data/tmp
ADD server-integral-platform-1.0.0.jar integral-platform.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/integral-platform.jar"]
RUN echo 'Asia/Shanghai' >/etc/timezone