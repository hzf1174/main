package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserCredentials;
import seedu.address.model.UserAccount;
import seedu.address.model.UserCredentials;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "usercredentials")
public class XmlSerializableUserCredentials {

    public static final String MESSAGE_DUPLICATE_PERSON = "UserCredentials list " +
        "contains duplicate user(s).";

    @XmlElement
    private List<XmlAdaptedUserAccount> accounts;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUserCredentials() {
        accounts = new ArrayList<>();
    }

    /**
     * Conversion
     * @param src
     */
    public XmlSerializableUserCredentials(ReadOnlyUserCredentials src) {
        this();
        accounts.addAll(src.getUserAccounts().stream().map(XmlAdaptedUserAccount::new).collect(Collectors.toList()));
    }

    /**
     * Converts this usercredentials into the model's {@code UserCredentials}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedUserCredentials}.
     */
    public UserCredentials toModelType() throws IllegalValueException {
        UserCredentials userCredentials = new UserCredentials();
        for (XmlAdaptedUserAccount a : accounts) {
            UserAccount userAccount = a.toModelType();
            if (userCredentials.hasAccount(userAccount)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            userCredentials.addAccount(userAccount);
        }
        return userCredentials;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableUserCredentials)) {
            return false;
        }
        return accounts.equals(((XmlSerializableUserCredentials) other).accounts);
    }
}
