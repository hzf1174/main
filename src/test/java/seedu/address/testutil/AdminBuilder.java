package seedu.address.testutil;

import seedu.address.model.user.Admin;
import seedu.address.model.user.Role;

/**
 * Builds a Admin user.
 */
public class AdminBuilder {

    public static final String DEFAULT_NAME = "John Doe";
    public static final Role DEFAULT_ROLE = Role.ADMIN;
    public static final String DEFAULT_PIC = " ";
    public static final int DEFAULT_SALARY = 3000;
    public static final String DEFAULT_EMPLOYEDDATE = "1/1/2018";

    private String username;
    private String name;
    private Role role;
    private String pic;
    private int salary;
    private String employedDate;

    public AdminBuilder() {
        name = DEFAULT_NAME;
        role = DEFAULT_ROLE;
        pic = DEFAULT_PIC;
        salary = DEFAULT_SALARY;
        employedDate = DEFAULT_EMPLOYEDDATE;
    }

    /**
     * Initializes the AdminBuilder with the data of {@code adminToCopy}.
     */
    public AdminBuilder(Admin adminToCopy) {
        username = adminToCopy.getUsername();
        name = adminToCopy.getName();
        role = adminToCopy.getRole();
        pic = adminToCopy.getPathToProfilePic();
        salary = adminToCopy.getSalary();
        employedDate = adminToCopy.getEmploymentDate();
    }

    /**
     * Sets the {@code name} of the {@code Admin} that we are building.
     */
    public AdminBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Admin} that we are building.
     */
    public AdminBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the {@code pic} of the {@code Admin} that we are building.
     */
    public AdminBuilder withPic(String path) {
        this.pic = path;
        return this;
    }

    /**
     * Sets the {@code salary} of the {@code Admin} that we are building.
     */
    public AdminBuilder withSalary(int salary) {
        this.salary = salary;
        return this;
    }

    /**
     * Sets the {@code employedDate} of the {@code Admin} that we are building.
     */
    public AdminBuilder withEmployedDate(String date) {
        this.employedDate = date;
        return this;
    }

    public Admin build() {
        return new Admin(username, name, role, pic, salary, employedDate);
    }

}
