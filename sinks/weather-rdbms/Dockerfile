FROM payara/micro:181

COPY build/postgresql/* /opt/payara/libs/
COPY build/activemq/activemq-rar-5.15.3.rar /opt/payara/deployments/
COPY post-deploy.asadmin /opt/payara/post-deploy.asadmin

ENTRYPOINT ["java", "-server", "-XX:+UseG1GC", "-Xmx196m", "-Xss256k", "-XX:MaxMetaspaceSize=128m", "-XX:CompressedClassSpaceSize=32m", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--noCluster", "--addjars", "/opt/payara/libs/", "--postdeploycommandfile", "/opt/payara/post-deploy.asadmin", "--name", "weather-file"]

COPY build/libs/weather-rdbms.war /opt/payara/deployments/
