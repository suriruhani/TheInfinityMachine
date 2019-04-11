package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_SOURCES_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalSources.SENSOR_RESEARCH;
import static seedu.address.testutil.TypicalSources.SMART_COMPUTERS;
import static seedu.address.testutil.TypicalSources.VR_RESEARCH;
import static seedu.address.testutil.TypicalSources.KEYWORD_MATCHING_EXPERIMENT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;


public class SearchCommandSystemTest extends SourceManagerSystemTest {

    @Test
    public void search() {
        /* Case: search multiple sources in source manager, command with leading spaces and trailing spaces
         * -> 2 sources found
         */
        String command = "   " + SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_EXPERIMENT + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SENSOR_RESEARCH, VR_RESEARCH); //first titles of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where source list is displaying the sources we are searching
         * -> 2 sources found
         */
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_EXPERIMENT;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search source where source list is not displaying the source we are searching -> 1 source found */
        command = SearchCommand.COMMAND_WORD + " Carl";
        ModelHelper.setFilteredList(expectedModel, SMART_COMPUTERS);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple sources in source manager, 2 keywords -> 2 sources found */
        command = SearchCommand.COMMAND_WORD + " Benson Daniel";
        ModelHelper.setFilteredList(expectedModel, SENSOR_RESEARCH, VR_RESEARCH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple sources in source manager, 2 keywords in reversed order -> 2 sources found */
        command = SearchCommand.COMMAND_WORD + " Daniel Benson";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple sources in source manager, 2 keywords with 1 repeat -> 2 sources found */
        command = SearchCommand.COMMAND_WORD + " Daniel Benson Daniel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search multiple sources in source manager, 2 matching keywords and 1 non-matching keyword
         * -> 2 sources found
         */
        command = SearchCommand.COMMAND_WORD + " Daniel Benson NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous search command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous search command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: search same sources in source manager after deleting 1 of them -> 1 source found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getSourceManager().getSourceList().contains(SENSOR_RESEARCH));
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_EXPERIMENT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, VR_RESEARCH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search source in source manager,
        keyword is same as name but of different case -> 1 source found */
        command = SearchCommand.COMMAND_WORD + " MeIeR";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search source in source manager, keyword is substring of name -> 0 sources found */
        command = SearchCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search source in source manager, name is substring of keyword -> 0 sources found */
        command = SearchCommand.COMMAND_WORD + " Meiers";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search source not in source manager -> 0 sources found */
        command = SearchCommand.COMMAND_WORD + " Mark";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search address of source in source manager -> 0 sources found */
        command = SearchCommand.COMMAND_WORD + " " + VR_RESEARCH.getType().type;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search email of source in source manager -> 0 sources found */
        command = SearchCommand.COMMAND_WORD + " " + VR_RESEARCH.getDetail().detail;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search tags of source in source manager -> 0 sources found */
        List<Tag> tags = new ArrayList<>(VR_RESEARCH.getTags());
        command = SearchCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search while a source is selected -> selected card deselected */
        showAllSources();
        selectSource(Index.fromOneBased(1));
        assertFalse(getSourceListPanel().getHandleToSelectedCard().getTitle().equals(VR_RESEARCH.getTitle().title));
        command = SearchCommand.COMMAND_WORD + " Daniel";
        ModelHelper.setFilteredList(expectedModel, VR_RESEARCH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: search source in empty source manager -> 0 sources found */
        deleteAllSources();
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_EXPERIMENT;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, VR_RESEARCH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "search Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_SOURCES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code SourceManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see SourceManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {

        String expectedResultMessage = String.format(
                MESSAGE_SOURCES_LISTED_OVERVIEW, expectedModel.getFilteredSourceList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();

    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code SourceManagerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
