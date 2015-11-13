package p4p.esb.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;
import p4p.front.data.Echo;
import p4p.front.data.TrType;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 20/09/15
 * Time: 16:27
 */


@Component(value = "echoResponse")
public class EchoResponse {

    Random rnd = new Random();

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

        message.setEndTransaction(new Date());
        message.setTrID( rnd.nextLong() );
        message.setTrType(TrType.SYNC);

        message.setResponse("MSG:{Login:[" + message.getLogin()
                + "],Request:[" + message.getRequest()
                + "],StartTransaction:[" + message.getStartTransaction()
                + "],EndTransaction:[" + message.getEndTransaction()
                + "],TrID:[" + message.getTrID()
                + "],TrType:[" + message.getTrType()
                + "],HDR:[" + sb.toString()+"]"
        );
    }
}
