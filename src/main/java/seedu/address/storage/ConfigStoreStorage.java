package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ConfigStore;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.ConfigStore}.
 */
public interface ConfigStoreStorage {

    /**
    * Returns the file path of the data file.
    */
    Path getConfigStoreStorageFilePath();

    /**
    * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
    *   Returns {@code Optional.empty()} if storage file is not found.
    * @throws DataConversionException if the data in storage is not in the expected format.
    * @throws IOException if there was any problem when reading from the storage.
    */
    Optional<ConfigStore> readConfigStore() throws DataConversionException, IOException;

    /**
    * @see #getConfigStoreStorageFilePath()
    */
    Optional<ConfigStore> readConfigStore(Path filePath) throws DataConversionException, IOException;

    /**
    * Saves the given {@link ReadOnlyAddressBook} to the storage.
    * @param addressBook cannot be null.
    * @throws IOException if there was any problem writing to the file.
    */
    void saveConfigStore(ConfigStore addressBook) throws IOException;

    /**
    * @see #saveConfigStore(ConfigStore)
    */
    void saveConfigStore(ConfigStore addressBook, Path filePath) throws IOException;

}
