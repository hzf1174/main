package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.ShowStagedTabRequestEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;

/**
 * Adds several modules to the student's staged module list.
 * Keyword matching is case insensitive.
 */
public class AddModuleToStudentStagedCommand extends Command {

    public static final String COMMAND_WORD = "addModuleS";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds on one module to your profile. "
            + "Parameters: "
            + "MOD_CODE(case insensitive)"
            + "[MORE MOD_CODE]\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2103T cs1010";

    public static final String MESSAGE_SUCCESS = "These modules have been successfully added:";
    public static final String MESSAGE_DUPLICATE_MODULE =
            "These modules can not be added because they already exist in your staged module list:";
    public static final String MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE =
            "These modules can not be added because they do not exist in our database:";
    public static final String MESSAGE_NOT_STUDENT = "Only a student user can execute this command";
    public static final String MESSAGE_NOT_LOGIN = "Please register or login";
    public static final String MESSAGE_DUPLICATE_FOUND_IN_COMMAND = "We found these duplicate entries in your input:";

    private final ArrayList<Code> toSearch;
    private Module toAdd;
    private String duplicateCodeInCommand;
    private String addSuccessCode;
    private String duplicateCode;
    private String notExistCode;

    /**
     * Creates an AddModuleToStudentTakenCommand to add the specified {@code module}
     */
    public AddModuleToStudentStagedCommand(ArrayList<Code> codeList, Set<String> duplicateSet) {
        requireNonNull(codeList);
        requireNonNull(duplicateSet);

        toSearch = codeList;
        toAdd = null;

        if (duplicateSet.isEmpty()) {
            duplicateCodeInCommand = "";
        } else {
            duplicateCodeInCommand = MESSAGE_DUPLICATE_FOUND_IN_COMMAND;
            Iterator<String> it = duplicateSet.iterator();
            while (it.hasNext()) {
                String code = it.next();
                duplicateCodeInCommand = duplicateCodeInCommand.concat(" " + code);
            }
            duplicateCodeInCommand = duplicateCodeInCommand.concat("\n");
        }

        addSuccessCode = MESSAGE_SUCCESS;
        duplicateCode = MESSAGE_DUPLICATE_MODULE;
        notExistCode = MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE;
    }

    public Module getSearchedModule() {
        return toAdd;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getCurrentUser() == null) {
            throw new CommandException(MESSAGE_NOT_LOGIN);
        }

        if (!model.isStudent()) {
            throw new CommandException(MESSAGE_NOT_STUDENT);
        }

        for (Code code : toSearch) {
            Optional<Module> optionalModule = model.searchCodeInDatabase(code);

            if (optionalModule.isPresent()) {
                toAdd = optionalModule.get();
            } else {
                notExistCode = notExistCode.concat(" " + code.toString());
                continue;
            }

            if (model.hasModuleStaged(toAdd)) {
                duplicateCode = duplicateCode.concat(" " + code.toString());
                continue;
            }

            model.addModuleStaged(toAdd);
            addSuccessCode = addSuccessCode.concat(" " + code.toString());
        }

        EventsCenter.getInstance().post(new ShowStagedTabRequestEvent());

        if (notExistCode.equals(MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE)) {
            notExistCode = "";
        } else {
            notExistCode = notExistCode.concat("\n");
        }

        if (duplicateCode.equals(MESSAGE_DUPLICATE_MODULE)) {
            duplicateCode = "";
        } else {
            duplicateCode = duplicateCode.concat("\n");
        }

        if (addSuccessCode.equals(MESSAGE_SUCCESS)) {
            addSuccessCode = "";
        } else {
            addSuccessCode = addSuccessCode.concat("\n");
        }

        return new CommandResult(duplicateCodeInCommand + notExistCode + duplicateCode + addSuccessCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleToStudentStagedCommand // instanceof handles nulls
                && toSearch.equals(((AddModuleToStudentStagedCommand) other).toSearch))
                && duplicateCodeInCommand.equals(((AddModuleToStudentStagedCommand) other).duplicateCodeInCommand);
    }
}
