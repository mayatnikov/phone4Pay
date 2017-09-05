#
#  __________ порядок запуска компонент Phone4Pay
#
#  Redis server
cd /H/Phone4Pay/redis 
./redis-server redis.conf

# очередь сообщений  ActiveMQ
/H/Java/apache-activemq-5.9.0/bin/activemq start

# Tomcat с установленным Auth2-Server
java7
/H/Java/apache-tomcat-8.0.15/bin/catalina.sh run

#   Phone4Pay BackServer
cd /H/Phone4Pay/projects/p4p-front-back.v3/BackServer
mvn spring-boot:run

#   Phone4Pay FrotServer
cd /H/Phone4Pay/projects/p4p-front-back.v3/FrontServer
mvn jetty:run

#   Phone4Pay test-java-client
cd /H/Phone4Pay/projects/test-java-client
gradle bootrun

