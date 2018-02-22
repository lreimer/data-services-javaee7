FROM payara/server-full:181

ENV AS_ADMIN $PAYARA_PATH/bin/asadmin
ENV DOMAIN domain1

COPY build/activemq/activemq-rar-5.15.3.rar /tmp/

RUN $AS_ADMIN start-domain $DOMAIN && \
    $AS_ADMIN --user admin --passwordfile=/opt/pwdfile deploy --type rar --name activemq-rar /tmp/activemq-rar-5.15.3.rar && \
    $AS_ADMIN --user admin --passwordfile=/opt/pwdfile create-resource-adapter-config --property ServerUrl='tcp\://message-queue\:61616':UserName='admin':Password='admin' activemq-rar && \
    $AS_ADMIN --user admin --passwordfile=/opt/pwdfile create-connector-connection-pool --raname activemq-rar --connectiondefinition javax.jms.ConnectionFactory --ping false --isconnectvalidatereq true jms/activeMqConnectionPool && \
    $AS_ADMIN --user admin --passwordfile=/opt/pwdfile create-connector-resource --poolname jms/activeMqConnectionPool jms/activeMqConnectionFactory && \
    $AS_ADMIN --user admin --passwordfile=/opt/pwdfile create-admin-object --raname activemq-rar --restype javax.jms.Topic --property PhysicalName=VEHICLE.LOCATION jms/VehicleLocation && \
    $AS_ADMIN stop-domain $DOMAIN

COPY build/libs/csv-source.war /opt/payara41/glassfish/domains/domain1/autodeploy/
