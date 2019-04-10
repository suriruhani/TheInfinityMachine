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
import static seedu.address.testutil.TypicalSources.ALICE;
import static seedu.address.testutil.TypicalSources.BENSON;
import static seedu.address.testutil.TypicalSources.CARL;
import static seedu.address.testutil.TypicalSources.DANIEL;
import static seedu.address.testutil.TypicalSources.ELLE;
import static seedu.address.testutil.TypicalSources.GEORGE;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

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

    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
    private Model expectedModel = new ModelManager(getTypicalSourceManager(), new UserPrefs(),
            getTypicalDeletedSources());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void equals() {

        ArgumentMultimap first = new ArgumentMultimap();
        first.put(PREFIX_TITLE, "");

        ArgumentMultimap second = new ArgumentMultimap();
        second.put(PREFIX_TYPE, "");

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
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TITLE);
        sList.add("");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_zeroTypeKeywords_noSourceFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TYPE);
        sList.add("");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_zeroDetailKeywords_noSourceFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_DETAILS);
        sList.add("");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_zeroTagKeywords_noSourceFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TAG);
        sList.add("");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneTitleKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 1);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TITLE);
        sList.add("Carl Kurz");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneTypeKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 1);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TYPE);
        sList.add("alice tpye");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneDetailKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 2);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_DETAILS);
        sList.add("carl_detail");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL), model.getFilteredSourceList());

    }

    @Test
    public void execute_oneTagKeyword_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 3);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TAG);
        sList.add("fren");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredSourceList());

    }

    @Test
    public void execute_multipleTitleKeywords_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 2);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TITLE);
        pList.add(PREFIX_TITLE);
        sList.add("alice");
        sList.add("paul");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL), model.getFilteredSourceList());

    }

    @Test
    public void execute_multipleTypeKeywords_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 0);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TYPE);
        pList.add(PREFIX_TYPE);
        sList.add("alice");
        sList.add("ben");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredSourceList());

    }

    @Test
    public void execute_multipleDetailKeywords_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 2);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_DETAILS);
        pList.add(PREFIX_DETAILS);
        pList.add(PREFIX_DETAILS);
        pList.add(PREFIX_DETAILS);
        sList.add("elle_detail");
        sList.add("george_detail");
        sList.add("tail");
        sList.add("eta");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, GEORGE), model.getFilteredSourceList());

    }

    @Test
    public void execute_multipleTagKeywords_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 3);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TAG);
        pList.add(PREFIX_TAG);
        pList.add(PREFIX_TAG);
        sList.add("friend");
        sList.add("end");
        sList.add("fri");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredSourceList());

    }

    @Test
    public void execute_multipleCompoundKeywords_multipleSourcesFound() {

        String expectedMessage = String.format(MESSAGE_SOURCES_LISTED_OVERVIEW, 2);
        ArrayList<Prefix> pList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();
        pList.add(PREFIX_TITLE);
        pList.add(PREFIX_TYPE);
        pList.add(PREFIX_DETAILS);
        pList.add(PREFIX_TAG);
        sList.add("aliec");
        sList.add("");
        sList.add("_detail");
        sList.add("end");
        SourceContainsKeywordsPredicate predicate = preparePredicate(pList, sList);
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredSourceList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, DANIEL), model.getFilteredSourceList());

    }

    /**
     * Parses {@code userInput} into a {@code SourceContainsKeywordsPredicate}.
     */
    private SourceContainsKeywordsPredicate preparePredicate(ArrayList<Prefix> prefix, ArrayList<String> userInput) {
        assert prefix.size() == userInput.size();
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        Iterator<Prefix> pIter = prefix.iterator();
        Iterator<String> sIter = userInput.iterator();
        while (pIter.hasNext() && sIter.hasNext()) {
            argMultimap.put(pIter.next(), sIter.next());
        }
        return new SourceContainsKeywordsPredicate(argMultimap);
    }
}
