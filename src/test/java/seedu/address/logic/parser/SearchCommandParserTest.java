package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.source.SourceContainsKeywordsPredicate;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_TITLE, "alice");
//        argMultimap.put(PREFIX_TYPE, "");
//        argMultimap.put(PREFIX_DETAILS, "_detail");
//        argMultimap.put(PREFIX_TAG, "end");
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand = new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap));
        assertParseSuccess(parser, "i/alice", expectedFindCommand);
        // multiple whitespaces between keywords
        //assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
