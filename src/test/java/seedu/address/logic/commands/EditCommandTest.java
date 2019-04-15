package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSourceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SOURCE;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditSourceDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SourceManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.Source;
import seedu.address.testutil.EditSourceDescriptorBuilder;
import seedu.address.testutil.SourceBuilder;
import seedu.address.testutil.TypicalSources;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Source editedSource = TypicalSources.getNonDefaultSource();
        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder(editedSource).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SOURCE, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SOURCE_SUCCESS, editedSource);

        Model expectedModel = new ModelManager(new SourceManager(model.getSourceManager()), new UserPrefs(),
                model.getDeletedSources());

        Source sourceToReplace = expectedModel.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());

        expectedModel.setSource(sourceToReplace, editedSource);
        expectedModel.commitSourceManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastSource = Index.fromOneBased(model.getFilteredSourceList().size());
        Source lastSource = model.getFilteredSourceList().get(indexLastSource.getZeroBased());

        SourceBuilder sourceInList = new SourceBuilder(lastSource);
        Source editedSource = sourceInList.withTitle(VALID_TITLE_NETWORK).withType(VALID_TYPE_NETWORK)
                .withTags(VALID_TAG_FOO).build();

        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder().withTitle(VALID_TITLE_NETWORK)
                .withType(VALID_TYPE_NETWORK).withTags(VALID_TAG_FOO).build();
        EditCommand editCommand = new EditCommand(indexLastSource, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SOURCE_SUCCESS, editedSource);

        Model expectedModel = new ModelManager(new SourceManager(model.getSourceManager()), new UserPrefs(),
                model.getDeletedSources());
        expectedModel.setSource(lastSource, editedSource);
        expectedModel.commitSourceManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SOURCE, new EditSourceDescriptor());
        Source editedSource = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SOURCE_SUCCESS, editedSource);

        Model expectedModel = new ModelManager(new SourceManager(model.getSourceManager()), new UserPrefs(),
                model.getDeletedSources());
        expectedModel.commitSourceManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);

        Source sourceInFilteredList = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        Source editedSource = new SourceBuilder(sourceInFilteredList).withTitle(VALID_TITLE_NETWORK).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SOURCE,
                new EditSourceDescriptorBuilder().withTitle(VALID_TITLE_NETWORK).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_SOURCE_SUCCESS, editedSource);

        Model expectedModel = new ModelManager(new SourceManager(model.getSourceManager()), new UserPrefs(),
                model.getDeletedSources());
        expectedModel.setSource(model.getFilteredSourceList().get(0), editedSource);
        expectedModel.commitSourceManager();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateSourceUnfilteredList_failure() {
        Source firstSource = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder(firstSource).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_SOURCE, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_SOURCE);
    }

    @Test
    public void execute_duplicateSourceFilteredList_failure() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);

        // edit source in filtered list into a duplicate in source manager
        Source sourceInList = model.getSourceManager().getSourceList().get(INDEX_SECOND_SOURCE.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SOURCE,
                new EditSourceDescriptorBuilder(sourceInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_SOURCE);
    }

    @Test
    public void execute_invalidSourceIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        EditSourceDescriptor descriptor =
                new EditSourceDescriptorBuilder().withTitle(VALID_TITLE_NETWORK).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of source manager
     */
    @Test
    public void execute_invalidSourceIndexFilteredList_failure() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);
        Index outOfBoundIndex = INDEX_SECOND_SOURCE;
        // ensures that outOfBoundIndex is still in bounds of source manager list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSourceManager().getSourceList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditSourceDescriptorBuilder().withTitle(VALID_TITLE_NETWORK).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Source editedSource = new SourceBuilder().build();
        Source sourceToEdit = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder(editedSource).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SOURCE, descriptor);
        Model expectedModel = new ModelManager(new SourceManager(model.getSourceManager()), new UserPrefs(),
                model.getDeletedSources());
        expectedModel.setSource(sourceToEdit, editedSource);
        expectedModel.commitSourceManager();

        // edit -> first source edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts sourceManager back to previous state and filtered source list to show all sources
        expectedModel.undoSourceManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first source edited again
        expectedModel.redoSourceManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        EditSourceDescriptor descriptor =
                new EditSourceDescriptorBuilder().withTitle(VALID_TITLE_NETWORK).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> source manager state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);

        // single source manager state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Source} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited source in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the source object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameSourceEdited() throws Exception {
        Source editedSource = new SourceBuilder().build();
        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder(editedSource).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_SOURCE, descriptor);
        Model expectedModel = new ModelManager(new SourceManager(model.getSourceManager()), new UserPrefs(),
                model.getDeletedSources());
        showSourceAtIndex(model, INDEX_SECOND_SOURCE);
        Source sourceToEdit = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        expectedModel.setSource(sourceToEdit, editedSource);
        expectedModel.updateFilteredSourceList(Model.PREDICATE_SHOW_ALL_SOURCES);
        expectedModel.commitSourceManager();

        // edit -> edits second source in unfiltered source list / first source in filtered source list
        editCommand.execute(model, commandHistory);

        // undo -> reverts sourceManager back to previous state and filtered source list to show all sources
        expectedModel.undoSourceManager();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased()), sourceToEdit);
        // redo -> edits same second source in unfiltered source list
        expectedModel.redoSourceManager();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_SOURCE, DESC_ENGINEERING);

        // same values -> returns true
        EditSourceDescriptor copyDescriptor = new EditSourceDescriptor(DESC_ENGINEERING);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_SOURCE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_SOURCE, DESC_ENGINEERING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_SOURCE, DESC_NETWORK)));
    }

}
