package seedu.address.model.credential;

import static java.util.Objects.requireNonNull;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wraps all CredentialStore data
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class CredentialStore implements ReadOnlyCredentialStore {

    private final HashMap<String, String> userCredentials = new HashMap<>();
    private final HashMap<String, String> keyMap = new HashMap<>();

    public CredentialStore() {}

    public boolean hasCredential(Credential credential) {
        requireNonNull(credential);
        return userCredentials.containsKey(credential.getUsername());
    }

    public void addCredential(Credential credential) {
        userCredentials.put(credential.getUsername(),
            credential.getPassword());
        keyMap.put(credential.getUsername(), credential.getKey());
    }

    @Override
    public List<Credential> getCredentials() {
        List<Credential> credentials = new ArrayList<>();
        for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
            Credential account = new Credential(entry.getKey(),
                entry.getValue(), keyMap.get(entry.getKey()));
            credentials.add(account);
        }
        return credentials;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CredentialStore)) {
            return false;
        }

        // state check
        CredentialStore other = (CredentialStore) obj;
        return userCredentials.equals(other.userCredentials)
            && keyMap.equals(other.keyMap);
    }
}
