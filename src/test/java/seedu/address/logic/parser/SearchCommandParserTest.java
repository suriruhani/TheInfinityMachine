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

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.source.SourceContainsKeywordsPredicate;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTitlePrefixArgs_returnsSearchCommand() {
        String input = " " + PREFIX_TITLE + " ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        SearchCommand expectedSearchCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, " " + PREFIX_TITLE + " ", expectedSearchCommand);
    }

    @Test
    public void parse_emptyTypePrefixArgs_returnsSearchCommand() {
        String input = " " + PREFIX_TYPE + " ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        SearchCommand expectedSearchCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, " " + PREFIX_TYPE + " ", expectedSearchCommand);
    }

    @Test
    public void parse_emptyDetailPrefixArgs_returnsSearchCommand() {
        String input = " " + PREFIX_DETAILS + " ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        SearchCommand expectedSearchCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, " " + PREFIX_DETAILS + " ", expectedSearchCommand);
    }

    @Test
    public void parse_emptyTagPrefixArgs_returnsSearchCommand() {
        String input = " " + PREFIX_TAG + " ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        SearchCommand expectedSearchCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, " " + PREFIX_TAG + " ", expectedSearchCommand);
    }

    @Test
    public void parse_validSingleArg_returnsSearchCommand() {
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
