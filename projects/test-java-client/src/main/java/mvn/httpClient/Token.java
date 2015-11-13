package mvn.httpClient;

/**
 * Created with IntelliJ IDEA.
 * User: vitaly
 * Date: 12/11/15
 * Time: 15:42
 */


public class Token {
    String access_token;
    String token_type;
    String refresh_token;
    String expires_in;
    String scope;
    String error;
    String error_desription;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_desription() {
        return error_desription;
    }

    public void setError_desription(String error_desription) {
        this.error_desription = error_desription;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String toString() {
        return "token="+access_token + " error="+error;
    }
}
