package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Config;
import seedu.address.model.ConfigStore;

/**
 * An Immutable ConfigStore that is serializable to XML format
 */
@XmlRootElement(name = "configstore")
public class XmlSerializableConfigStore {

    @XmlElement
    private List<XmlAdaptedConfig> configDatas;

    /**
    * Creates an empty XmlSerializableConfigStore.
    * This empty constructor is required for marshalling.
    */
    public XmlSerializableConfigStore() {
        configDatas = new ArrayList<>();
    }

    /**
    * Conversion
    */
    public XmlSerializableConfigStore(ConfigStore src) {
        this();
        XmlAdaptedConfig adaptedConfig = new XmlAdaptedConfig(src.getConfigData());
        configDatas.add(adaptedConfig);
    }

    /**
    * Converts this configstore into the model's {@code ConfigStore} object.
    *l
    * @throws IllegalValueException if there were any data constraints violated or duplicates in the
    * {@code XmlAdaptedConfig}.
    */
    public ConfigStore toModelType() throws IllegalValueException {
        ConfigStore configStore = new ConfigStore();
        for (XmlAdaptedConfig adaptedConfig : configDatas) {
            Config config = adaptedConfig.toModelType();
            configStore.addConfigData(config);
        }
        return configStore;
    }
}
