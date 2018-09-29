package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyModuleList;

/**
 * Represents a storage for {@link seedu.address.model.ModuleList}.
 */
public interface ModuleListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getModuleFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyModuleList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyModuleList> readModuleList() throws DataConversionException, IOException;

    /**
     * @see #getModuleFilePath()
     */
    Optional<ReadOnlyModuleList> readModuleList(Path filePath) throws DataConversionException, IOException;

}
