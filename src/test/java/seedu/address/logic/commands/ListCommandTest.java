package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSourceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalSources.ALGORITHM_RESEARCH;
import static seedu.address.testutil.TypicalSources.AR_RESEARCH;
import static seedu.address.testutil.TypicalSources.COMPUTER_ORGANISATION;
import static seedu.address.testutil.TypicalSources.NAVAL_HISTORY_THREE;
import static seedu.address.testutil.TypicalSources.SENSOR_RESEARCH;
import static seedu.address.testutil.TypicalSources.SMART_COMPUTERS;
import static seedu.address.testutil.TypicalSources.VR_RESEARCH;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;
import static seedu.address.testutil.TypicalSources.getTypicalSources;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSourceManager(), new UserPrefs(),
                getTypicalDeletedSources(), 0);
        expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(),
                model.getDeletedSources(), 0);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                String.format(ListCommand.MESSAGE_LIST_ALL_SUCCESS, 20 ), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        assertCommandSuccess(new ListCommand(), model, commandHistory,
                String.format(ListCommand.MESSAGE_LIST_ALL_SUCCESS, 20 ), expectedModel);
    }

    @Test
    public void execute_listPositiveN_showsTopN() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        ListCommand command = new ListCommand(Index.fromOneBased(2), true);
        expectedModel.updateFilteredSourceList(command.makePredicateForTopN(2));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_TOP_N_SUCCESS,
                2), expectedModel);
        assertEquals(Arrays.asList(ALGORITHM_RESEARCH, SENSOR_RESEARCH), model.getFilteredSourceList());
    }

    @Test
    public void execute_listPositiveNMoreThanTotal_showsTopN() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        ListCommand command = new ListCommand(Index.fromOneBased(25), true);
        expectedModel.updateFilteredSourceList(command.makePredicateForTopN(20));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_TOP_N_SUCCESS,
                20), expectedModel);
        assertEquals(getTypicalSources(), model.getFilteredSourceList());
    }

    @Test
    public void execute_listNegativeN_showsLastN() {
        ListCommand command = new ListCommand(Index.fromOneBased(2), false);
        expectedModel.updateFilteredSourceList(
                command.makePredicateForLastN(2, model.getFilteredSourceList().size()));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_LAST_N_SUCCESS,
                2), expectedModel);
        assertEquals(Arrays.asList(NAVAL_HISTORY_THREE, COMPUTER_ORGANISATION), model.getFilteredSourceList());
    }

    @Test
    public void execute_listNegativeNMoreThanTotal_showsTopN() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        ListCommand command = new ListCommand(Index.fromOneBased(25), false);
        expectedModel.updateFilteredSourceList(command.makePredicateForLastN(20,
                model.getFilteredSourceList().size() ));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_LAST_N_SUCCESS,
                20), expectedModel);
        assertEquals(getTypicalSources(), model.getFilteredSourceList());
    }

    @Test
    public void execute_listXandY_showsXandY() {
        ListCommand command = new ListCommand(Index.fromOneBased(3), Index.fromOneBased(5));
        expectedModel.updateFilteredSourceList(command.makePredicateForXToY(3, 5));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_X_TO_Y_SUCCESS,
                3, 5), expectedModel);
        assertEquals(Arrays.asList(SMART_COMPUTERS, VR_RESEARCH, AR_RESEARCH), model.getFilteredSourceList());
    }

    @Test
    public void execute_listSameXandY_showsX() {
        ListCommand command = new ListCommand(Index.fromOneBased(2), Index.fromOneBased(2));
        expectedModel.updateFilteredSourceList(command.makePredicateForXToY(2, 2));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_X_TO_Y_SUCCESS,
                2, 2), expectedModel);
        assertEquals(Arrays.asList(SENSOR_RESEARCH), model.getFilteredSourceList());
    }

    @Test
    public void execute_listXandYreverseRange_showsError() {
        ListCommand command = new ListCommand(Index.fromOneBased(5), Index.fromOneBased(3));
        assertCommandFailure(command, model, commandHistory,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
