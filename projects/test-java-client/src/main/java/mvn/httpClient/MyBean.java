package mvn.httpClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @Value("${app.par1}")
    private String par1;

    @Value("${app.par2}")
    private String par2;

    public String toString() {
        return "par1="+par1+" par2="+ par2;
    }

    public String getPar1() {
        return par1;
    }

    public void setPar1(String par1) {
        this.par1 = par1;
    }

    public String getPar2() {
        return par2;
    }

    public void setPar2(String par2) {
        this.par2 = par2;
    }
}
