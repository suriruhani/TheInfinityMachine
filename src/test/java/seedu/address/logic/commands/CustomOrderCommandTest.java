package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.Source;

public class CustomOrderCommandTest {
    private Model testModel = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_basicOperationForwardSwap_success() {
        int sourceIndex = 3;
        int movePosition = 5;

        Model expectedModel = new ModelManager(testModel.getSourceManager(), new UserPrefs(),
                testModel.getDeletedSources());

        Source sourceToMove = testModel.getFilteredSourceList().get(sourceIndex - 1);
        expectedModel.deleteSource(sourceToMove);
        expectedModel.addSourceAtIndex(sourceToMove, movePosition - 1);
        expectedModel.commitSourceManager();

        String expectedMessage = String.format(CustomOrderCommand.MESSAGE_SUCCESS, sourceIndex, movePosition);

        assertCommandSuccess(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_basicOperationForwardSwapEdgeCase_success() {
        int sourceIndex = 1;
        int movePosition = testModel.getFilteredSourceList().size();

        Model expectedModel = new ModelManager(testModel.getSourceManager(), new UserPrefs(),
                testModel.getDeletedSources());

        Source sourceToMove = testModel.getFilteredSourceList().get(sourceIndex - 1);
        expectedModel.deleteSource(sourceToMove);
        expectedModel.addSourceAtIndex(sourceToMove, movePosition - 1);
        expectedModel.commitSourceManager();

        String expectedMessage = String.format(CustomOrderCommand.MESSAGE_SUCCESS, sourceIndex, movePosition);

        assertCommandSuccess(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_basicOperationBackwardSwap_success() {
        int sourceIndex = 5;
        int movePosition = 3;

        Model expectedModel = new ModelManager(testModel.getSourceManager(), new UserPrefs(),
                testModel.getDeletedSources());

        Source sourceToMove = testModel.getFilteredSourceList().get(sourceIndex - 1);
        expectedModel.deleteSource(sourceToMove);
        expectedModel.addSourceAtIndex(sourceToMove, movePosition - 1);
        expectedModel.commitSourceManager();

        String expectedMessage = String.format(CustomOrderCommand.MESSAGE_SUCCESS, sourceIndex, movePosition);

        assertCommandSuccess(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_basicOperationBackwardSwapEdgeCase_success() {
        int sourceIndex = testModel.getFilteredSourceList().size();
        int movePosition = 1;

        Model expectedModel = new ModelManager(testModel.getSourceManager(), new UserPrefs(),
                testModel.getDeletedSources());

        Source sourceToMove = testModel.getFilteredSourceList().get(sourceIndex - 1);
        expectedModel.deleteSource(sourceToMove);
        expectedModel.addSourceAtIndex(sourceToMove, movePosition - 1);
        expectedModel.commitSourceManager();

        String expectedMessage = String.format(CustomOrderCommand.MESSAGE_SUCCESS, sourceIndex, movePosition);

        assertCommandSuccess(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_sameInputParameters_failure() {
        int sourceIndex = 4;
        int movePosition = 4;

        String expectedMessage = CustomOrderCommand.MESSAGE_INDEX_IDENTICAL;

        assertCommandFailure(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_indexZero_failure() {
        int sourceIndex = 0;
        int movePosition = 4;

        String expectedMessage = CustomOrderCommand.MESSAGE_SOURCE_INDEX_INVALID;

        assertCommandFailure(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_indexOutOfBounds_failure() {
        int sourceIndex = testModel.getFilteredSourceList().size() + 1;
        int movePosition = 4;

        String expectedMessage = CustomOrderCommand.MESSAGE_SOURCE_INDEX_INVALID;

        assertCommandFailure(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_moveZero_failure() {
        int sourceIndex = 4;
        int movePosition = 0;

        String expectedMessage = CustomOrderCommand.MESSAGE_MOVE_POSITION_INVALID;

        assertCommandFailure(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_moveOutOfBounds_failure() {
        int sourceIndex = 4;
        int movePosition = testModel.getFilteredSourceList().size() + 1;

        String expectedMessage = CustomOrderCommand.MESSAGE_MOVE_POSITION_INVALID;

        assertCommandFailure(
                new CustomOrderCommand(sourceIndex, movePosition),
                testModel,
                commandHistory,
                expectedMessage);
    }
}
