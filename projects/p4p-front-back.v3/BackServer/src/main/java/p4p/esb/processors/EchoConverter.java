package p4p.esb.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;
import p4p.front.data.Echo;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 20/09/15
 * Time: 16:27
 */


@Component(value = "echoConverter")
public class EchoConverter {


    public void handle(Exchange exchange) {
        Message camelMessage = exchange.getIn();
        StringBuffer sb = new StringBuffer();

        Map<String, Object> prop =  exchange.getProperties();
        for(String kk: prop.keySet()) {
           sb.append(" prop:"+kk+"="+prop.get(kk)+";");
        }

        Map<String, Object> hdrs =  exchange.getIn().getHeaders();
        for(String kk: hdrs.keySet()) {
            sb.append(" hdr:"+kk+"="+prop.get(kk)+";");
        }

        Echo message = camelMessage.getBody(Echo.class);
        message.setData("From:" + message.getFrom()
                + " To:"+ message.getTo()
                + " Echo:" + message.getEcho()
                + " Data:" + message.getData()
                + " HDR:" + sb.toString() );
    }
}
