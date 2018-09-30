package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.Config;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Config objects.
 */
public class ConfigBuilder {

    public static final String DEFAULT_DATA = "Some data";

    private byte[] data;

    public ConfigBuilder() {
        this.data = DEFAULT_DATA.getBytes();
    }

    /**
     * Initializes the ConfigBuilder with the data of {@code configToStore}.
     */
    public ConfigBuilder(Config configToStore) {
        this.data = configToStore.getConfigData();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ConfigBuilder withData(byte[] data) {
        this.data = data;
        return this;
    }

    public Config build() {
        return new Config(data);
    }

}
