FROM 192.168.104.45/devops/openjdk:8

RUN mkdir -p /home/it/service-security/logs

COPY target/service-security-0.0.1-SNAPSHOT.jar /home/it/service-security

WORKDIR /home/it/service-security
VOLUME /home/it/service-security/logs

ENTRYPOINT ["java","-Xms128m","-Xmx512m","-jar","service-security-0.0.1-SNAPSHOT.jar"]
EXPOSE 8085