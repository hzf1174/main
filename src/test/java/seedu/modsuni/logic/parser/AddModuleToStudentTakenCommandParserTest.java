package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.modsuni.logic.commands.AddModuleToStudentTakenCommand;

public class AddModuleToStudentTakenCommandParserTest {
    private AddModuleToStudentTakenCommandParser parser = new AddModuleToStudentTakenCommandParser();

    @Test
    public void parse_emptyCode_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddModuleToStudentTakenCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

}
