package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CustomOrderCommand;

public class CustomOrderCommandParserTest {
    private CustomOrderCommandParser parser = new CustomOrderCommandParser();

    @Test
    public void parse_validArguments_returnsCustomOrderCommand() {
        assertParseSuccess(
                parser,
                "2 3",
                new CustomOrderCommand(2, 3));
    }

    @Test
    public void parse_bothArgumentsInvalid_throwsParseException() {
        assertParseFailure(
                parser,
                "a b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexArgumentsInvalid_throwsParseException() {
        assertParseFailure(
                parser,
                "a 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_moveArgumentsInvalid_throwsParseException() {
        assertParseFailure(
                parser,
                "3 b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomOrderCommand.MESSAGE_USAGE));
    }
}
