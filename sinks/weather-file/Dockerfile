FROM payara/micro:181

RUN mkdir -p /data/

COPY build/activemq/activemq-rar-5.15.3.rar /opt/payara/deployments/
COPY post-deploy.asadmin /opt/payara/post-deploy.asadmin
COPY build/libs/weather-file.war /opt/payara/deployments/

ENTRYPOINT ["java", "-server", "-XX:+UseG1GC", "-Xmx196m", "-Xss256k", "-XX:MaxMetaspaceSize=128m", "-XX:CompressedClassSpaceSize=32m", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--postdeploycommandfile", "/opt/payara/post-deploy.asadmin", "--name", "weather-file"]
