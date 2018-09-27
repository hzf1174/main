package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyUserCredentials;
import seedu.address.model.UserCredentials;


public class XmlUserCredentialsStorage implements UserCredentialsStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserCredentialsStorage.class);

    private Path filePath;

    public XmlUserCredentialsStorage(Path filePath){
        this.filePath = filePath;
    }

    @Override
    public Path getUserCredentialsFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyUserCredentials> readUserAccounts() throws DataConversionException, IOException {
        return readUserAccounts(filePath);
    }

    public Optional<ReadOnlyUserCredentials> readUserAccounts(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("UserCredentials file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableUserCredentials xmlUserCredentials =
            XmlFileStorage.loadUserCredentialsDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlUserCredentials.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserAccounts(UserCredentials userCredentials) throws IOException {
        saveUserAccounts(userCredentials, filePath);
    }

    @Override
    public void saveUserAccounts(UserCredentials userCredentials, Path filePath) throws IOException {
        requireNonNull(userCredentials);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath,
            new XmlSerializableUserCredentials(userCredentials));
    }
}
