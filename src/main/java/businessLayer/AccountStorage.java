package businessLayer;

import java.io.Serializable;
import java.util.HashSet;

public class AccountStorage implements Serializable {
    private HashSet<Account> accounts;
    private int idSeed;

    public AccountStorage() {
        accounts = new HashSet<>();
        idSeed = 0;
    }

    public boolean register(Account a) {
        if(login(a.getUser(), a.getPass()) != -1) {
            return false;
        }
        if(a instanceof Client) {
            ((Client)a).setId(++idSeed);
        }

        return accounts.add(a);
    }

    public int login(String user, String pass) {
        for(Account crt: accounts) {
            if(crt.findMatchByCredentials(user, pass)) {
                if(crt instanceof Client) {
                    return ((Client)crt).getId();
                } else {
                    return 0;
                }
            }
        }
        return -1;
    }

    public HashSet<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(HashSet<Account> accounts) {
        this.accounts = accounts;
    }
}
