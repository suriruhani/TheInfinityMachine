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
    public void parse_singlePositiveArgEqualTotal_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(20), true);
        assertParseSuccess(parser, " 20" , expectedListCommand);
    }

    @Test
    public void parse_singleNegativeArg_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(1), false);
        assertParseSuccess(parser, " -1" , expectedListCommand);
    }

    @Test
    public void parse_singleNegativeArgEqualTotal_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(20), false);
        assertParseSuccess(parser, " -20" , expectedListCommand);
    }

    @Test
    public void parse_singlePositiveArgWithSpaces_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(2), true);
        assertParseSuccess(parser, "   2    " , expectedListCommand);
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

    @Test
    public void parse_twoRangeArgsWithSpace_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(1), Index.fromOneBased(4));
        assertParseSuccess(parser, "   1    4  " , expectedListCommand);
    }

    @Test
    public void parse_moreThanTwoArgs_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(2), Index.fromOneBased(3));
        assertParseSuccess(parser, " 2 3 4" , expectedListCommand);
    }

    @Test
    public void parse_moreThanTwoArgsWithSpaces_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(1), Index.fromOneBased(3));
        assertParseSuccess(parser, " 1     3        4 5" , expectedListCommand);
    }
}
