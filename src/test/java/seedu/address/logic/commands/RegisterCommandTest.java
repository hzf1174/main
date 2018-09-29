package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.address.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;
import static seedu.address.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.address.testutil.TypicalUsers.STUDENT_SEB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.credential.Credential;
import seedu.address.model.user.User;
import seedu.address.testutil.CredentialBuilder;

public class RegisterCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCredential_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RegisterCommand(null, STUDENT_MAX);
    }

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RegisterCommand(new CredentialBuilder().build(), null);
    }

    @Test
    public void equals() {
        Credential maxCredential = CREDENTIAL_STUDENT_MAX;
        Credential sebCredential = CREDENTIAL_STUDENT_SEB;
        User maxUser = STUDENT_MAX;
        User sebUser = STUDENT_SEB;

        RegisterCommand registerMaxCommand =
            new RegisterCommand(maxCredential, maxUser);
        RegisterCommand registerSebCommand =
            new RegisterCommand(sebCredential, sebUser);

        // same object -> returns true
        assertTrue(registerMaxCommand.equals(registerMaxCommand));

        // same values -> returns true
        RegisterCommand registerMaxCommandCopy =
            new RegisterCommand(maxCredential, maxUser);
        assertTrue(registerMaxCommand.equals(registerMaxCommandCopy));

        // different types -> returns false
        assertFalse(registerMaxCommand.equals(1));

        // null -> returns false
        assertFalse(registerMaxCommand.equals(null));

        // different person -> returns false
        assertFalse(registerMaxCommand.equals(registerSebCommand));
    }
}
