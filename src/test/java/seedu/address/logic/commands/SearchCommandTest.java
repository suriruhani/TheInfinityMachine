package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_SOURCES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSources.CARL;
import static seedu.address.testutil.TypicalSources.ELLE;
import static seedu.address.testutil.TypicalSources.FIONA;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.SourceContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSourceManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        SourceContainsKeywordsPredicate firstPredicate =
                new SourceContainsKeywordsPredicate(Collections.singletonList("first"));
        SourceContainsKeywordsPredicate secondPredicate =
                new SourceContainsKeywordsPredicate(Collections.singletonList("second"));

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
    public void execute_zeroKeywords_noSourceFound() {
        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        SourceContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());
    }

    @Test
    public void execute_multipleKeywords_multipleSourcesFound() {
        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 3);
        SourceContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredSourceList());
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private SourceContainsKeywordsPredicate preparePredicate(String userInput) {
        return new SourceContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
