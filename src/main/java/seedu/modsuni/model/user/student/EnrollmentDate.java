package seedu.modsuni.model.user.student;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's enrollment date;
 */
public class EnrollmentDate {
    public static final String MESSAGE_DATE_CONSTRAINTS =
        "Enrollment date should be in this format: DD/MM/YYYY";

    public static final String DATE_VALIDATION_REGEX = "\\d{2}/\\d{2}/\\d{4}$";

    public final String enrollmentDate;

    public EnrollmentDate(String enrollmentDate) {
        requireNonNull(enrollmentDate);
        checkArgument(isValidEnrollmentDate(enrollmentDate),
            MESSAGE_DATE_CONSTRAINTS);
        this.enrollmentDate = enrollmentDate;
    }

    public static boolean isValidEnrollmentDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return enrollmentDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EnrollmentDate // instanceof handles nulls
            && enrollmentDate.equals(((EnrollmentDate) other).enrollmentDate)); //
    }

    @Override
    public int hashCode() {
        return enrollmentDate.hashCode();
    }
}
