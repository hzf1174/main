package systemtests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Ignore;
import org.junit.Test;

import seedu.modsuni.model.AddressBook;
import seedu.modsuni.model.person.Person;
import seedu.modsuni.model.util.SampleDataUtil;
import seedu.modsuni.testutil.TestUtil;

public class SampleDataTest extends ModsUniSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected AddressBook getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    @Ignore
    public void addressBook_dataFileDoesNotExist_loadSampleData() {
        Person[] expectedList = SampleDataUtil.getSamplePersons();
        //assertListMatching(getPersonListPanel(), expectedList);
    }
}
