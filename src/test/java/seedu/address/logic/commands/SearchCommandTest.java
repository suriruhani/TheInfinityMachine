package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.SourceContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    /*
    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSourceManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    */

    @Test
    public void equals() {
        /*

//        ArgumentMultimap first = new ArgumentMultimap();
//        first.put(new Prefix("first"), "");
//
//        ArgumentMultimap second = new ArgumentMultimap();
//        second.put(new Prefix("second"), "");
//
//        SourceContainsKeywordsPredicate firstPredicate =
//                new SourceContainsKeywordsPredicate(first);
//        SourceContainsKeywordsPredicate secondPredicate =
//                new SourceContainsKeywordsPredicate(second);
//
//        SearchCommand searchFirstCommand = new SearchCommand(firstPredicate);
//        SearchCommand searchSecondCommand = new SearchCommand(secondPredicate);
//
//        // same object -> returns true
//        assertTrue(searchFirstCommand.equals(searchFirstCommand));
//
//        // same values -> returns true
//        SearchCommand searchFirstCommandCopy = new SearchCommand(firstPredicate);
//        assertTrue(searchFirstCommand.equals(searchFirstCommandCopy));
//
//        // different types -> returns false
//        assertFalse(searchFirstCommand.equals(1));
//
//        // null -> returns false
//        assertFalse(searchFirstCommand.equals(null));
//
//        // different source -> returns false
//        assertFalse(searchFirstCommand.equals(searchSecondCommand));
        */
    }

    @Test
    public void execute_zeroKeywords_noSourceFound() {
        /*
//        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
//        SourceContainsKeywordsPredicate predicate = preparePredicate(" ");
//        SearchCommand command = new SearchCommand(predicate);
//        expectedModel.updateFilteredSourceList(predicate);
//        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
//        assertEquals(Collections.emptyList(), model.getFilteredSourceList());
        */
    }

    @Test
    public void execute_multipleKeywords_multipleSourcesFound() {
        /*
        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 3);
        SourceContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredSourceList());
        */
    }

    /**
     * Parses {@code userInput} into a {@code TitleContainsKeywordsPredicate}.
     */
    private SourceContainsKeywordsPredicate preparePredicate(String userInput) {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(new Prefix("i/"), userInput);
        return new SourceContainsKeywordsPredicate(argMultimap);
        //return new SourceContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
