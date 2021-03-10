

FROM openjdk:jre-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} atm.jar
ENTRYPOINT ["java","-jar","/atm.jar"]