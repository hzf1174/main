package seedu.address.model.user.student;

import java.util.List;
import java.util.Objects;

import seedu.address.model.credential.Username;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.user.Name;
import seedu.address.model.user.PathToProfilePic;
import seedu.address.model.user.Role;
import seedu.address.model.user.User;

/**
 * Represents a Student User.
 *
 */
public class Student extends User {
    protected EnrollmentDate enrollmentDate;
    protected List<String> major;
    protected List<String> minor;

    protected UniqueModuleList modulesTaken;
    protected UniqueModuleList modulesStaged;

    /**
     * Constructor method of User
     *
     * @param username         The username of the user.
     * @param name             The name of the user.
     * @param role             The role of the user.
     * @param pathToProfilePic The path to the image to be used as profile picture.
     */
    public Student(Username username, Name name, Role role,
                   PathToProfilePic pathToProfilePic, EnrollmentDate enrollmentDate,
                   List<String> major, List<String> minor) {
        super(username, name, role, pathToProfilePic);
        this.enrollmentDate = enrollmentDate;
        this.major = major;
        this.minor = minor;
        this.modulesTaken = new UniqueModuleList();
        this.modulesStaged = new UniqueModuleList();
    }

    /**
     * Constructor method of User
     *
     * @param username         The username of the user.
     * @param name             The name of the user.
     * @param role             The role of the user.
     * @param pathToProfilePic The path to the image to be used as profile picture.
     * @param modulesTaken     The list of modules taken.
     */
    public Student(Username username, Name name, Role role,
                   PathToProfilePic pathToProfilePic, EnrollmentDate enrollmentDate,
                   List<String> major, List<String> minor, UniqueModuleList modulesTaken) {
        super(username, name, role, pathToProfilePic);
        this.enrollmentDate = enrollmentDate;
        this.major = major;
        this.minor = minor;
        this.modulesTaken = modulesTaken;
    }

    public void updateEnrollmentDate(EnrollmentDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public void updateMajors(List<String> newMajors) {
        this.major = newMajors;
    }

    public void updateMinors(List<String> newMinors) {
        this.minor = newMinors;
    }

    /**
     * Returns true if both student's profile contains the module and false otherwise.
     */
    public boolean hasModulesTaken(Module module) {
        return modulesTaken.contains(module);
    }

    /**
     * Removes a module inside the module list the student has already taken.
     * @param module
     */
    public void removeModulesTaken(Module module) {
        modulesTaken.remove(module);
    }

    /**
     * Adds a module inside the module list the student has already taken.
     * @param module
     */
    public void addModulesTaken(Module module) {
        modulesTaken.add(module);
    }
    /**
     * Returns true if both student's profile contains the module and false otherwise.
     */
    public boolean hasModulesStaged(Module module) {
        return modulesStaged.contains(module);
    }

    public void removeModulesStaged(Module module) {
        modulesStaged.remove(module);
    }

    public void addModulesStaged(Module module) {
        modulesStaged.add(module);
    }

    public UniqueModuleList getModulesStaged() {
        return modulesStaged;
    }

    public UniqueModuleList getModulesTaken() {
        return modulesTaken;
    }

    public EnrollmentDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public List<String> getMajor() {
        return major;
    }

    public List<String> getMinor() {
        return minor;
    }

    /**
     * Returns true if the student has added modules to take and false if otherwise.
     */
    public boolean hasModuleToTake() {
        if (modulesStaged.hasModules()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updatePassword(String newPassword) {
        //TODO
    }

    @Override
    public void updateName(String newName) {
        //TODO
    }

    @Override
    public void updateProfilePic(String newPath) {
        //TODO
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getUsername().equals(getUsername());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Student Username: ")
            .append(getUsername().getUsername());
        return builder.toString();
    }
}
