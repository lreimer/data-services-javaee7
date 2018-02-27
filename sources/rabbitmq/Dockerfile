FROM payara/micro:181

COPY build/mqtt/* /opt/payara/deployments/
COPY build/activemq/* /opt/payara/deployments/
COPY post-deploy.asadmin /opt/payara/post-deploy.asadmin

ENTRYPOINT ["java", "-server", "-XX:+UseG1GC", "-Xmx196m", "-Xss256k", "-XX:MaxMetaspaceSize=128m", "-XX:CompressedClassSpaceSize=32m", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--noCluster", "--postdeploycommandfile", "/opt/payara/post-deploy.asadmin"]

COPY build/libs/rabbitmq-source.war /opt/payara/deployments/
