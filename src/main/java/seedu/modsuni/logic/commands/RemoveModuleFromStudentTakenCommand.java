package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.ShowTakenTabRequestEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;

/**
 * Deletes several modules from the student's taken module list.
 * Keyword matching is case insensitive.
 */
public class RemoveModuleFromStudentTakenCommand extends Command {

    public static final String COMMAND_WORD = "removeModuleT";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the module identified by its code.\n"
            + "Parameters: MOD_CODE(case insensitive)"
            + "[MORE MOD_CODE]\n"
            + "Example: " + COMMAND_WORD + " CS2103T cs1010";

    public static final String MESSAGE_REMOVE_MODULE_SUCCESS = "These modules have been successfully removed:";
    public static final String MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE =
            "These modules can not be removed because they do not exist in our database:";
    public static final String MESSAGE_MODULE_NOT_EXISTS =
            "These modules can not be removed because they do not exist in your taken module list:";
    public static final String MESSAGE_NOT_STUDENT = "Only a student user can execute this command";
    public static final String MESSAGE_NOT_LOGIN = "Please register or login";
    public static final String MESSAGE_DUPLICATE_FOUND_IN_COMMAND = "We found these duplicate entries in your input:";


    private final ArrayList<Code> toSearch;
    private Module toRemove;
    private String duplicateCodeInCommand;
    private String removeSuccessCode;
    private String notExistOwnCode;
    private String notExistDataCode;

    public RemoveModuleFromStudentTakenCommand(ArrayList<Code> moduleList, Set<String> duplicateSet) {
        requireNonNull(moduleList);
        requireNonNull(duplicateSet);

        toSearch = moduleList;
        toRemove = null;

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

        removeSuccessCode = MESSAGE_REMOVE_MODULE_SUCCESS;
        notExistOwnCode = MESSAGE_MODULE_NOT_EXISTS;
        notExistDataCode = MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE;
    }

    public Module getSearchedModule() {
        return toRemove;
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
                toRemove = optionalModule.get();
            } else {
                notExistDataCode = notExistDataCode.concat(" " + code.toString());
                continue;
            }

            if (!model.hasModuleTaken(toRemove)) {
                notExistOwnCode = notExistOwnCode.concat(" " + code.toString());
                continue;
            }

            model.removeModuleTaken(toRemove);
            removeSuccessCode = removeSuccessCode.concat(" " + code.toString());
        }

        EventsCenter.getInstance().post(new ShowTakenTabRequestEvent());

        if (notExistDataCode.equals(MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE)) {
            notExistDataCode = "";
        } else {
            notExistDataCode = notExistDataCode.concat("\n");
        }

        if (notExistOwnCode.equals(MESSAGE_MODULE_NOT_EXISTS)) {
            notExistOwnCode = "";
        } else {
            notExistOwnCode = notExistOwnCode.concat("\n");
        }

        if (removeSuccessCode.equals(MESSAGE_REMOVE_MODULE_SUCCESS)) {
            removeSuccessCode = "";
        } else {
            removeSuccessCode = removeSuccessCode.concat("\n");
        }

        return new CommandResult(duplicateCodeInCommand + notExistDataCode
                + notExistOwnCode + removeSuccessCode);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveModuleFromStudentTakenCommand // instanceof handles nulls
                && toSearch.equals(((RemoveModuleFromStudentTakenCommand) other).toSearch)) // state check
                && duplicateCodeInCommand.equals(((RemoveModuleFromStudentTakenCommand) other).duplicateCodeInCommand);
    }
}
