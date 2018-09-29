package seedu.address.testutil;

import java.util.Arrays;
import java.util.List;

import seedu.address.model.user.Role;
import seedu.address.model.user.Student;

public class StudentBuilder {

    public static final String DEFAULT_NAME = "Max Verstappen";
    public static final String DEFAULT_USERNAME = "max33";
    public static final String DEFAULT_PROFILE_PIC_FILEPATH = "redbull";
    public static final Role   DEFAULT_ROLE = Role.STUDENT;
    public static final String DEFAULT_ENROLLMENT_DATE = "15/03/2015";
    public static final List<String> DEFAULT_MAJOR = Arrays.asList("CS", "DA");
    public static final List<String> DEFAULT_MINOR = Arrays.asList("MA", "BA");

    private String name;
    private String username;
    private final Role role;
    private String profilePicFilePath;
    private String enrollmentDate;
    private List<String> major;
    private List<String> minor;

    public StudentBuilder() {
        name = DEFAULT_NAME;
        username = DEFAULT_USERNAME;
        role = DEFAULT_ROLE;
        profilePicFilePath = DEFAULT_PROFILE_PIC_FILEPATH;
        enrollmentDate = DEFAULT_ENROLLMENT_DATE;
        major = DEFAULT_MAJOR;
        minor = DEFAULT_MINOR;
    }

    public StudentBuilder withName(String name){
        this.name = name;
        return this;
    }

    public StudentBuilder withUsername(String username){
        this.username = username;
        return this;
    }

    public StudentBuilder withProfilePicFilePath(String pathToPic){
        this.profilePicFilePath = pathToPic;
        return this;
    }

    public StudentBuilder withEnrollmentDate(String enrollmentDate){
        this.enrollmentDate = enrollmentDate;
        return this;
    }

    public StudentBuilder withMajor(List<String> inputMajor){
        this.major = inputMajor;
        return this;
    }

    public StudentBuilder withMinor(List<String> inputMinor){
        this.minor = inputMinor;
        return this;
    }

    public Student build(){
        return new Student(username, name, role, profilePicFilePath,
            enrollmentDate, major, minor);
    }


}