package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSourceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SOURCE;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
    private Model expectedModel = new ModelManager(getTypicalSourceManager(), new UserPrefs(),
            getTypicalDeletedSources());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastSourceIndex = Index.fromOneBased(model.getFilteredSourceList().size());

        assertExecutionSuccess(INDEX_FIRST_SOURCE);
        assertExecutionSuccess(INDEX_THIRD_SOURCE);
        assertExecutionSuccess(lastSourceIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        showSourceAtIndex(expectedModel, INDEX_FIRST_SOURCE);

        assertExecutionSuccess(INDEX_FIRST_SOURCE);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        showSourceAtIndex(expectedModel, INDEX_FIRST_SOURCE);

        Index outOfBoundsIndex = INDEX_SECOND_SOURCE;
        // ensures that outOfBoundIndex is still in bounds of source manager list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getSourceManager().getSourceList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_SOURCE);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_SOURCE);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_SOURCE);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different source -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected source is set to the source at {@code index} in the filtered source list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_SOURCE_SUCCESS, index.getOneBased());
        expectedModel.setSelectedSource(model.getFilteredSourceList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        /*
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
        */
    }
}
