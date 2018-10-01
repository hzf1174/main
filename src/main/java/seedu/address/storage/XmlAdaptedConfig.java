package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Config;

/**
 * JAXB-friendly version of the Config.
 */
public class XmlAdaptedConfig {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Config %s field is missing!";

    @XmlElement(required = true)
    private byte[] data;


    /**
    * Constructs an XmlAdaptedConfig.
    * This is the no-arg constructor that is required by JAXB.
    */
    public XmlAdaptedConfig() {}

    /**
    * Constructs an {@code XmlAdaptedConfig} with the given data.
    */
    public XmlAdaptedConfig(byte[] data) {
        this.data = data;
    }

    /**
    * Converts a given Config into this class for JAXB use.
    *
    * @param source future changes to this will not affect the created XmlAdaptedConfig
    */
    public XmlAdaptedConfig(Config source) {
        data = source.getConfigData();
    }

    /**
    * Converts this jaxb-friendly adapted config object into the model's Config object.
    *
    * @throws IllegalValueException if there were any data constraints violated in the adapted config
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
