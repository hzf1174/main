package seedu.modsuni.model;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.modsuni.commons.core.ComponentManager;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.model.AddressBookChangedEvent;
import seedu.modsuni.commons.events.model.CredentialStoreChangedEvent;
import seedu.modsuni.commons.events.model.ModuleListChangedEvent;
import seedu.modsuni.commons.events.model.SaveUserChangedEvent;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.person.Person;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.exceptions.NotStudentUserException;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.storage.UserStorage;
import seedu.modsuni.storage.XmlUserStorage;

/**
 * Represents the in-memory model of the modsuni book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final ReadOnlyModuleList databaseModuleList;
    private final ReadOnlyModuleList stagedModuleList;
    private final ReadOnlyModuleList takenModuleList;
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final CredentialStore credentialStore;
    private FilteredList<Module> filteredDatabaseModules;
    private FilteredList<Module> filteredStagedModules;
    private FilteredList<Module> filteredTakenModules;
    private User currentUser = null;

    /**
     * Initializes a ModelManager with the given databaseModuleList, addressBook, userPrefs, credentialStore and
     * configStore.
     */
    public ModelManager(ReadOnlyModuleList databseModuleList, ReadOnlyAddressBook addressBook, UserPrefs userPrefs,
                        ReadOnlyCredentialStore credentialStore) {

        requireAllNonNull(databseModuleList, addressBook, userPrefs, credentialStore);

        logger.fine("Initializing with modulelist: " + databseModuleList + " modsuni book: " + addressBook
            + " and user prefs " + userPrefs);


        this.databaseModuleList = databseModuleList;
        this.stagedModuleList = new ModuleList();
        this.takenModuleList = new ModuleList();
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        this.credentialStore = (CredentialStore) credentialStore;
        this.currentUser = null;
        this.filteredDatabaseModules = new FilteredList<>(databseModuleList.getModuleList());
        this.filteredStagedModules = new FilteredList<>(stagedModuleList.getModuleList());
        this.filteredTakenModules = new FilteredList<>(takenModuleList.getModuleList());
    }

    public ModelManager() {
        this(new ModuleList(), new AddressBook(), new UserPrefs(),
            new CredentialStore());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyModuleList getModuleList() {
        return databaseModuleList;
    }

    @Override
    public Optional<Module> searchCodeInDatabase(Code code) {
        requireNonNull(code);
        ModuleList moduleList = (ModuleList) getModuleList();
        return moduleList.searchCode(code);
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public boolean hasModuleTaken(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        return student.hasModulesTaken(module);
    }

    @Override
    public void removeModuleTaken(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.removeModulesTaken(module); (
                (ModuleList) takenModuleList).removeModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addModuleTaken(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.addModulesTaken(module); (
                (ModuleList) takenModuleList).addModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public boolean hasModuleStaged(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        return student.hasModulesStaged(module);
    }

    @Override
    public void removeModuleStaged(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.removeModulesStaged(module); (
                (ModuleList) stagedModuleList).removeModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addModuleStaged(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.addModulesStaged(module); (
                (ModuleList) stagedModuleList).addModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Student Account Management =============================================================

    @Override
    public boolean isStudent() {
        if (currentUser == null) {
            return false;
        }
        return currentUser.getRole() == Role.STUDENT && currentUser instanceof Student;
    }

    //=========== Admin Account Management =============================================================

    @Override
    public void addAdmin(Admin admin, Path savePath) {
        requireNonNull(admin);
        saveUserFile(admin, savePath);

    }

    /**
     * Raise an event indicating that credential store has change
     */
    private void indicateModuleListChanged() {
        raise(new ModuleListChangedEvent(databaseModuleList));
    }

    @Override
    public void addModuleToDatabase(Module module) {
        requireNonNull(module); (
                (ModuleList) databaseModuleList).addModule(module);
        indicateModuleListChanged();
    }

    @Override
    public void removeModuleFromDatabase(Module module) {
        requireNonNull(module); (
                (ModuleList) databaseModuleList).removeModule(module);
        indicateModuleListChanged();
    }

    @Override
    public boolean hasModuleInDatabase(Module module) {
        requireNonNull(module);
        return ((ModuleList) databaseModuleList).hasModule(module);
    }

    @Override
    public ObservableList<Module> getObservableModuleList() {
        ModuleList modList = (ModuleList) this.getModuleList();
        return modList.getModuleList();
    }

    @Override
    public boolean isAdmin() {
        return currentUser.getRole() == Role.ADMIN;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     */
    @Override
    public ObservableList<Module> getFilteredDatabaseModuleList() {
        return FXCollections.unmodifiableObservableList(filteredDatabaseModules);
    }

    @Override
    public ObservableList<Module> getFilteredStagedModuleList() {
        return FXCollections.unmodifiableObservableList(filteredStagedModules);
    }

    @Override
    public ObservableList<Module> getFilteredTakenModuleList() {
        return FXCollections.unmodifiableObservableList(filteredTakenModules);
    }

    @Override
    public void updateFilteredDatabaseModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredDatabaseModules.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Save current User to file ====================================
    @Override
    public void saveUserFile(User user, Path savePath) {
        raise(new SaveUserChangedEvent(user, getCredentialPassword(user),
            savePath));
    }

    @Override
    public Optional<User> readUserFile(Path filePath) throws IOException, DataConversionException {
        logger.fine("Attempting to read data from file: " + filePath);
        UserStorage userStorage = new XmlUserStorage(filePath);
        return userStorage.readUser(filePath);
    }

    //============ Credential Store Methods ====================================

    /**
     * Raise an event indicating that credential store has change
     */
    private void indicateCredentialStoreChanged() {
        raise(new CredentialStoreChangedEvent(credentialStore));
    }

    @Override
    public void addCredential(Credential credential) {
        credentialStore.addCredential(credential);
        indicateCredentialStoreChanged();
    }

    @Override
    public void removeCredential(Credential credential) {
        credentialStore.removeCredential(credential);
        indicateCredentialStoreChanged();
    }

    @Override
    public Credential getCredential(Username username) {
        return credentialStore.getCredential(username);
    }

    @Override
    public boolean hasCredential(Credential credential) {
        return credentialStore.hasCredential(credential);
    }

    @Override
    public ReadOnlyCredentialStore getCredentialStore() {
        return credentialStore;
    }

    @Override
    public boolean isVerifiedCredential(Credential toVerify) {
        return credentialStore.isVerifiedCredential(toVerify);
    }

    @Override
    public Password getCredentialPassword(User user) {
        return credentialStore.getCredentialPassword(user.getUsername());
    }

    //============= User Account Management Methods ============================

    @Override
    public void setCurrentUser(User user) {
        requireNonNull(user);
        currentUser = user;
        if (isStudent()) {
            Student student = (Student) getCurrentUser(); (
            (ModuleList) stagedModuleList).setModules(student.getModulesStaged().asUnmodifiableObservableList()); (
                    (ModuleList) takenModuleList).setModules(student.getModulesTaken().asUnmodifiableObservableList());
        }
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
            && filteredPersons.equals(other.filteredPersons)
            && credentialStore.equals(other.credentialStore);
    }
}
