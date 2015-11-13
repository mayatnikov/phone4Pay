package p4p.front.data;

import java.io.Serializable;

//@TODO Вынести все данные в отдельный проект
//@TODO Сделать общий интерфейс с типовыми методами

/**
 * A simple DTO class to encapsulate messages along with their destinations and timestamps.
 */
public class Echo extends Message implements Serializable {

    private static final long serialVersionUID = 17866556L;

    private String request;
    private String response;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String toString() {
        return "Login:["+login+"]Request:["+request+"]Response:["+response+"]startTransaction:["+startTransaction;
    }
}