package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserAccount;

/**
 * JAXB-friendly version of the UserAccount.
 */
public class XmlAdaptedUserAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "UserAccount's " +
        "%s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUserAccount() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedUserAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedUserAccount
     */
    public XmlAdaptedUserAccount(UserAccount source) {
        username = source.getUsername();
        password = source.getPassword();
    }


    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public UserAccount toModelType() throws IllegalValueException {

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Username"));
        }

        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Password"));
        }

        return new UserAccount(username, password);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUserAccount)) {
            return false;
        }

        XmlAdaptedUserAccount otherAccount = (XmlAdaptedUserAccount) other;
        return Objects.equals(username, otherAccount.username)
            && Objects.equals(password, otherAccount.password);
    }
}
