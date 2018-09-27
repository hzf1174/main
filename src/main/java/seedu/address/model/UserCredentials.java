package seedu.address.model;

import static java.util.Objects.requireNonNull;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wraps all UserCredentials data
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class UserCredentials implements ReadOnlyUserCredentials{

    private final HashMap<String, String> userCredentials = new HashMap<>();

    public boolean hasAccount(UserAccount userAccount) {
        requireNonNull(userAccount);
        return userCredentials.containsKey(userAccount.getUsername());
    }

    public void addAccount(UserAccount userAccount) {
        userCredentials.put(userAccount.getUsername(),
            userAccount.getPassword());
    }

    @Override
    public List<UserAccount> getUserAccounts() {
        List<UserAccount> userAccounts = new ArrayList<>();
        for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
            UserAccount account = new UserAccount(entry.getKey(),
                entry.getValue());
            userAccounts.add(account);
        }
        return userAccounts;
    }
}
