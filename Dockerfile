FROM ubuntu:20.04

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y openjdk-16-jdk && \
    apt-get clean

EXPOSE 8080
EXPOSE 8090

COPY /build/libs/*.jar /home/user/any.jar
WORKDIR /home/user
ENTRYPOINT ["java","-jar","any.jar"]

