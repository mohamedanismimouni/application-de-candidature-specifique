FROM adoptopenjdk:11-jre-hotspot
#ADD ./polaris-config /polaris-config
COPY target/*.jar  /config.jar
ENTRYPOINT ["sh", "-c", "java -jar /config.jar"]





