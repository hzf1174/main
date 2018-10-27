package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalModules.ACC1002X;
import static seedu.modsuni.testutil.TypicalModules.CS1010;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.person.Person;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.testutil.StudentBuilder;
import seedu.modsuni.testutil.TypicalModules;


public class RemoveModuleFromStudentTakenCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveModuleFromStudentTakenCommand(null, new HashSet<>());
    }

    @Test
    public void constructor_nullDuplicate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(new Code("CS2103T"))), null);
    }

    @Test
    public void execute_moduleAcceptedByModel_removeSuccessful() throws Exception {
        Code validCodeBeforeSearch = new Code("ACC1002");
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());
        RemoveModuleFromStudentTakenCommandTest.ModelStubForTest modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest(ACC1002);

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();

        assertEquals(validCodeBeforeSearch, validModuleAfterSearch.getCode());
        assertEquals(createCommandResult("", "", "", " ACC1002"),
                commandResult.feedbackToUser);
        assertFalse(modelStub.student.hasModulesTaken(validModuleAfterSearch));
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_moduleNotFound_throwsCommandException() throws Exception {
        Code validCodeBeforeSearch = new Code("ACC1002");
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest();

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();
        assertEquals(validCodeBeforeSearch, validModuleAfterSearch.getCode());
        assertEquals(createCommandResult("", "", " ACC1002", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }


    @Test
    public void execute_nonexistentModule_throwsCommandException() throws Exception {
        Code nonexistentCode = new Code("CS1010");
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(nonexistentCode)),
                        new HashSet<>());
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest(CS1010);

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();
        assertNull(validModuleAfterSearch);
        assertEquals(createCommandResult("", " CS1010", "", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_nonStudentUser_throwsCommandException() throws Exception {
        Code validCodeBeforeSearch = new Code("ACC1002X");
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubWithNonStudentUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveModuleFromStudentTakenCommand.MESSAGE_NOT_STUDENT);
        removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_notLogin_throwsCommandException() throws Exception {
        Code validCodeBeforeSearch = new Code("ACC1002X");
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubWithNotLogin();

        thrown.expect(CommandException.class);
        thrown.expectMessage(RemoveModuleFromStudentTakenCommand.MESSAGE_NOT_LOGIN);
        removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_hybridModules_removedCorrectly() throws Exception {
        Code validCode = new Code("ACC1002X");
        Code notExistOwnCode = new Code("ACC1002");
        Code notExistDataCode = new Code("CS1010");
        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(
                        Arrays.asList(validCode, notExistOwnCode, notExistDataCode)), new HashSet<>());
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest(ACC1002X);

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = removeModuleFromStudentTakenCommand.getSearchedModule();
        assertFalse(((ModelStubForTest) modelStub).checkModule(ACC1002X));
        assertEquals(createCommandResult("", " CS1010", " ACC1002", " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_hybridModulesWithDuplicateInCommand_addedCorrectly() throws Exception {
        Code validCode = new Code("ACC1002X");
        Code duplicateCode = new Code("ACC1002");
        Code notExistCode = new Code("CS1010");
        HashSet<String> duplicateCodeInCommandSet = new HashSet<>();
        duplicateCodeInCommandSet.add("ACC1002");
        duplicateCodeInCommandSet.add("CS1010");

        RemoveModuleFromStudentTakenCommand removeModuleFromStudentTakenCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(validCode,
                        duplicateCode, notExistCode)), duplicateCodeInCommandSet);
        RemoveModuleFromStudentTakenCommandTest.ModelStub modelStub =
                new RemoveModuleFromStudentTakenCommandTest.ModelStubForTest(ACC1002X);

        CommandResult commandResult = removeModuleFromStudentTakenCommand.execute(modelStub, commandHistory);
        assertEquals(createCommandResult(" ACC1002 CS1010", " CS1010", " ACC1002",
                " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Code cs1010 = new Code("CS1010");
        Code acc1002x = new Code("ACC1002X");
        HashSet<String> cs1010Duplicate = new HashSet<>();

        cs1010Duplicate.add("CS1010");
        RemoveModuleFromStudentTakenCommand removeCs1010Command =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)), new HashSet<>());
        RemoveModuleFromStudentTakenCommand removeAcc1002XCommand =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(acc1002x)), new HashSet<>());
        RemoveModuleFromStudentTakenCommand removeCs1010DuplicateCs1010Command =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)), cs1010Duplicate);

        // same object -> returns true
        assertTrue(removeCs1010Command.equals(removeCs1010Command));

        // same values -> returns true
        RemoveModuleFromStudentTakenCommand removeCs1010CommandCopy =
                new RemoveModuleFromStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)), new HashSet<>());
        assertTrue(removeCs1010Command.equals(removeCs1010CommandCopy));

        // different types -> returns false
        assertFalse(removeCs1010Command.equals(1));

        // null -> returns false
        assertFalse(removeCs1010Command.equals(null));

        // different code -> returns false
        assertFalse(removeCs1010Command.equals(removeAcc1002XCommand));

        // different duplicate -> returns false
        assertFalse(removeCs1010Command.equals(removeCs1010DuplicateCs1010Command));

        // different code and duplicate -> returns false
        assertFalse(removeAcc1002XCommand.equals(removeCs1010DuplicateCs1010Command));
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
        public ObservableList<Module> getFilteredStagedModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredTakenModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredDatabaseModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDatabaseModuleList(Predicate<Module> predicate) {
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
        public void removeCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Credential getCredential(Username username) {
            throw new AssertionError("THis method should not be called.");
        }

        @Override
        public void addAdmin(Admin admin, Path savePath) {
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
        public Password getCredentialPassword(User user) {
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
        public Optional<Module> searchCodeInDatabase(Code code) {
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
        public boolean isStudent() {
            return true;
        }

        @Override
        public User getCurrentUser() {
            return student;
        }

        @Override
        public Optional<Module> searchCodeInDatabase(Code code) {
            return moduleList.searchCode(code);
        }
    }

    /**
     * Create a command result with three types of Code.
     */
    private String createCommandResult(String duplicateCodeInCommand, String notExistDataCode,
                                       String notExistOwnCode, String removeSuccessCode) {
        if (duplicateCodeInCommand != "") {
            duplicateCodeInCommand = RemoveModuleFromStudentTakenCommand.MESSAGE_DUPLICATE_FOUND_IN_COMMAND
                    .concat(duplicateCodeInCommand + '\n');
        }

        if (notExistDataCode != "") {
            notExistDataCode = RemoveModuleFromStudentTakenCommand.MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE
                    .concat(notExistDataCode + '\n');
        }

        if (notExistOwnCode != "") {
            notExistOwnCode = RemoveModuleFromStudentTakenCommand.MESSAGE_MODULE_NOT_EXISTS
                    .concat(notExistOwnCode + '\n');
        }

        if (removeSuccessCode != "") {
            removeSuccessCode = RemoveModuleFromStudentTakenCommand.MESSAGE_REMOVE_MODULE_SUCCESS
                    .concat(removeSuccessCode + '\n');
        }

        return duplicateCodeInCommand
                + notExistDataCode
                + notExistOwnCode
                + removeSuccessCode;
    }
}
