package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Finds and lists all modules in the user's profile whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all modules whose codes begin with any of "
            + "the specified keywords (case-insensitive) and displays them as a list.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " CS101";

    private Module keyword;
    private List<Module> result;

    public SearchCommand(Module module) {
        requireNonNull(module);
        this.keyword = module;
    }

    public List<Module> getResult() {
        return result;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        result = model.searchKeyWordInModuleList(keyword);
        return new CommandResult(String.format(Messages.MESSAGE_MODULE_LISTED_OVERVIEW, result.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && keyword.equals(((SearchCommand) other).keyword)); // state check
    }
}
