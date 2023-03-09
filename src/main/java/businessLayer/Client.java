package businessLayer;

public class Client extends Account {
    private int id;

    public Client(String user, String pass) {
        super(user, pass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Client client = (Client) o;
        return id == client.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
