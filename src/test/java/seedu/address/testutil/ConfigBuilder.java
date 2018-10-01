package seedu.address.testutil;

import seedu.address.model.Config;

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
     * Sets the {@code data} of the {@code Config} that we are building.
     */
    public ConfigBuilder withData(byte[] data) {
        this.data = data;
        return this;
    }

    public Config build() {
        return new Config(data);
    }

}
