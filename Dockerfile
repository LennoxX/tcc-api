FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/docker-locacao-api.jar docker-locacao-api.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","docker-locacao-api.jar"]
