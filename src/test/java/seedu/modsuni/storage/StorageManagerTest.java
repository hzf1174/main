package seedu.modsuni.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.modsuni.commons.events.model.AddressBookChangedEvent;
import seedu.modsuni.commons.events.model.ModuleListChangedEvent;
import seedu.modsuni.commons.events.storage.DataSavingExceptionEvent;
import seedu.modsuni.model.AddressBook;
import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.ReadOnlyAddressBook;
import seedu.modsuni.model.ReadOnlyModuleList;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlModuleListStorage moduleListStorage = new XmlModuleListStorage(getTempFilePath("modulelist"));
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        XmlCredentialStoreStorage credentialStoreStorage =
            new XmlCredentialStoreStorage(getTempFilePath("cd"));
        XmlUserStorage configStoreStorage = new XmlUserStorage(getTempFilePath("c"));
        storageManager = new StorageManager(
            moduleListStorage,
            addressBookStorage,
            userPrefsStorage,
            credentialStoreStorage,
            configStoreStorage);

    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(
                new XmlModuleListStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlCredentialStoreStorage(Paths.get("dummy")),
                new XmlUserStorage(Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void moduleListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlModuleListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlModuleListStorageTest}
         * class.
         */
        ModuleList original = getTypicalModuleList();
        storageManager.saveModuleList(original);
        ReadOnlyModuleList retrieved = storageManager.readModuleList().get();
        assertEquals(original, new ModuleList(retrieved));
    }

    @Test
    public void getModuleListFilePath() {
        assertNotNull(storageManager.getModuleFilePath());
    }

    @Test
    public void handleModuleListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(
                new XmlModuleListStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")),
                new XmlCredentialStoreStorage(Paths.get("dummy")),
                new XmlUserStorage(Paths.get("dummy")));
        storage.handleModuleListChangedEvent(new ModuleListChangedEvent(new ModuleList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class for ModuleList to throw an exception when the save method is called
     */
    class XmlModuleListStorageExceptionThrowingStub extends XmlModuleListStorage {

        public XmlModuleListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveModuleList(ReadOnlyModuleList moduleList, Path filePath) throws
                IOException {
            throw new IOException("dummy exception");
        }
    }


}
