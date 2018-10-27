package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.modsuni.logic.commands.RemoveModuleFromStudentTakenCommand;
import seedu.modsuni.model.module.Code;

public class RemoveModuleFromStudentTakenCommandParserTest {
    private RemoveModuleFromStudentTakenCommandParser parser = new RemoveModuleFromStudentTakenCommandParser();

    @Test
    public void parse_emptyCode_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveModuleFromStudentTakenCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_multiCode_success() {
        Code cs1010 = new Code("CS1010");
        Code cs2103T = new Code("CS2103T");
        ArrayList<Code> expectedModuleList = new ArrayList<>(Arrays.asList(cs1010, cs2103T));
        assertParseSuccess(parser, "CS1010 CS2103T",
                new RemoveModuleFromStudentTakenCommand(expectedModuleList, new HashSet<>()));
    }

    @Test
    public void parse_caseInsensitive_success() {
        Code cs1010 = new Code("CS1010");
        ArrayList<Code> expectedModuleList = new ArrayList<>(Arrays.asList(cs1010));
        assertParseSuccess(parser, "cs1010",
                new RemoveModuleFromStudentTakenCommand(expectedModuleList, new HashSet<>()));
    }

    @Test
    public void parse_duplicateCode_success() {
        Code cs1010 = new Code("CS1010");
        Code cs2103T = new Code("CS2103T");
        ArrayList<Code> expectedModuleList = new ArrayList<>(Arrays.asList(cs1010, cs2103T));
        HashSet<String> expectedHashSet = new HashSet<>();
        expectedHashSet.add("CS1010");
        assertParseSuccess(parser, "CS1010 CS2103T CS1010 CS1010",
                new RemoveModuleFromStudentTakenCommand(expectedModuleList, expectedHashSet));
    }
}
