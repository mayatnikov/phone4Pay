package p4p.esb.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import p4p.front.data.Echo;

import java.io.IOException;

/**
 сохранить в REDIS ответ пользователю
 он его потом заберет
 */


@Component(value = "redisStore")
public class RedisStore {

    @Autowired
    RedisConnectionFactory connFactory;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StringRedisTemplate redis;


    public void handle(Exchange exchange) throws IOException {
        Message camelMessage = exchange.getIn();


        Echo message = camelMessage.getBody(Echo.class);

       redis.opsForList().leftPush(message.getLogin(), mapper.writeValueAsString(message));

//               message.getFrom(),
//                         " To:"+ message.getTo()
//                       + " Echo:" + message.getRequest()
//                       + " Data:" + message.getResponse() );

        message.setResponse("REF=899676434");
        message.setRequest("GetAll");
        message.setTrID(122999L);
    }
}
