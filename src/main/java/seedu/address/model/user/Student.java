package seedu.address.model.user;

/**
 * Represents a Student User.
 *
 */
public class Student extends User {
    /**
     * Constructor method of User
     *
     * @param username         The username of the user.
     * @param name             The name of the user.
     * @param role             The role of the user.
     * @param pathToProfilePic The path to the image to be used as profile picture.
     */
    public Student(String username, String name, Role role,
                   String pathToProfilePic) {
        super(username, name, role, pathToProfilePic);
    }

    @Override
    public void updatePassword(String newPassword) {

    }

    @Override
    public void updateName(String newName) {

    }

    @Override
    public void updateProfilePic(String newPath) {

    }

    @Override
    public void deleteUser(User userToDelete) {

    }
}
