package seedu.address.model;

public class UserAccount {

    // Identity Field
    private final String username;

    // Data Field
    private final String password;

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
