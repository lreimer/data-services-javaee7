FROM payara/micro:181

COPY build/hazelcast/* /opt/payara/libs/
COPY build/activemq/activemq-rar-5.15.3.rar /opt/payara/deployments/
COPY post-deploy.asadmin /opt/payara/post-deploy.asadmin

RUN mkdir -p /opt/payara/config/

ENTRYPOINT ["java", "-server", "-XX:+UseG1GC", "-Xmx196m", "-Xss256k", "-XX:MaxMetaspaceSize=128m", "-XX:CompressedClassSpaceSize=32m", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--postdeploycommandfile", "/opt/payara/post-deploy.asadmin", "--hzconfigfile", "/opt/payara/config/hazelcast.xml", "--addjars", "/opt/payara/libs/"]

COPY build/libs/weather-processor.war /opt/payara/deployments/
