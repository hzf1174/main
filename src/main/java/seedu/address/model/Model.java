package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.credential.Credential;
import seedu.address.model.credential.ReadOnlyCredentialStore;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;
import seedu.address.model.user.Admin;
import seedu.address.model.user.User;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the ModuleList */
    ReadOnlyModuleList getModuleList();

    /** Returns a ObservableList of modules */
    ObservableList<Module> getObservableModuleList();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Check if the user is a student.
     */
    boolean isStudent();

    /**
     * Adds the given admin.
     * {@code admin} must not already exist in the address book.
     */
    void addAdmin(Admin admin);

    /**
     * Adds the given module to the database.
     * @param module
     */
    void addModuleToDatabase(Module module);

    /**
     * Removes a existing module from the database.
     * @param module
     */
    void removeModuleFromDatabase(Module module);

    /**
     * Returns true if the given {@code module} already exists in the database.
     */
    boolean hasModuleInDatabase(Module module);

    /**
     * Check if the user is a admin..
     */
    boolean isAdmin();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasModuleTaken(Module module);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void removeModuleTaken(Module module);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addModuleTaken(Module module);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasModuleStaged(Module module);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void removeModuleStaged(Module module);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addModuleStaged(Module module);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered module list
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Adds the given credential.
     * {@code credential} must not already exist in the credential store.
     */
    void addCredential(Credential credential);
    /**
     * Returns true if credential with the same username already exists in
     * the credential store.
     */
    boolean hasCredential(Credential credential);

    /**
     * Returns the CredentialStore
     */
    ReadOnlyCredentialStore getCredentialStore();

    /**
     * Returns true if credential is verified within the CredentialStore
     */
    boolean isVerifiedCredential(Credential credential);

    /**
     * Sets the given user as the currentUser.
     * @param user
     */
    void setCurrentUser(User user);

    /**
     * Returns the currentUser.
     */
    User getCurrentUser();

    /**
     * Saves the current user.
     */
    void saveUserFile(User user, Path savePath);

    /**
     * Returns the optional of the module in the storage.
     */
    Optional<Module> searchModuleInModuleList(Module module);

    /**
     * Returns the a list of modules whose codes begin with the keyword.
     */
    List<Module> searchKeyWordInModuleList(Module keyword);
}
