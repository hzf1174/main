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
import seedu.modsuni.model.module.UniqueModuleList;
import seedu.modsuni.model.person.Person;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.testutil.StudentBuilder;
import seedu.modsuni.testutil.TypicalModules;

public class AddModuleToStudentTakenCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddModuleToStudentTakenCommand(null, new HashSet<>());
    }

    @Test
    public void constructor_nullDuplicate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(new Code("CS2103T"))), null);
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        AddModuleToStudentTakenCommandTest.ModelStubAcceptingModuleAdded modelStub =
                new AddModuleToStudentTakenCommandTest.ModelStubAcceptingModuleAdded();
        Code validCodeBeforeSearch = new Code("ACC1002X");
        AddModuleToStudentTakenCommand addModuleToStudentTakenCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());

        CommandResult commandResult = addModuleToStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = addModuleToStudentTakenCommand.getSearchedModule();
        UniqueModuleList expectModuleList = new UniqueModuleList();
        expectModuleList.add(validModuleAfterSearch);

        assertEquals(validCodeBeforeSearch, validModuleAfterSearch.getCode());
        assertEquals(createCommandResult("", "", "", " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(expectModuleList, modelStub.student.getModulesTaken());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateModule_cannotAdded() throws Exception {
        Code validCodeBeforeSearch = new Code("ACC1002X");
        AddModuleToStudentTakenCommand addModuleToStudentTakenCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());
        AddModuleToStudentTakenCommandTest.ModelStub modelStub =
                new AddModuleToStudentTakenCommandTest.ModelStubWithModule(ACC1002X);

        CommandResult commandResult = addModuleToStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = addModuleToStudentTakenCommand.getSearchedModule();
        assertEquals(validCodeBeforeSearch, validModuleAfterSearch.getCode());
        assertEquals(createCommandResult("", "", " ACC1002X", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

    }

    @Test
    public void execute_nonexistentModule_cannotAdded() throws Exception {
        Code nonexistentCode = new Code("CS1010");
        AddModuleToStudentTakenCommand addModuleToStudentTakenCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(nonexistentCode)),
                        new HashSet<>());
        AddModuleToStudentTakenCommandTest.ModelStub modelStub =
                new AddModuleToStudentTakenCommandTest.ModelStubWithModule(CS1010);

        CommandResult commandResult = addModuleToStudentTakenCommand.execute(modelStub, commandHistory);
        Module validModuleAfterSearch = addModuleToStudentTakenCommand.getSearchedModule();
        assertNull(validModuleAfterSearch);
        assertEquals(createCommandResult("", " CS1010", "", ""), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_nonStudentUser_throwsCommandException() throws Exception {
        Code validCodeBeforeSearch = new Code("ACC1002X");
        AddModuleToStudentTakenCommand addModuleToStudentTakenCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());
        AddModuleToStudentTakenCommandTest.ModelStub modelStub =
                new AddModuleToStudentTakenCommandTest.ModelStubWithNonStudentUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleToStudentTakenCommand.MESSAGE_NOT_STUDENT);
        addModuleToStudentTakenCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_hybridModules_addedCorrectly() throws Exception {
        Code validCode = new Code("ACC1002X");
        Code duplicateCode = new Code("ACC1002");
        Code notExistCode = new Code("CS1010");
        AddModuleToStudentTakenCommand addModuleToStudentTakenCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList
                        (validCode, duplicateCode, notExistCode)), new HashSet<>());
        AddModuleToStudentTakenCommandTest.ModelStub modelStub =
                new AddModuleToStudentTakenCommandTest.ModelStubForHybrid(ACC1002);

        CommandResult commandResult = addModuleToStudentTakenCommand.execute(modelStub, commandHistory);
        assertEquals(createCommandResult("", " CS1010", " ACC1002", " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_notLogin_throwsCommandException() throws Exception {
        Code validCodeBeforeSearch = new Code("ACC1002X");
        AddModuleToStudentTakenCommand addModuleToStudentTakenCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(validCodeBeforeSearch)),
                        new HashSet<>());
        AddModuleToStudentTakenCommandTest.ModelStub modelStub =
                new AddModuleToStudentTakenCommandTest.ModelStubWithNotLogin();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddModuleToStudentTakenCommand.MESSAGE_NOT_LOGIN);
        addModuleToStudentTakenCommand.execute(modelStub, commandHistory);
    }


    @Test
    public void execute_hybridModulesWithDuplicateInCommand_addedCorrectly() throws Exception {
        Code validCode = new Code("ACC1002X");
        Code duplicateCode = new Code("ACC1002");
        Code notExistCode = new Code("CS1010");
        HashSet<String> duplicateCodeInCommandSet = new HashSet<>();
        duplicateCodeInCommandSet.add("ACC1002");
        duplicateCodeInCommandSet.add("CS1010");

        AddModuleToStudentTakenCommand addModuleToStudentTakenCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(validCode,
                        duplicateCode, notExistCode)), duplicateCodeInCommandSet);
        AddModuleToStudentTakenCommandTest.ModelStub modelStub =
                new AddModuleToStudentTakenCommandTest.ModelStubForHybrid(ACC1002);

        CommandResult commandResult = addModuleToStudentTakenCommand.execute(modelStub, commandHistory);
        assertEquals(createCommandResult(" ACC1002 CS1010", " CS1010",
                " ACC1002", " ACC1002X"), commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Code cs1010 = new Code("CS1010");
        Code acc1002x = new Code("ACC1002X");
        HashSet<String> cs1010Duplicate = new HashSet<>();

        cs1010Duplicate.add("CS1010");

        AddModuleToStudentTakenCommand addCs1010Command =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)), new HashSet<>());
        AddModuleToStudentTakenCommand addAcc1002XCommand =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(acc1002x)), new HashSet<>());
        AddModuleToStudentTakenCommand addCs1010DuplicateCs1010Command =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)), cs1010Duplicate);

        // same object -> returns true
        assertTrue(addCs1010Command.equals(addCs1010Command));

        // same values -> returns true
        AddModuleToStudentTakenCommand addCs1010CommandCopy =
                new AddModuleToStudentTakenCommand(new ArrayList<>(Arrays.asList(cs1010)), new HashSet<>());
        assertTrue(addCs1010Command.equals(addCs1010CommandCopy));

        // different types -> returns false
        assertFalse(addCs1010Command.equals(1));

        // null -> returns false
        assertFalse(addCs1010Command.equals(null));

        // different code -> returns false
        assertFalse(addCs1010Command.equals(addAcc1002XCommand));

        // different duplicate -> returns false
        assertFalse(addCs1010Command.equals(addCs1010DuplicateCs1010Command));

        // different code and duplicate -> returns false
        assertFalse(addAcc1002XCommand.equals(addCs1010DuplicateCs1010Command));
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

    private class ModelStubWithNonStudentUser extends AddModuleToStudentTakenCommandTest.ModelStub {
        @Override
        public boolean isStudent() {
            return false;
        }

        @Override
        public User getCurrentUser() {
            return new StudentBuilder().build();
        }
    }

    private class ModelStubWithNotLogin extends AddModuleToStudentTakenCommandTest.ModelStub {
        @Override
        public User getCurrentUser() {
            return null;
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends AddModuleToStudentTakenCommandTest.ModelStub {
        private final Module module;
        private final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModuleTaken(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }

        @Override
        public Optional<Module> searchCodeInDatabase(Code code) {
            return moduleList.searchCode(code);
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
    private class ModelStubAcceptingModuleAdded extends AddModuleToStudentTakenCommandTest.ModelStub {
        final Student student = new StudentBuilder().build();
        final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        @Override
        public boolean hasModuleTaken(Module module) {
            requireNonNull(module);
            return student.hasModulesTaken(module);
        }

        @Override
        public void addModuleTaken(Module module) {
            requireNonNull(module);
            student.addModulesTaken(module);
        }

        @Override
        public Optional<Module> searchCodeInDatabase(Code code) {
            return moduleList.searchCode(code);
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
    private class ModelStubForHybrid extends AddModuleToStudentTakenCommandTest.ModelStub {
        private final Module module;
        private final ModuleList moduleList = TypicalModules.getTypicalModuleList();

        ModelStubForHybrid(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public void addModuleTaken(Module module) {
            requireNonNull(module);
            return;
        }

        @Override
        public boolean hasModuleTaken(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }

        @Override
        public Optional<Module> searchCodeInDatabase(Code code) {
            return moduleList.searchCode(code);
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
    private String createCommandResult(String duplicateCodeInCommand, String notExistCode,
                                       String duplicateCode, String addSuccessCode) {
        if (duplicateCodeInCommand != "") {
            duplicateCodeInCommand = AddModuleToStudentTakenCommand.MESSAGE_DUPLICATE_FOUND_IN_COMMAND
                    .concat(duplicateCodeInCommand + '\n');
        }

        if (notExistCode != "") {
            notExistCode = AddModuleToStudentTakenCommand.MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE
                    .concat(notExistCode + '\n');
        }

        if (duplicateCode != "") {
            duplicateCode = AddModuleToStudentTakenCommand.MESSAGE_DUPLICATE_MODULE
                    .concat(duplicateCode + '\n');
        }

        if (addSuccessCode != "") {
            addSuccessCode = AddModuleToStudentTakenCommand.MESSAGE_SUCCESS
                    .concat(addSuccessCode + '\n');
        }

        return duplicateCodeInCommand
                + notExistCode
                + duplicateCode
                + addSuccessCode;
    }

}
