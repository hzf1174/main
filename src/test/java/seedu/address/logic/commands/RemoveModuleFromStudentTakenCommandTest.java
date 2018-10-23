package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalModules.ACC1002;
import static seedu.address.testutil.TypicalModules.ACC1002X;
import static seedu.address.testutil.TypicalModules.CS1010;

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
import seedu.address.model.person.Person;
import seedu.address.model.user.Admin;
import seedu.address.model.user.User;
import seedu.address.model.user.student.Student;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.StudentBuilder;
import seedu.address.testutil.TypicalModules;


public class RemoveModuleFromStudentTakenCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveModuleFromStudentTakenCommand(null);
    }

    @Test
    public void execute_moduleAcceptedByModel_removeSuccessful() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));
        RemoveModuleFromStudentTakenCommandTest.ModelStubForTest modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest(ACC1002);

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();

        assertNotEquals(validModuleBeforeSearch, validModuleAfterSearch);
        assertEquals(createCommandResult("", "", " ACC1002"),
                commandResult.feedbackToUser);
        assertFalse(modelStub.student.hasModulesTaken(validModuleAfterSearch));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_moduleNotFound_throwsCommandException() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest();

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();
        assertNotEquals(validModuleBeforeSearch, validModuleAfterSearch);
        assertEquals(createCommandResult("", " ACC1002", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }


    @Test
    public void execute_nonexistentModule_throwsCommandException() throws Exception {
        Module nonexistentModule = CS1010;
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(nonexistentModule)));
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest(nonexistentModule);

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();
        assertNull(validModuleAfterSearch);
        assertEquals(createCommandResult(" CS1010", "", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_nonStudentUser_throwsCommandException() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubWithNonStudentUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveModuleFromStudentTakenCommand.MESSAGE_NOT_STUDENT);
        removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_notLogin_throwsCommandException() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validModuleBeforeSearch)));
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubWithNotLogin();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveModuleFromStudentTakenCommand.MESSAGE_NOT_LOGIN);
        removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_hybridModules_removedCorrectly() throws Exception {
        Module validModule = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        Module duplicateModule = new Module(new Code("ACC1002"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        Module notExistModule = new Module(new Code("CS1010"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(
                        Arrays.asList(validModule, duplicateModule, notExistModule)));
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest(ACC1002X);

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();
        assertFalse(((ModelStubForTest) modelStub).checkModule(ACC1002X));
        assertEquals(createCommandResult(" CS1010", " ACC1002", " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Module cs1010 = new ModuleBuilder().withCode(new Code("CS1010")).build();
        Module acc1002x = new ModuleBuilder().withCode(new Code("ACC1002X")).build();
        RemoveModuleFromStudentTakenCommand removeCs1010Command =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)));
        RemoveModuleFromStudentTakenCommand removeAcc1002XCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(acc1002x)));

        // same object -> returns true
        assertTrue(removeCs1010Command.equals(removeCs1010Command));

        // same values -> returns true
        RemoveModuleFromStudentTakenCommand removeCs1010CommandCopy =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)));
        assertTrue(removeCs1010Command.equals(removeCs1010CommandCopy));

        // different types -> returns false
        assertFalse(removeCs1010Command.equals(1));

        // null -> returns false
        assertFalse(removeCs1010Command.equals(null));

        // different person -> returns false
        assertFalse(removeCs1010Command.equals(removeAcc1002XCommand));
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


    private class ModelStubWithNotLogin extends RemoveModuleFromStudentTakenCommandTest.ModelStub {
        @Override
        public User getCurrentUser() {
            return null;
        }
    }

    private class ModelStubWithNonStudentUser extends RemoveModuleFromStudentTakenCommandTest.ModelStub {
        @Override
        public boolean isStudent() {
            return false;
        }

        @Override
        public User getCurrentUser() {
            return new StudentBuilder().build();
        }
    }

    /**
     * A Model stub that always accept the person being removed.
     */
    private class ModelStubForTest extends RemoveModuleFromStudentTakenCommandTest.ModelStub {
        final Student student = new StudentBuilder().build();
        final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        public ModelStubForTest(Module module) {
            student.addModulesTaken(module);
        }

        public ModelStubForTest() {
        }

        public boolean checkModule(Module module) {
            return student.hasModulesTaken(module);
        }

        @Override
        public boolean hasModuleTaken(Module module) {
            requireNonNull(module);
            return student.hasModulesTaken(module);
        }

        @Override
        public void removeModuleTaken(Module module) {
            requireNonNull(module);
            student.removeModulesTaken(module);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public boolean isStudent() {
            return true;
        }

        @Override
        public User getCurrentUser() {
            return student;
        }

        @Override
        public Optional<Module> searchModuleInModuleList(Module module) {
            return moduleList.getModuleInformation(module);

        }
    }

    /**
     * Create a command result with three types of Code.
     */
    private String createCommandResult(String notExistDataCode, String notExistOwnCode, String removeSuccessCode) {
        return RemoveModuleFromStudentTakenCommand.MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE
                + notExistDataCode + '\n'
                + RemoveModuleFromStudentTakenCommand.MESSAGE_MODULE_NOT_EXISTS
                + notExistOwnCode + '\n'
                + RemoveModuleFromStudentTakenCommand.MESSAGE_REMOVE_MODULE_SUCCESS
                + removeSuccessCode;
    }
}
