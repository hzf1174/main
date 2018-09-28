package seedu.address.model.credential;

public class Credential {

    // Identity Field
    private final String username;

    // Data Field
    private final String password;
    private final String key; // TODO Awaiting Encryption function

    public Credential(String username, String password, String key) {
        this.username = username;
        this.password = password;
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getKey() { return key; }

}
