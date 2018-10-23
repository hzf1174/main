package seedu.modsuni.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.commons.util.FileUtil;
import seedu.modsuni.model.user.User;

/**
 * A class to access User data stored as an xml file on the hard disk.
 */
public class XmlUserStorage implements UserStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserStorage.class);

    private Path filePath;

    public XmlUserStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserSavedFilePath() {
        return filePath;
    }

    @Override
    public Optional<User> readUser() throws DataConversionException, IOException {
        return readUser(filePath);
    }

    /**
     * Similar to {@link #readUser()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<User> readUser(Path filePath)
            throws DataConversionException, FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("User saved file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableUser xmlUser = XmlFileStorage.loadUserDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlUser.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUser(User user) throws IOException {
        saveUser(user, filePath);
    }

    /**
     * Similar to {@link #saveUser(User)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveUser(User user, Path filePath) throws IOException {
        requireNonNull(user);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableUser(user));
    }

}
