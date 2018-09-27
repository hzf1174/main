package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserCredentials;
import seedu.address.model.UserCredentials;

/**
 * Represents a storage for {@link UserCredentials}.
 */
public interface UserCredentialsStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getUserCredentialsFilePath();

    /**
     * Returns UserCredentials data as a {@link UserCredentials}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserCredentials> readUserAccounts() throws DataConversionException,
        IOException;

    /**
     * @see #getUserCredentialsFilePath()
     */
    Optional<ReadOnlyUserCredentials> readUserAccounts(Path filePath) throws DataConversionException
        , IOException;

    /**
     * Saves the given {@link UserCredentials} to the storage.
     * @param userCredentials cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserAccounts(UserCredentials userCredentials) throws IOException;

    /**
     * @see #saveUserAccounts(UserCredentials)
     */
    void saveUserAccounts(UserCredentials userCredentials, Path filePath) throws IOException;
}
