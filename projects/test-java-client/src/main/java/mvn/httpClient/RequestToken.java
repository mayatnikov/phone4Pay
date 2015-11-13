package mvn.httpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class RequestToken {

    private Log log = LogFactory.getLog(RequestToken.class);

    RestTemplate rest = new RestTemplate();

    @Value("${auth.uri}")
    private String authUri;

    @Value("${auth.user}")
    private String authUser;

    @Value("${auth.password}")
    private String authPassword;

    @Value("${auth.param}")
    private String authParam;

@Bean
    public Token getToken() {
        Token token;

        String creds = authUser+":"+authPassword;
        byte[] credsBytes = creds.getBytes();
        byte[] credsB64 = Base64.getEncoder().encode(credsBytes);

        String credsStr = new String(credsB64);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + credsStr);
        headers.add( "Content-Type", "application/x-www-form-urlencoded");
        headers.add("Accept","application/json");
        headers.add("Accept-Charset", "UTF-8");              // не работает !

        HttpEntity<String> request = new HttpEntity<String>(authParam,headers);
        log.info("Request token from server...");
         try {
             ResponseEntity resp = rest.exchange(authUri, HttpMethod.POST, request, Token.class);
             token = (Token) (resp.getBody());
         }
         catch (HttpClientErrorException e)
         {
             log.error(e.getMessage());
             token= new Token();
         }
        log.debug("Token="+token.access_token);
        log.info("End of request token from server...");
        return token;
    }
}
