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
public class EchoSimpleTest implements CommandLineRunner  {

    @Value("${front.echo-uri}")
    String uri;

    private Log log = LogFactory.getLog(EchoSimpleTest.class);

    RestTemplate rest = new RestTemplate();

    @Autowired
    Token token;


    public void run(String... args) {
        log.info("Start simple echo with JSON-Request URI="+uri+"/11");
        HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token.getAccess_token());
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("Accept", "application/json");
            headers.add("Accept-Charset", "UTF-8");              // не работает !

        String answer="?";
        HttpEntity<String> request = new HttpEntity<String>(headers);

         try {
            for(int tik=0;tik<10;tik++) {
                ResponseEntity resp = rest.exchange(uri + "/" + "11", HttpMethod.GET, request, String.class);
                answer = (String) (resp.getBody());
                log.info("echo11=" + answer);
            }
         }
         catch (HttpClientErrorException e)
         {
             log.error(e.getMessage());
         }
        log.info("End of echo test");
    }
}
