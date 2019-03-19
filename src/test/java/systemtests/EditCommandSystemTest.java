package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FOO;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SOURCE;
import static seedu.address.testutil.TypicalSources.ALICE;
import static seedu.address.testutil.TypicalSources.BENSON;
import static seedu.address.testutil.TypicalSources.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.SourceBuilder;
import seedu.address.testutil.SourceUtil;

public class EditCommandSystemTest extends SourceManagerSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_SOURCE;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + TITLE_DESC_BOB + "  "
                + " " + TYPE_DESC_BOB + "  " + DETAIL_DESC_BOB + " " + TAG_DESC_BAR + " ";
        Source editedSource = new SourceBuilder(BENSON).withTags(VALID_TAG_BAR).build();
        assertCommandSuccess(command, index, editedSource);

        /* Case: undo editing the last source in the list -> last source restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last source in the list -> last source edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.setSource(getModel().getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased()), editedSource);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a source with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + TYPE_DESC_BOB
                + DETAIL_DESC_BOB + TAG_DESC_FOO + TAG_DESC_BAR;
        assertCommandSuccess(command, index, BENSON);

        /* Case: edit a source with new values same as another source's values but with different title -> edited */
        assertTrue(getModel().getSourceManager().getSourceList().contains(BENSON));
        index = INDEX_SECOND_SOURCE;
        assertNotEquals(getModel().getFilteredSourceList().get(index.getZeroBased()), BENSON);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_AMY + TYPE_DESC_BOB
                + DETAIL_DESC_BOB + TAG_DESC_FOO + TAG_DESC_BAR;
        editedSource = new SourceBuilder(BENSON).withTitle(VALID_TITLE_AMY).build();
        assertCommandSuccess(command, index, editedSource);

        /* Case: edit a source with new values same as another source's values but with different type
         * -> edited
         */
        index = INDEX_SECOND_SOURCE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + TYPE_DESC_AMY
                + DETAIL_DESC_BOB + TAG_DESC_FOO + TAG_DESC_BAR;
        editedSource = new SourceBuilder(BENSON).withType(VALID_TYPE_AMY).build();
        assertCommandSuccess(command, index, editedSource);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_SOURCE;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Source sourceToEdit = getModel().getFilteredSourceList().get(index.getZeroBased());
        editedSource = new SourceBuilder(sourceToEdit).withTags().build();
        assertCommandSuccess(command, index, editedSource);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered source list, edit index within bounds of source manager and source list -> edited */
        showSourcesWithTitle(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_SOURCE;
        assertTrue(index.getZeroBased() < getModel().getFilteredSourceList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TITLE_DESC_BOB;
        sourceToEdit = getModel().getFilteredSourceList().get(index.getZeroBased());
        editedSource = new SourceBuilder(sourceToEdit).withTitle(VALID_TITLE_BOB).build();
        assertCommandSuccess(command, index, editedSource);

        /* Case: filtered source list, edit index within bounds of source manager but out of bounds of source list
         * -> rejected
         */
        showSourcesWithTitle(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getSourceManager().getSourceList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_BOB,
                Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a source card is selected -------------------------- */

        /* Case: selects first card in the source list, edit a source -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllSources();
        index = INDEX_FIRST_SOURCE;
        selectSource(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_AMY + TYPE_DESC_AMY
                + DETAIL_DESC_AMY + TAG_DESC_FOO;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new source's name
        assertCommandSuccess(command, index, ALICE, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + TITLE_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + TITLE_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredSourceList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + TITLE_DESC_BOB,
                Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + TITLE_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid title -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased() + INVALID_TITLE_DESC,
                Title.MESSAGE_CONSTRAINTS);

        /* Case: invalid type -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased() + INVALID_TYPE_DESC,
                Type.MESSAGE_CONSTRAINTS);

        /* Case: invalid detail -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased() + INVALID_DETAIL_DESC,
                Detail.MESSAGE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_CONSTRAINTS);

        /* Case: edit a source with new values same as another source's values -> rejected */
        executeCommand(SourceUtil.getAddCommand(BENSON));
        assertTrue(getModel().getSourceManager().getSourceList().contains(BENSON));
        index = INDEX_FIRST_SOURCE;
        assertFalse(getModel().getFilteredSourceList().get(index.getZeroBased()).equals(BENSON));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + TYPE_DESC_BOB
                + DETAIL_DESC_BOB + TAG_DESC_FOO + TAG_DESC_BAR;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_SOURCE);

        /* Case: edit a source with new values same as another source's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + TYPE_DESC_BOB
                + DETAIL_DESC_BOB + TAG_DESC_BAR;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_SOURCE);

        /* Case: edit a source with new values same as another source's values but with different detail -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + TYPE_DESC_BOB
                + DETAIL_DESC_AMY + TAG_DESC_FOO + TAG_DESC_BAR;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_SOURCE);

        /* Case: edit a source with new values same as another source's values but with different type -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + TITLE_DESC_BOB + TYPE_DESC_AMY
                + DETAIL_DESC_BOB + TAG_DESC_FOO + TAG_DESC_BAR;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_SOURCE);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Source, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Source, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Source editedSource) {
        assertCommandSuccess(command, toEdit, editedSource, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the source at index {@code toEdit} being
     * updated to values specified {@code editedSource}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Source editedSource,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.setSource(expectedModel.getFilteredSourceList().get(toEdit.getZeroBased()), editedSource);
        expectedModel.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_SOURCE_SUCCESS, editedSource), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code SourceManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see SourceManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see SourceManagerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code SourceManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see SourceManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
