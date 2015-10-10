package p4p.front.data;

import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable {
    String from;
    String to;
    Date startTransaction;
    Long trID;
    int trStatus;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getStartTransaction() {
        return startTransaction;
    }

    public void setStartTransaction(Date startTransaction) {
        this.startTransaction = startTransaction;
    }

    public Long getTrID() {
        return trID;
    }

    public void setTrID(Long trID) {
        this.trID = trID;
    }

    public int getTrStatus() {
        return trStatus;
    }

    public void setTrStatus(int trStatus) {
        this.trStatus = trStatus;
    }
}
