package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_runsList() {
        assertParseSuccess(parser, "", new ListCommand());
    }

    @Test
    public void parse_spaceArg_runsList() {
        assertParseSuccess(parser, "    ", new ListCommand());
    }

    @Test
    public void parse_singlePositiveArg_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(2), true);
        assertParseSuccess(parser, " 2" , expectedListCommand);
    }

    @Test
    public void parse_singleNegativeArg_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(1), false);
        assertParseSuccess(parser, " -1" , expectedListCommand);
    }

    @Test
    public void parse_twoRangeArgs_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(2), Index.fromOneBased(3));
        assertParseSuccess(parser, " 2 3" , expectedListCommand);
    }

    @Test
    public void parse_twoSameArgs_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(2), Index.fromOneBased(2));
        assertParseSuccess(parser, " 2 2" , expectedListCommand);
    }


}
