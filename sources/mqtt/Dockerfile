FROM payara/micro:181

COPY build/libs/mqtt-source.war /opt/payara/deployments/
COPY build/mqtt/* /opt/payara/deployments/

ENTRYPOINT ["java", "-server", "-XX:+UseG1GC", "-Xmx196m", "-Xss256k", "-XX:MaxMetaspaceSize=128m", "-XX:CompressedClassSpaceSize=32m", "-jar", "/opt/payara/payara-micro.jar"]
CMD ["--deploy", "/opt/payara/deployments/mqtt-rar-0.1.0.rar", "--deploy", "/opt/payara/deployments/mqtt-source.war"]
