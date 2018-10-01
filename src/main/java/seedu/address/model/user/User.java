package seedu.address.model.user;

/**
 * General details of a User account.
 */
public abstract class User {
    protected String username;
    protected String name;
    protected Role role;
    protected String pathToProfilePic;

    /**
     * Constructor method of User
     *
     * @param name             The name of the user.
     * @param role             The role of the user.
     * @param pathToProfilePic The path to the image to be used as profile picture.
     */
    public User(String username, String name, Role role,
                String pathToProfilePic) {
        this.username = username;
        this.name = name;
        this.role = role;
        this.pathToProfilePic = pathToProfilePic;
    }

    public abstract void updateName(String newName);

    public abstract void updateProfilePic(String newPath);

    public abstract void deleteUser(User userToDelete);

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public String getPathToProfilePic() {
        return pathToProfilePic;
    }

    public abstract void updatePassword (String newPassword);

}
