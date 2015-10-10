package p4p.front.data;

import java.io.Serializable;

//@TODO Вынести все данные в отдельный проект
//@TODO Сделать общий интерфейс с типовыми методами

/**
 * A simple DTO class to encapsulate messages along with their destinations and timestamps.
 */
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