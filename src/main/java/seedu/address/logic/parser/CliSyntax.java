package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_USERNAME = new Prefix("user/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pass/");
    public static final Prefix PREFIX_PATH_TO_PIC = new Prefix("pic/");
    public static final Prefix PREFIX_STUDENT_ENROLLMENT_DATE = new Prefix(
        "enroll/");
    public static final Prefix PREFIX_STUDENT_MAJOR = new Prefix("maj/");
    public static final Prefix PREFIX_STUDENT_MINOR = new Prefix("min/");

}
