package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH_TO_PIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddAdminCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.credential.Credential;
import seedu.address.model.user.Admin;
import seedu.address.model.user.Role;

/**
 * Parses input arguments and creates a new AddAdminCommand object
 */
public class AddAdminCommandParser implements Parser<AddAdminCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAdminCommand
     * and returns an AddAdminCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAdminCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_USERNAME,
                        PREFIX_PASSWORD, PREFIX_PATH_TO_PIC , PREFIX_SALARY, PREFIX_EMPLOYMENT_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_SALARY, PREFIX_EMPLOYMENT_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAdminCommand.MESSAGE_USAGE));
        }


        //Admin Credential
        String username = argMultimap.getValue(PREFIX_USERNAME).get();
        String password = argMultimap.getValue(PREFIX_PASSWORD).get();

        //Admin info
        String name = argMultimap.getValue(PREFIX_NAME).get();
        Role role = Role.ADMIN;
        String pathToProfilePic = argMultimap.getValue(PREFIX_PATH_TO_PIC).get();
        int salary = Integer.parseInt(argMultimap.getValue(PREFIX_SALARY).get());
        String employmentDate = argMultimap.getValue(PREFIX_PATH_TO_PIC).get();
        Admin admin = new Admin(username, name, role, pathToProfilePic, salary,
            employmentDate);



        //TODO key to be replaced
        Credential credential = new Credential(username, password, password);
        return new AddAdminCommand(admin, credential);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
