FROM payara/micro:181

COPY build/libs/payara-hazelcast.war /opt/payara/deployments/

ENTRYPOINT ["java", "-server", "-XX:+UseG1GC", "-Xmx256m", "-Xss256k", "-XX:MaxMetaspaceSize=128m", "-XX:CompressedClassSpaceSize=32m", "-XX:+UseStringDeduplication", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--deploymentDir", "/opt/payara/deployments"]
