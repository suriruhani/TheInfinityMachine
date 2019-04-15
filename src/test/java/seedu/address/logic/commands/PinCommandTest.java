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

public class PinCommandTest {
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
    private Model testModelWithMaxPinned = new ModelManager(
            getTypicalSourceManager(),
            new UserPrefs(),
            getTypicalDeletedSources(),
            PinCommand.PINNED_LIMIT);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_basicOperationPinSource_success() {
        int targetIndex = 2;

        Model expectedModel = new ModelManager(
                testModelNoPinned.getSourceManager(),
                new UserPrefs(),
                testModelNoPinned.getDeletedSources(),
                0);

        Source sourceToPin = testModelNoPinned.getFilteredSourceList().get(targetIndex - 1);
        expectedModel.deleteSource(sourceToPin);
        expectedModel.addSourceAtIndex(sourceToPin, 0);

        PinnedSourcesCoordinationCenter.incrementPinnedSources(expectedModel);

        String expectedMessage = String.format(PinCommand.MESSAGE_SUCCESS, sourceToPin);

        assertCommandSuccess(
                new PinCommand(targetIndex),
                testModelNoPinned,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void execute_pinAlreadyPinnedSource_failure() {
        int targetIndex = 1;

        String expectedMessage = PinCommand.MESSAGE_SOURCE_PINNED_INVALID;

        assertCommandFailure(
                new PinCommand(targetIndex),
                testModelWithPinned,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_inputIndexZero_failure() {
        int targetIndex = 0;

        String expectedMessage = PinCommand.MESSAGE_SOURCE_INDEX_INVALID;

        assertCommandFailure(
                new PinCommand(targetIndex),
                testModelWithPinned,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_inputIndexOutOfBounds_failure() {
        int targetIndex = testModelWithPinned.getFilteredSourceList().size() + 1;

        String expectedMessage = PinCommand.MESSAGE_SOURCE_INDEX_INVALID;

        assertCommandFailure(
                new PinCommand(targetIndex),
                testModelWithPinned,
                commandHistory,
                expectedMessage);
    }

    @Test
    public void execute_exceedMaxPinnedSources_failure() {
        int targetIndex = PinCommand.PINNED_LIMIT + 1;

        String expectedMessage = PinCommand.MESSAGE_MAX_PINNED_INVALID;

        assertCommandFailure(
                new PinCommand(targetIndex),
                testModelWithMaxPinned,
                commandHistory,
                expectedMessage);
    }
}
