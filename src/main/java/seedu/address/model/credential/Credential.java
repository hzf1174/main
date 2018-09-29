package seedu.address.model.credential;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

public class Credential {

    // Identity Field
    private final String username;

    // Data Field
    private final String password;
    private final String key; // TODO Awaiting Encryption function

    /**
     * Every field must be present and not null.
     */
    public Credential(String username, String password, String key) {
        requireAllNonNull(username, password, key);
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

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCredential(Credential otherCredential) {
        if (otherCredential == this) {
            return true;
        }

        return otherCredential != null
            && otherCredential.getUsername().equals(getUsername())
            && otherCredential.getPassword().equals(getPassword())
            && otherCredential.getKey().equals(getKey());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Credential)) {
            return false;
        }

        Credential otherCredential = (Credential) other;
        return otherCredential.getUsername().equals(getUsername())
            && otherCredential.getPassword().equals(getPassword())
            && otherCredential.getKey().equals(getKey());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password, key);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Username: ")
            .append(getUsername())
            .append(" Password: ")
            .append(getPassword())
            .append(" Key: ")
            .append(getKey());
        return builder.toString();
    }
}
