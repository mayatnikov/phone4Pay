package p4p.esb.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import p4p.front.data.Echo;

/*
формирование в exchange.message ответ из всех сообщений для пользователя из redis
заберем все из redis
там должны лежать JSON строки serialized POJO классов
 */

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
        resp.append("{\"RESP\":{");

        String ritem;
              while ((ritem=redis.opsForList().rightPop(loginUser)) != null )  resp.append(ritem);
        resp.append("}}");
        message.setRequest("");
        message.setTo("");
        message.setResponse(resp.toString());
    }


}
