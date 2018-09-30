package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Config;
import seedu.address.testutil.ConfigBuilder;

public class SaveCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SaveCommand(null);
    }

    @Test
    public void equals() {
        Config config = new ConfigBuilder().withData("Some data".getBytes()).build();
        SaveCommand configSaveCommand = new SaveCommand(config);

        // same object -> returns true
        assertTrue(configSaveCommand.equals(configSaveCommand));

        // same values -> returns true
        SaveCommand addConfigSaveCommandCopy = new SaveCommand(config);
        assertTrue(addConfigSaveCommandCopy.equals(addConfigSaveCommandCopy));

        // different types -> returns false
        assertFalse(configSaveCommand.equals(1));

        // null -> returns false
        assertFalse(configSaveCommand.equals(null));
    }

}
