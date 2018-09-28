package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.stream.Stream;

import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.credential.Credential;
import seedu.address.model.user.Role;
import seedu.address.model.user.Student;
import seedu.address.model.user.User;

/**
 * Parses input arguments and creates a new RegisterCommand object
 */
public class RegisterCommandParser implements Parser<RegisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RegisterCommand and returns an RegisterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RegisterCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_USERNAME,
                PREFIX_PASSWORD, PREFIX_NAME, PREFIX_PATH_TO_PIC);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD
            , PREFIX_NAME, PREFIX_PATH_TO_PIC)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
        }

        String username = argMultimap.getValue(PREFIX_USERNAME).get();
        String password = argMultimap.getValue(PREFIX_PASSWORD).get();
        String name = argMultimap.getValue(PREFIX_NAME).get();
        String pathToPic = argMultimap.getValue(PREFIX_PATH_TO_PIC).get();

        User newUser = new Student(username, name, Role.STUDENT, pathToPic);

        //TODO key to be replaced
        Credential credential = new Credential(username, password, password);

        return new RegisterCommand(credential, newUser);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
