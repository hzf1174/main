package seedu.address.model;

import java.util.List;

public interface ReadOnlyUserCredentials {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    List<UserAccount> getUserAccounts();
}
