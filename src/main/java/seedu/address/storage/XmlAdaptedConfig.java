package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Config;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedConfig {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Config %s field is missing!";

    //  @XmlElement(required = true)
    //  private String name;
    @XmlElement(required = true)
    private byte[] data;


    /**
    * Constructs an XmlAdaptedPerson.
    * This is the no-arg constructor that is required by JAXB.
    */
    public XmlAdaptedConfig() {}

    /**
    * Constructs an {@code XmlAdaptedPerson} with the given person details.
    */
    public XmlAdaptedConfig(byte[] data) {
        this.data = data;
    }

    /**
    * Converts a given Person into this class for JAXB use.
    *
    * @param source future changes to this will not affect the created XmlAdaptedPerson
    */
    public XmlAdaptedConfig(Config source) {
        data = source.getConfigData();
    }

    /**
    * Converts this jaxb-friendly adapted person object into the model's Person object.
    *
    * @throws IllegalValueException if there were any data constraints violated in the adapted person
    */
    public Config toModelType() throws IllegalValueException {
        if (data == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Username"));
        }

        return new Config(data);
    }

    //  @Override
    //  public boolean equals(Object other) {
    //    if (other == this) {
    //      return true;
    //    }
    //
    //    if (!(other instanceof XmlAdaptedPerson)) {
    //      return false;
    //    }
    //
    //    XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
    //    return Objects.equals(name, otherPerson.name)
    //            && Objects.equals(phone, otherPerson.phone)
    //            && Objects.equals(email, otherPerson.email)
    //            && Objects.equals(address, otherPerson.address)
    //            && tagged.equals(otherPerson.tagged);
    //  }
}
