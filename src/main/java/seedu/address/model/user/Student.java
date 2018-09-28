package seedu.address.model.user;

import java.util.List;

/**
 * Represents a Student User.
 *
 */
public class Student extends User {

    private final String enrollmentDate;
    private final List<String> major;
    private final List<String> minor;
    /**
     * Constructor method of User
     *
     * @param username         The username of the user.
     * @param name             The name of the user.
     * @param role             The role of the user.
     * @param pathToProfilePic The path to the image to be used as profile picture.
     */
    public Student(String username, String name, Role role,
                   String pathToProfilePic, String enrollmentDate,
                   List<String> major, List<String> minor) {
        super(username, name, role, pathToProfilePic);
        this.enrollmentDate = enrollmentDate;
        this.major = major;
        this.minor = minor;
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
    public void deleteUser(User userToDelete) {

    }
}
