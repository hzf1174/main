package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.user.Student;
import seedu.address.model.user.User;

public class TypicalUsers {

    public static final Student STUDENT_MAX = new StudentBuilder().build();
    public static final Student STUDENT_SEB = new StudentBuilder()
        .withName("Sebestian Vettel")
        .withUsername("vettel5")
        .withProfilePicFilePath("ferrari")
        .withEnrollmentDate("17/06/2007")
        .withMajor(Arrays.asList("CS","BA"))
        .withMinor(Arrays.asList("IS","MA")).build();
    // private static final User ADMIN_TOTO = new AdminBuilder().build();

    private TypicalUsers(){}

    public List<User> getTypicalUsers(){
        return new ArrayList<>(Arrays.asList(STUDENT_MAX, STUDENT_SEB));
    }
}
