package seedu.address.logic.commands;


import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_SOURCES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.testutil.TypicalSources.*;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.SourceContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.Collections;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {

    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources());
    private Model expectedModel = new ModelManager(getTypicalSourceManager(), new UserPrefs(),
            getTypicalDeletedSources());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void equals() {

        ArgumentMultimap first = new ArgumentMultimap();
        first.put(new Prefix("first"), "");

        ArgumentMultimap second = new ArgumentMultimap();
        second.put(new Prefix("second"), "");

        SourceContainsKeywordsPredicate firstPredicate =
                new SourceContainsKeywordsPredicate(first);
        SourceContainsKeywordsPredicate secondPredicate =
                new SourceContainsKeywordsPredicate(second);

        SearchCommand searchFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand searchSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(searchFirstCommand.equals(searchFirstCommand));

        // same values -> returns true
        SearchCommand searchFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));

        // different types -> returns false
        assertFalse(searchFirstCommand.equals(1));

        // null -> returns false
        assertFalse(searchFirstCommand.equals(null));

        // different source -> returns false
        assertFalse(searchFirstCommand.equals(searchSecondCommand));

    }

    @Test
    public void execute_zeroTitleKeywords_noSourceFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_TITLE, "");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_zeroTypeKeywords_noSourceFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_TYPE, "");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_zeroDetailKeywords_noSourceFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_DETAILS, "");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_zeroTagKeywords_noSourceFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_TAG, "");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneTitleKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 1);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_TITLE,"Kurz");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneTypeKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 1);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_TYPE,"carl");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneDetailKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 1);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_DETAILS,"carl_detail");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneTagKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 3);
        SourceContainsKeywordsPredicate predicate = preparePredicate(PREFIX_TITLE,"Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredSourceList());

    }

    /**
     * Parses {@code userInput} into a {@code SourceContainsKeywordsPredicate}.
     */
    private SourceContainsKeywordsPredicate preparePredicate(Prefix prefix, String userInput) {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(prefix, userInput);
        return new SourceContainsKeywordsPredicate(argMultimap);
        //return new SourceContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
