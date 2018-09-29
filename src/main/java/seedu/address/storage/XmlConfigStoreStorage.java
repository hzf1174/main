package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ConfigStore;

/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlConfigStoreStorage implements ConfigStoreStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlConfigStoreStorage.class);

    private Path filePath;

    public XmlConfigStoreStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getConfigStoreStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ConfigStore> readConfigStore() throws DataConversionException, IOException {
        return readConfigStore(filePath);
    }

    /**
    * Similar to {@link #readConfigStore()}
    * @param filePath location of the data. Cannot be null
    * @throws DataConversionException if the file is not in the correct format.
    */
    public Optional<ConfigStore> readConfigStore(Path filePath) throws DataConversionException,
          FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Config file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableConfigStore xmlConfigStore = XmlFileStorage.loadConfigStoreDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlConfigStore.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveConfigStore(ConfigStore configStore) throws IOException {
        saveConfigStore(configStore, filePath);
    }

    /**
    * Similar to {@link #saveConfigStore(ConfigStore)}
    * @param filePath location of the data. Cannot be null
    */
    public void saveConfigStore(ConfigStore configStore, Path filePath) throws IOException {
        requireNonNull(configStore);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableConfigStore(configStore));
    }
}
