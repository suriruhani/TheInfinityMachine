package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PinnedSourcesCoordinationCenter;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.Source;

public class UnpinCommandTest {
    private Model testModelNoPinned = new ModelManager(
            getTypicalSourceManager(),
            new UserPrefs(),
            getTypicalDeletedSources(),
            0);
    private Model testModelWithPinned = new ModelManager(
            getTypicalSourceManager(),
            new UserPrefs(),
            getTypicalDeletedSources(),
            2);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_basicOperationUnpinSource_success() {
        int targetIndex = 1;

        Model expectedModel = new ModelManager(
                testModelWithPinned.getSourceManager(),
                new UserPrefs(),
                testModelWithPinned.getDeletedSources(),
                2);

        PinnedSourcesCoordinationCenter.decrementPinnedSources(expectedModel);
        int numPinnedSources = expectedModel.getNumberOfPinnedSources();

        // essentially an "order" command where the source is moved to the top of unpinned list
        Source sourceToUnpin = expectedModel.getFilteredSourceList().get(targetIndex - 1);
        expectedModel.deleteSource(sourceToUnpin);
        expectedModel.addSourceAtIndex(sourceToUnpin, numPinnedSources);
        // model is not committed because the unpin command is not undoable

        String expectedMessage = String.format(UnpinCommand.MESSAGE_SUCCESS, sourceToUnpin);

        assertCommandSuccess(
                new UnpinCommand(targetIndex),
                testModelWithPinned,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_unpinAlreadyUnpinnedSource_failure() {
        int targetIndex = 4;

        String expectedMessage = UnpinCommand.MESSAGE_SOURCE_NOT_PINNED_INVALID;

        assertCommandFailure(
                new UnpinCommand(targetIndex),
                testModelWithPinned,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_inputIndexZero_failure() {
        int targetIndex = 0;

        String expectedMessage = UnpinCommand.MESSAGE_SOURCE_INDEX_INVALID;

        assertCommandFailure(
                new UnpinCommand(targetIndex),
                testModelWithPinned,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_inputIndexOutOfBounds_failure() {
        int targetIndex = testModelWithPinned.getFilteredSourceList().size() + 1;

        String expectedMessage = UnpinCommand.MESSAGE_SOURCE_INDEX_INVALID;

        assertCommandFailure(
                new UnpinCommand(targetIndex),
                testModelWithPinned,
                commandHistory,
                expectedMessage);
    }
}
