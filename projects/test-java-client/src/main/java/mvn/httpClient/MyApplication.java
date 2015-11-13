package mvn.httpClient;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class MyApplication  implements CommandLineRunner {

    private Log log = LogFactory.getLog(MyApplication.class);

    @Autowired
    MyBean myBean;

    @Override
    public void run(String... args) {
        log.debug("DEBUG LEVEL OUTPUT>"+myBean);
        log.info( "INFO  LEVEL OUTPUT>"+myBean);
    }
}
