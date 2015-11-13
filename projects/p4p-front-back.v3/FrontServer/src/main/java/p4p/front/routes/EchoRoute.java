package p4p.front.routes;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

@Component
    public class EchoRoute extends SpringRouteBuilder {

        @Bean
        ConnectionFactory jmsConnectionFactory() {
            // use a pool for ActiveMQ connections
            PooledConnectionFactory pool = new PooledConnectionFactory();
            pool.setConnectionFactory(new ActiveMQConnectionFactory("tcp://localhost:61616"));
            return pool;
        }

        @Override
        public void configure() throws Exception {

            from("activemq:p4p-echo-in").to("log:ECHO-IN-REQUEST?level=INFO");
            from("direct:in").to("activemq:p4p-echo-in");
                                   // .to("bean:echoHandler");

        }
    }
