FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
MAINTAINER  Maxim Morev <maxmorev@gmail.com>
RUN mkdir /opt/bootiful
WORKDIR /opt/bootiful
COPY build/libs/minio-rest-api-0.0.2.jar /opt/bootiful/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/bootiful/app.jar"]