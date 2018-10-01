package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 *
 */
public class GenerateCommand extends Command {

    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_SUCCESS = "Generate success!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
