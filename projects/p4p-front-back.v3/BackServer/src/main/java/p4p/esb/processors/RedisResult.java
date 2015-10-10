package p4p.esb.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import p4p.front.data.Echo;

@Component(value = "redisResult")
public class RedisResult {

    @Autowired
    RedisConnectionFactory connFactory;

    @Autowired
    private StringRedisTemplate redis;


    public void handle(Exchange exchange) {
        Message camelMessage = exchange.getIn();
        Echo message = camelMessage.getBody(Echo.class);
        String loginUser = message.getFrom();
        StringBuffer resp = new StringBuffer();

//        List<String> rl = redis.opsForList().range(loginUser,0,-1);
//        for (String ritem : rl ) {
//            resp.append(ritem);
//        }

        String ritem;
              while ((ritem=redis.opsForList().rightPop(loginUser)) != null )  resp.append(ritem);
        message.setEcho("");
        message.setTo("");
        message.setData(resp.toString());
    }


}
