package p4p.front.data;

import java.io.Serializable;

public class Echo extends Message implements Serializable {

    private static final long serialVersionUID = 17866556L;

    private String echo;
    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public String toString() {
        return "To:["+to+"]From:" +from + "]Echo:["+echo+"]Data:["+data+"]";
    }
}