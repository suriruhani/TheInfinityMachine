package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSourceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalSources.*;

import org.junit.Before;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ListCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

import java.util.Arrays;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
        expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(), model.getDeletedSources(), 0);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_LIST_ALL_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_LIST_ALL_SUCCESS,
                expectedModel);
    }

    @Ignore
    @Test
    public void execute_listZero_showsError() throws ParseException {
        //showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        //ListCommand command = new ListCommandParser().parse(" 0");
        //expectedModel.updateFilteredSourceList(command.makePredicateForTopN(2));
//        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_TOP_N_SUCCESS,
//                2), expectedModel);
        assertCommandFailure(new ListCommandParser().parse(" 0"), model, commandHistory, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        //assertEquals(Arrays.asList(ALGORITHM_RESEARCH, SENSOR_RESEARCH), model.getFilteredSourceList());
    }

    @Test
    public void execute_listPositiveN_showsTopN() throws ParseException {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        ListCommand command = new ListCommandParser().parse(" 2");
        expectedModel.updateFilteredSourceList(command.makePredicateForTopN(2));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_TOP_N_SUCCESS,
                2), expectedModel);
        assertEquals(Arrays.asList(ALGORITHM_RESEARCH, SENSOR_RESEARCH), model.getFilteredSourceList());
    }

    @Test
    public void execute_listNegativeN_showsLastN() throws ParseException {
        ListCommand command = new ListCommandParser().parse(" -2");
        expectedModel.updateFilteredSourceList(command.makePredicateForLastN(2, model.getFilteredSourceList().size()));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_LAST_N_SUCCESS,
                2), expectedModel);
        assertEquals(Arrays.asList(GAME_DEVELOPMENT, AI_RESEARCH), model.getFilteredSourceList());
    }

    @Test
    public void execute_listXY_showsXY() throws ParseException {
        ListCommand command = new ListCommandParser().parse(" 3 5");
        expectedModel.updateFilteredSourceList(command.makePredicateForXToY(3, 5));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_X_TO_Y_SUCCESS,
                3, 5), expectedModel);
        assertEquals(Arrays.asList(SMART_COMPUTERS, VR_RESEARCH, AR_RESEARCH), model.getFilteredSourceList());
    }

    @Test
    public void execute_listSameXY_showsX() throws ParseException {
        ListCommand command = new ListCommandParser().parse(" 2 2");
        expectedModel.updateFilteredSourceList(command.makePredicateForXToY(2, 2));
        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_X_TO_Y_SUCCESS,
                2, 2), expectedModel);
        assertEquals(Arrays.asList(SENSOR_RESEARCH), model.getFilteredSourceList());
    }

    @Ignore
    @Test
    public void execute_listXYreverseRange_showsError() throws ParseException {
        //showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        ListCommand command = new ListCommandParser().parse(" 5 3");
        //expectedModel.updateFilteredSourceList(command.makePredicateForTopN(2));
//        assertCommandSuccess(command, model, commandHistory, String.format(ListCommand.MESSAGE_LIST_TOP_N_SUCCESS,
//                2), expectedModel);
        assertCommandFailure(command, model, commandHistory, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        //assertEquals(Arrays.asList(ALGORITHM_RESEARCH, SENSOR_RESEARCH), model.getFilteredSourceList());
    }
}
