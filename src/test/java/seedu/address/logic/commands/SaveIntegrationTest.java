package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Config;
import seedu.address.model.ConfigStore;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.ConfigBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code SaveCommand}.
 */
public class SaveIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ConfigStore());
    }

    @Test
    public void execute_saveConfig_success() {
        Config validConfig = new ConfigBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new ConfigStore());
        expectedModel.saveConfigFile(validConfig);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new SaveCommand(validConfig), model, commandHistory,
                String.format(SaveCommand.MESSAGE_SUCCESS, validConfig), expectedModel);
    }

}
