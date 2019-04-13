package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BAR;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_ENGINEERING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.source.SourceContainsKeywordsPredicate;

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
    public void parse_twoNonRangeArgs_returnsSearchCommand() {
        ListCommand expectedListCommand = new ListCommand(Index.fromOneBased(3), Index.fromOneBased(2));
        assertParseFailure(parser, " 3 2" , ListCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_validArgs_returnsSearchCommand() {
        String input = TITLE_DESC_ENGINEERING;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        // no leading and trailing whitespaces
        SearchCommand expectedSearchCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, TITLE_DESC_ENGINEERING, expectedSearchCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n \t" + TITLE_DESC_ENGINEERING, expectedSearchCommand);
    }

    @Test
    public void parse_validMultipleArgs_returnsSearchCommand() {
        String input = TYPE_DESC_ENGINEERING + TYPE_DESC_ENGINEERING;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        SearchCommand expectedSearchCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, TYPE_DESC_ENGINEERING + TYPE_DESC_ENGINEERING, expectedSearchCommand);
    }

    @Test
    public void parse_validCompoundArgs_returnsSearchCommand() {
        String input = TYPE_DESC_ENGINEERING + TAG_DESC_BAR;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        SearchCommand expectedSearchCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, TYPE_DESC_ENGINEERING + TAG_DESC_BAR, expectedSearchCommand);
    }

}
