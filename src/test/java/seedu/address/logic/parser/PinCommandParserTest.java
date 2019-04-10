package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.PinCommand;

public class PinCommandParserTest {
    private PinCommandParser parser = new PinCommandParser();

    @Test
    public void parse_validArguments_returnsPinCommand() {
        assertParseSuccess(
                parser,
                "1",
                new PinCommand(1));
    }

    @Test
    public void parse_invalidArguments_throwsParseException() {
        assertParseFailure(
                parser,
                "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinCommand.MESSAGE_USAGE));
    }
}
