package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH_TO_PIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ENROLLMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_MINOR;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.credential.Credential;
import seedu.address.model.user.User;


/**
 * Adds a new Student to the local database.
 */
public class RegisterCommand extends Command{

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Registers a new Student Account. "
        + "Parameters: "
        + PREFIX_USERNAME + "USERNAME "
        + PREFIX_PASSWORD + "PASSWORD "
        + PREFIX_NAME + "NAME "
        + PREFIX_PATH_TO_PIC + "PATH "
        + PREFIX_STUDENT_ENROLLMENT_DATE + "DD/MM/YY "
        + PREFIX_STUDENT_MAJOR + "MAJORCODE_1 " + PREFIX_STUDENT_MAJOR +
        "MAJORCODE_2 "
        + PREFIX_STUDENT_MINOR + "MINORCODE_1 " + PREFIX_STUDENT_MINOR +
        "MINORCODE_2";

    public static final String MESSAGE_SUCCESS = "New Account created added: " +
        "%1$s";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists in the database";

    private final Credential toRegister;
    private final User user;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public RegisterCommand(Credential newCredential, User newUser) {
        requireAllNonNull(newCredential, newUser);
        toRegister = newCredential;
        user = newUser;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCredential(toRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_USERNAME);
        }

        model.addCredential(toRegister);
        model.setCurrentUser(user);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toRegister));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RegisterCommand // instanceof handles nulls
            && toRegister.equals(((RegisterCommand) other).toRegister))
            && user.equals(((RegisterCommand) other).user);
    }

}
