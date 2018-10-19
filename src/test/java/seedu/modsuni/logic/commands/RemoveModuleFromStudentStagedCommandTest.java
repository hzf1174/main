package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalModules.CS1010;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.ReadOnlyAddressBook;
import seedu.modsuni.model.ReadOnlyModuleList;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.person.Person;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.testutil.ModuleBuilder;
import seedu.modsuni.testutil.StudentBuilder;
import seedu.modsuni.testutil.TypicalModules;


public class RemoveModuleFromStudentStagedCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveModuleFromStudentStagedCommand(null);
    }

    @Test
    public void execute_moduleAcceptedByModel_removeSuccessful() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());

        RemoveModuleFromStudentStagedCommand removeModuleFromStudentStagedCommand =
                new RemoveModuleFromStudentStagedCommand(validModuleBeforeSearch);
        RemoveModuleFromStudentStagedCommandTest.ModelStubForTest modelStub =
                new RemoveModuleFromStudentStagedCommandTest.ModelStubForTest(ACC1002);

        CommandResult commandResult = removeModuleFromStudentStagedCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentStagedCommand.getSearchedModule();

        assertNotEquals(validModuleBeforeSearch, validModuleAfterSearch);
        assertEquals(String.format(RemoveModuleFromStudentStagedCommand.MESSAGE_REMOVE_MODULE_SUCCESS,
                validModuleAfterSearch),
                commandResult.feedbackToUser);
        assertFalse(modelStub.student.hasModulesStaged(validModuleAfterSearch));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_moduleNotFound_throwsCommandException() throws Exception {
        Module validModule = ACC1002;
        RemoveModuleFromStudentStagedCommand removeModuleFromStudentStagedCommand =
                new RemoveModuleFromStudentStagedCommand(validModule);
        RemoveModuleFromStudentStagedCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentStagedCommandTest.ModelStubForTest();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveModuleFromStudentStagedCommand.MESSAGE_MODULE_NOT_EXISTS);
        removeModuleFromStudentStagedCommand.execute(modelStub, commandHistory);
    }


    @Test
    public void execute_nonexistentModule_throwsCommandException() throws Exception {
        Module nonexistentModule = CS1010;
        RemoveModuleFromStudentStagedCommand removeModuleFromStudentStagedCommand =
                new RemoveModuleFromStudentStagedCommand(nonexistentModule);
        RemoveModuleFromStudentStagedCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentStagedCommandTest.ModelStubForTest(nonexistentModule);

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveModuleFromStudentStagedCommand.MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE);
        removeModuleFromStudentStagedCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_nonStudentUser_throwsCommandException() throws Exception {
        Module validModuleBeforeSearch = new Module(new Code("ACC1002X"), "", "", "",
                0, true, true, true, true, new ArrayList<Code>(), new Prereq());
        RemoveModuleFromStudentStagedCommand removeModuleFromStudentStagedCommand =
                new RemoveModuleFromStudentStagedCommand(validModuleBeforeSearch);
        RemoveModuleFromStudentStagedCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentStagedCommandTest.ModelStubWithNonStudentUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveModuleFromStudentStagedCommand.MESSAGE_NOT_STUDENT);
        removeModuleFromStudentStagedCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Module cs1010 = new ModuleBuilder().withCode(new Code("CS1010")).build();
        Module acc1002x = new ModuleBuilder().withCode(new Code("ACC1002X")).build();
        RemoveModuleFromStudentStagedCommand removeCs1010Command = new RemoveModuleFromStudentStagedCommand(cs1010);
        RemoveModuleFromStudentStagedCommand removeAcc1002XCommand = new RemoveModuleFromStudentStagedCommand(acc1002x);

        // same object -> returns true
        assertTrue(removeCs1010Command.equals(removeCs1010Command));

        // same values -> returns true
        RemoveModuleFromStudentStagedCommand removeCs1010CommandCopy = new RemoveModuleFromStudentStagedCommand(cs1010);
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
        public List<Module> searchKeyWordInModuleList(Module keyword) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveUserFile(User user, Path savePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<User> readUserFile(Path filePath) throws IOException, DataConversionException {
            throw new AssertionError("This method should not be called.");
        }
    }

    private class ModelStubWithNonStudentUser extends RemoveModuleFromStudentStagedCommandTest.ModelStub {
        @Override
        public boolean isStudent() {
            return false;
        }
    }

    /**
     * A Model stub that always accept the person being removed.
     */
    private class ModelStubForTest extends RemoveModuleFromStudentStagedCommandTest.ModelStub {
        final Student student = new StudentBuilder().build();
        final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        public ModelStubForTest(Module module) {
            student.addModulesStaged(module);
        }

        public ModelStubForTest() {
        }

        @Override
        public boolean hasModuleStaged(Module module) {
            requireNonNull(module);
            return student.hasModulesStaged(module);
        }

        @Override
        public void removeModuleStaged(Module module) {
            requireNonNull(module);
            student.removeModulesStaged(module);
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
        public Optional<Module> searchModuleInModuleList(Module module) {
            return moduleList.getModuleInformation(module);

        }
    }

}
