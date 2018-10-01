package seedu.address.storage;
import java.nio.file.Path;
/**
 * Represents a storage for {@link seedu.address.model.Module}.
 */
public interface ModuleStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();
}
