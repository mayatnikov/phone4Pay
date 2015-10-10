package p4p.esb.routes;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
public class MyRoutes {

    @Value("${aq.host}")
    private String aqHost;

    @Value("${aq.port}")
    private String aqPort;

    @Bean
    ConnectionFactory jmsConnectionFactory() {
        // use a pool for ActiveMQ connections
        String aqURI="tcp://"+aqHost+":"+aqPort;
        PooledConnectionFactory pool = new PooledConnectionFactory();
        pool.setConnectionFactory(new ActiveMQConnectionFactory(aqURI));
        return pool;
    }

    @Bean
    RouteBuilder myRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("activemq:p4p-echo-in").to("log:ECHO-IN-REQUEST?level=INFO").beanRef("echoConverter");
                from("activemq:p4p-echo-redis").to("log:ECHO-REDIS-REQUEST?level=INFO").beanRef("redisStore");
                from("activemq:p4p-echo-result").to("log:ECHO-REDIS-RESULT?level=INFO").beanRef("redisResult");
            }
        };
    }
}


/*

        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("activemq:p4p-echo-in").to("log:ECHO-IN-REQUEST?level=INFO").beanRef("echoConverter");
                from("activemq:p4p-echo-redis").to("log:ECHO-REDIS-REQUEST?level=INFO").beanRef("redisStore");
            }
        };

 */