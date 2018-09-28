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
import seedu.address.model.credential.CredentialStore;
import seedu.address.model.credential.ReadOnlyCredentialStore;


public class XmlCredentialStoreStorage implements CredentialStoreStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlCredentialStoreStorage.class);

    private Path filePath;

    public XmlCredentialStoreStorage(Path filePath){
        this.filePath = filePath;
    }

    @Override
    public Path getCredentialStoreFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCredentialStore> readCredentialStore() throws DataConversionException,
        IOException {
        return readCredentialStore(filePath);
    }

    public Optional<ReadOnlyCredentialStore> readCredentialStore(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("CredentialStore file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableCredentialStore xmlUserCredentials =
            XmlFileStorage.loadCredentialStoreDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlUserCredentials.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCredentialStore(ReadOnlyCredentialStore credentialStore) throws IOException {
        saveCredentialStore(credentialStore, filePath);
    }

    @Override
    public void saveCredentialStore(ReadOnlyCredentialStore credentialStore,
                               Path filePath) throws IOException {
        requireNonNull(credentialStore);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath,
            new XmlSerializableCredentialStore(credentialStore));
    }
}
