package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;

import org.junit.Test;

import seedu.address.logic.commands.RestoreCommand;

public class RestoreCommandParserTest {

    private RestoreCommandParser parser = new RestoreCommandParser();

    @Test
    public void parse_validArgs_returnsRestoreCommand() {
        assertParseSuccess(parser, "1", new RestoreCommand(INDEX_FIRST_SOURCE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestoreCommand.MESSAGE_USAGE));
    }
}
