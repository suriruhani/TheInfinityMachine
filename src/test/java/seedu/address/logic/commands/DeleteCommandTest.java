package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSourceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SOURCE;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.Source;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Source sourceToDelete = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SOURCE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SOURCE_SUCCESS, sourceToDelete);

        ModelManager expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(),
                model.getDeletedSources());

        // add deleted source to deleted sources database
        expectedModel.addDeletedSource(sourceToDelete);
        expectedModel.commitDeletedSources();

        // remove source from source manager database
        expectedModel.deleteSource(sourceToDelete);
        expectedModel.commitSourceManager();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);

        Source sourceToDelete = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SOURCE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_SOURCE_SUCCESS, sourceToDelete);

        ModelManager expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(),
                model.getDeletedSources());

        // add deleted source to deleted sources database
        expectedModel.addDeletedSource(sourceToDelete);
        expectedModel.commitDeletedSources();

        // remove source from source manager database
        expectedModel.deleteSource(sourceToDelete);
        expectedModel.commitSourceManager();

        showNoSource(expectedModel);
        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);

        Index outOfBoundIndex = INDEX_SECOND_SOURCE;
        // ensures that outOfBoundIndex is still in bounds of source manager list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSourceManager().getSourceList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Source sourceToDelete = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SOURCE);
        Model expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(), model.getDeletedSources());

        // add deleted source to deleted sources database
        expectedModel.addDeletedSource(sourceToDelete);
        expectedModel.commitDeletedSources();

        // remove source from source manager database
        expectedModel.deleteSource(sourceToDelete);
        expectedModel.commitSourceManager();

        // delete -> first source deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts sourceManager back to previous state and filtered source list to show all sources
        expectedModel.undoSourceManager();
        expectedModel.undoDeletedSources();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first source deleted again
        expectedModel.redoSourceManager();
        expectedModel.redoDeletedSources();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> source manager state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);

        // single source manager state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Source} from a filtered list
     * 2. Adds the source to the deleted sources list.
     * 3. Undo the deletion.
     * 4. The unfiltered list should be shown now. Verify that the index of the previously deleted source in the
     * unfiltered list is different from the index at the filtered list.
     * 5. Redo the deletion. This ensures {@code RedoCommand} deletes the source object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameSourceDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_SOURCE);
        Model expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(), model.getDeletedSources());

        showSourceAtIndex(model, INDEX_SECOND_SOURCE);
        Source sourceToDelete = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());

        // add deleted source to deleted sources database
        expectedModel.addDeletedSource(sourceToDelete);
        expectedModel.commitDeletedSources();

        // remove source from source manager database
        expectedModel.deleteSource(sourceToDelete);
        expectedModel.commitSourceManager();

        // delete -> deletes second source in unfiltered source list / first source in filtered source list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts sourceManager back to previous state and filtered source list to show all sources
        expectedModel.undoSourceManager();
        expectedModel.undoDeletedSources();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(sourceToDelete, model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased()));
        // redo -> deletes same second source in unfiltered source list
        expectedModel.redoSourceManager();
        expectedModel.redoDeletedSources();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_SOURCE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_SOURCE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_SOURCE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different source -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no sources.
     */
    private void showNoSource(Model model) {
        model.updateFilteredSourceList(p -> false);

        assertTrue(model.getFilteredSourceList().isEmpty());
    }
}
