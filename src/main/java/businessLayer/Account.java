package businessLayer;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {
    private String user;
    private String pass;

    public Account(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
    @Override
    public boolean equals(Object o) {
        return (this == o ||
                (this.user.equals(((Account)o).user) && this.pass.equals(((Account)o).pass)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "user=" + user;
    }

    public boolean findMatchByCredentials(String user, String pass) {
        return (this.user.equals(user) && this.pass.equals(pass));
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
