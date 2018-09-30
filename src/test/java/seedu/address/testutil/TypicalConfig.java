package seedu.address.testutil;

import seedu.address.model.Config;
import seedu.address.model.ConfigStore;

/**
 * A utility class containing a {@code Config} object to be used in tests.
 */
public class TypicalConfig {

    public static final Config data = new ConfigBuilder().withData("data1".getBytes()).build();

    private TypicalConfig() {} // prevents instantiation

    /**
     * Returns an {@code ConfigStore} with all the typical Config.
     */
    public static ConfigStore getTypicalConfigStore() {
        return getTypicalConfigStore();
    }

    public static Config getTypicalConfig() {
        return new Config(data.getConfigData());
    }
}
