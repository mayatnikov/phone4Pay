package mvn.httpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpBean  implements CommandLineRunner {
    private Log log = LogFactory.getLog(HttpBean.class);

    @Value("${front.cgi-uri}")
    private String frontUrl;


    public void run (String ... args) {
        RestTemplate restTemplate = new RestTemplate();
        log.info("CGI-Script response len=" + restTemplate.getForObject(frontUrl, String.class).length());
    }
}
