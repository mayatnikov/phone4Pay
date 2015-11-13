package p4p.front.data;

import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable {
    String login;
    Date startTransaction;
    Date endTransaction;
    Long trID;
    int trStatus;
    TrType trType; // SYNC ASYNC PUSH

    public Date getEndTransaction() {  return endTransaction;   }

    public void setEndTransaction(Date endTransaction) { this.endTransaction = endTransaction; }

    public String getLogin() {   return login;   }

    public void setLogin(String login) {  this.login = login;  }

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

    public TrType getTrType() {
        return trType;
    }

    public void setTrType(TrType trType) {
        this.trType = trType;
    }
}
