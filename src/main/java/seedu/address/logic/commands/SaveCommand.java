package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Config;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";
    public static final String MESSAGE_SUCCESS = "Current module configuration has be saved!";

    private final Config toSaveConfig;

    public SaveCommand(Config newConfig) {
        requireNonNull(newConfig);
        toSaveConfig = newConfig;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        System.out.println("execute 1");
        model.saveConfigFile(toSaveConfig);
        System.out.println("execute 2");
        model.commitAddressBook();
        System.out.println("execute 3");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
