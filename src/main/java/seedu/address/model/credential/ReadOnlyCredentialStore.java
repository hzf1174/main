package seedu.address.model.credential;

import java.util.List;

/**
<<<<<<< HEAD
 * Unmodifiable view of a credential store
=======
 *
>>>>>>> 124020da086a70cd48dacd11050d497e5dc039f1
 */
public interface ReadOnlyCredentialStore {
    /**
     * Returns an list of credentials.
     * This list will not contain any duplicate credentials.
     */
    List<Credential> getCredentials();
}
