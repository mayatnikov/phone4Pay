package mvn.httpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class EchoSimpleTestAsync implements CommandLineRunner  {

    private String action="redis";
    private String body="{\"login\":\"?\",\"from\":\"?\",\"request\":\"REQUEST-BODY\",\"startTransaction\":\"201511121448\"}";

    @Value("${front.echo-uri}")
    String uri;

    private Log log = LogFactory.getLog(EchoSimpleTestAsync.class);

    RestTemplate rest = new RestTemplate();

    @Autowired
    Token token;

    public void run(String... args) {
        log.info("Start Async request with JSON-Request URI="+uri+"/"+action);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token.getAccess_token());
            headers.add("Content-Type", "application/json");
            headers.add("Accept", "application/json");
            headers.add("Accept-Charset", "UTF-8");              // не работает !
            headers.add("Charset", "UTF-8");

        String answer="?";
        HttpEntity<String> request = new HttpEntity<String>(body,headers);

         try {
            for(int tik=0;tik<10;tik++) {
                ResponseEntity resp = rest.exchange(uri + "/" + action, HttpMethod.POST, request, String.class);
                answer = (String) (resp.getBody());
                log.info("Async request-response="+answer);
            }
         }
         catch (HttpClientErrorException e)
         {
             log.error(e.getMessage());
         }
        }
}
