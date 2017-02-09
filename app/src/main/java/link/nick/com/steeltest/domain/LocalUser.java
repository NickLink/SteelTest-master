package link.nick.com.steeltest.domain;

/**
 * Created by Nick on 09.02.2017.
 */

public class LocalUser {
    private String user;
    private String pass;

    public LocalUser(){
    }

    public LocalUser(String user, String pass){
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
