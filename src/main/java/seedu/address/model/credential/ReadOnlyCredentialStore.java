package seedu.address.model.credential;

import java.util.List;

public interface ReadOnlyCredentialStore {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    List<Credential> getCredentials();
}
