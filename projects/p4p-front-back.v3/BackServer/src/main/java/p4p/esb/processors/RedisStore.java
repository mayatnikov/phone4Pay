package p4p.esb.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import p4p.front.data.Echo;
/**
 сохранить в REDIS ответ пользователю
 он его потом заберет
 */


@Component(value = "redisStore")
public class RedisStore {

    @Autowired
    RedisConnectionFactory connFactory;

    @Autowired
    private StringRedisTemplate redis;


    public void handle(Exchange exchange) {
        Message camelMessage = exchange.getIn();


        Echo message = camelMessage.getBody(Echo.class);

       redis.opsForList().leftPush(message.getFrom(),
                         " To:"+ message.getTo()
                       + " Echo:" + message.getEcho()
                       + " Data:" + message.getData() );

        message.setData("REF=899676434");
        message.setEcho("");
        message.setTrID(122999L);
    }
}
