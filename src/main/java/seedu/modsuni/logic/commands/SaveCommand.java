package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SAVE_PATH;

import java.nio.file.Path;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.NewCommandResultAvailableEvent;
import seedu.modsuni.commons.events.ui.NewSaveResultAvailableEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.user.User;
import seedu.modsuni.ui.SaveDisplay;

/**
 * Saves the current user.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves current user data to a specific path. "
            + "Parameters: "
            + PREFIX_SAVE_PATH + "FILEPATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SAVE_PATH + "userdata.xml";

    public static final String MESSAGE_SUCCESS = "Current user data has be saved!";

    public static final String MESSAGE_ERROR = "Unable to save. Please ensure that you are registered or logged in.";

    private final Path savePath;

    public SaveCommand(Path savePath) {
        requireAllNonNull(savePath);
        this.savePath = savePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        User currentUser = model.getCurrentUser();

        if (currentUser == null) {
            throw new CommandException(MESSAGE_ERROR);
        }

        model.saveUserFile(currentUser, savePath);

        NewCommandResultAvailableEvent newCommandResultAvailableEvent = new NewCommandResultAvailableEvent();
        newCommandResultAvailableEvent.setToBeDisplayed(new SaveDisplay());
        EventsCenter.getInstance().post(newCommandResultAvailableEvent);

        EventsCenter.getInstance().post(new NewSaveResultAvailableEvent(currentUser));

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveCommand // instanceof handles nulls
                && savePath.equals(((SaveCommand) other).savePath));
    }
}
