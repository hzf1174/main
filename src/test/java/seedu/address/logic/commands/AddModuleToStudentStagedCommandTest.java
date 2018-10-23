package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModuleList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyModuleList;
import seedu.address.model.credential.Credential;
import seedu.address.model.credential.ReadOnlyCredentialStore;
import seedu.address.model.module.Code;
import seedu.address.model.module.Module;
import seedu.address.model.module.Prereq;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.person.Person;
import seedu.address.model.user.Admin;
import seedu.address.model.user.User;
import seedu.address.model.user.student.Student;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalModules;

public class AddModuleToStudentStagedCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddModuleToStudentStagedCommand(null);
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        AddModuleToStudentStagedCommandTest.ModelStubAcceptingModuleAdded modelStub =
                new AddModuleToStudentStagedCommandTest.ModelStubAcceptingModuleAdded();
        Module validModuleBeforeSearch = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        AddModuleToStudentStagedCommand addModuleToStudentStagedCommand =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));

        CommandResult commandResult = addModuleToStudentStagedCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = addModuleToStudentStagedCommand.getSearchedModule();
        UniqueModuleList expectModuleList = new UniqueModuleList();
        expectModuleList.add(validModuleAfterSearch);

        assertNotEquals(validModuleBeforeSearch, validModuleAfterSearch);
        assertEquals(createCommandResult("", "", " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(expectModuleList, modelStub.student.getModulesStaged());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateModule_cannotAdded() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        AddModuleToStudentStagedCommand addModuleToStudentStagedCommand =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));
        AddModuleToStudentStagedCommandTest.ModelStub modelStub =
                new AddModuleToStudentStagedCommandTest.ModelStubWithModule(validModuleBeforeSearch);

        CommandResult commandResult = addModuleToStudentStagedCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = addModuleToStudentStagedCommand.getSearchedModule();
        assertNotEquals(validModuleBeforeSearch, validModuleAfterSearch);
        assertEquals(createCommandResult("", " ACC1002X", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

    }

    @Test
    public void execute_nonexistentModule_cannotAdded() throws Exception {
        Module nonexistentModule = new Module(new Code("CS1010"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        AddModuleToStudentStagedCommand addModuleToStudentStagedCommand =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(nonexistentModule)));
        AddModuleToStudentStagedCommandTest.ModelStub modelStub =
                new AddModuleToStudentStagedCommandTest.ModelStubWithModule(nonexistentModule);

        CommandResult commandResult = addModuleToStudentStagedCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = addModuleToStudentStagedCommand.getSearchedModule();
        assertNull(validModuleAfterSearch);
        assertEquals(createCommandResult(" CS1010", "", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_nonStudentUser_throwsCommandException() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        AddModuleToStudentStagedCommand addModuleToStudentStagedCommand =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));
        AddModuleToStudentStagedCommandTest.ModelStub modelStub =
                new AddModuleToStudentStagedCommandTest.ModelStubWithNonStudentUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleToStudentStagedCommand.MESSAGE_NOT_STUDENT);
        addModuleToStudentStagedCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_notLogin_throwsCommandException() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        AddModuleToStudentStagedCommand addModuleToStudentStagedCommand =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));
        AddModuleToStudentStagedCommandTest.ModelStub modelStub =
                new AddModuleToStudentStagedCommandTest.ModelStubWithNotLogin();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleToStudentStagedCommand.MESSAGE_NOT_LOGIN);
        addModuleToStudentStagedCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_hybridModules_addedCorrectly() throws Exception {
        Module validModule = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        Module duplicateModule = new Module(new Code("ACC1002"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        Module notExistModule = new Module(new Code("CS1010"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        AddModuleToStudentStagedCommand addModuleToStudentStagedCommand =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(validModule,
                        duplicateModule, notExistModule)));
        AddModuleToStudentStagedCommandTest.ModelStub modelStub =
                new AddModuleToStudentStagedCommandTest.ModelStubForHybrid(duplicateModule);

        CommandResult commandResult = addModuleToStudentStagedCommand.execute(modelStub, commandHistory);
        assertEquals(createCommandResult(" CS1010", " ACC1002", " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Module cs1010 = new ModuleBuilder().withCode(new Code("CS1010")).build();
        Module acc1002x = new ModuleBuilder().withCode(new Code("ACC1002X")).build();
        AddModuleToStudentStagedCommand addCs1010Command =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(cs1010)));
        AddModuleToStudentStagedCommand addAcc1002XCommand =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(acc1002x)));

        // same object -> returns true
        assertTrue(addCs1010Command.equals(addCs1010Command));

        // same values -> returns true
        AddModuleToStudentStagedCommand addCs1010CommandCopy =
                new AddModuleToStudentStagedCommand(new ArrayList<>(Arrays.asList(cs1010)));
        assertTrue(addCs1010Command.equals(addCs1010CommandCopy));

        // different types -> returns false
        assertFalse(addCs1010Command.equals(1));

        // null -> returns false
        assertFalse(addCs1010Command.equals(null));

        // different person -> returns false
        assertFalse(addCs1010Command.equals(addAcc1002XCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModuleList getModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getObservableModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAdmin(Admin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleToDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleFromDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleInDatabase(Module module) {
            return false;
        }

        @Override
        public boolean hasCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isVerifiedCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAdmin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isStudent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCredentialStore getCredentialStore() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public User getCurrentUser() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void setCurrentUser(User user) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Optional<Module> searchModuleInModuleList(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveUserFile(User user, Path savePath) {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithNonStudentUser extends AddModuleToStudentStagedCommandTest.ModelStub {
        @Override
        public boolean isStudent() {
            return false;
        }

        @Override
        public User getCurrentUser() {
            return new StudentBuilder().build();
        }
    }

    private class ModelStubWithNotLogin extends AddModuleToStudentStagedCommandTest.ModelStub {
        @Override
        public User getCurrentUser() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends AddModuleToStudentStagedCommandTest.ModelStub {
        private final Module module;
        private final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModuleStaged(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }

        @Override
        public Optional<Module> searchModuleInModuleList(Module module) {
            return moduleList.getModuleInformation(module);
        }

        @Override
        public boolean isStudent() {
            return true;
        }

        @Override
        public User getCurrentUser() {
            return new StudentBuilder().build();
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends AddModuleToStudentStagedCommandTest.ModelStub {
        final Student student = new StudentBuilder().build();
        final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        @Override
        public boolean hasModuleStaged(Module module) {
            requireNonNull(module);
            return student.hasModulesStaged(module);
        }

        @Override
        public void addModuleStaged(Module module) {
            requireNonNull(module);
            student.addModulesStaged(module);
        }

        @Override
        public Optional<Module> searchModuleInModuleList(Module module) {
            return moduleList.getModuleInformation(module);
        }

        @Override
        public boolean isStudent() {
            return true;
        }

        @Override
        public User getCurrentUser() {
            return student;
        }

    }

    /**
     * A Model stub that for testing hybrid modules.
     */
    private class ModelStubForHybrid extends AddModuleToStudentStagedCommandTest.ModelStub {
        private final Module module;
        private final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        ModelStubForHybrid(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public void addModuleStaged(Module module) {
            requireNonNull(module);
            return;
        }

        @Override
        public boolean hasModuleStaged(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }

        @Override
        public Optional<Module> searchModuleInModuleList(Module module) {
            return moduleList.getModuleInformation(module);
        }

        @Override
        public boolean isStudent() {
            return true;
        }

        @Override
        public User getCurrentUser() {
            return new StudentBuilder().build();
        }
    }

    /**
     * Create a command result with three types of Code.
     */
    private String createCommandResult(String notExistCode, String duplicateCode, String addSuccessCode) {
        return AddModuleToStudentStagedCommand.MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE
                + notExistCode + '\n'
                + AddModuleToStudentStagedCommand.MESSAGE_DUPLICATE_MODULE
                + duplicateCode + '\n'
                + AddModuleToStudentStagedCommand.MESSAGE_SUCCESS
                + addSuccessCode;
    }

}
