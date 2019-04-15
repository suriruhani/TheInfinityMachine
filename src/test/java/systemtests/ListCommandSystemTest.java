package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_SOURCES_LISTED_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.TypicalSources.AI_RESEARCH;
import static seedu.address.testutil.TypicalSources.ALGORITHM_RESEARCH;
import static seedu.address.testutil.TypicalSources.AR_RESEARCH;
import static seedu.address.testutil.TypicalSources.SENSOR_RESEARCH;
import static seedu.address.testutil.TypicalSources.SMART_COMPUTERS;
import static seedu.address.testutil.TypicalSources.TITLE_PREFIX_AI;
import static seedu.address.testutil.TypicalSources.TITLE_PREFIX_RESEARCH;
import static seedu.address.testutil.TypicalSources.TITLE_PREFIX_SMARTCOMP;
import static seedu.address.testutil.TypicalSources.VR_RESEARCH;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.Model;

public class ListCommandSystemTest extends SourceManagerSystemTest {

    @Ignore
    @Test
    public void list() {
        /* Case: search multiple sources in source manager, command with leading spaces and trailing spaces
         * -> 2 sources found
         */
        String command = SearchCommand.COMMAND_WORD + TITLE_PREFIX_RESEARCH;
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALGORITHM_RESEARCH, SENSOR_RESEARCH, VR_RESEARCH,
                AR_RESEARCH, AI_RESEARCH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous search command where source list is displaying the sources we are searching
         * -> 2 sources found
         */
        command = SearchCommand.COMMAND_WORD + TITLE_PREFIX_RESEARCH;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search source where source list is not displaying the source we are searching -> 1 source found */
        command = SearchCommand.COMMAND_WORD + TITLE_PREFIX_SMARTCOMP;
        ModelHelper.setFilteredList(expectedModel, SMART_COMPUTERS);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search same sources in source manager after deleting 1 of them -> 1 source found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getSourceManager().getSourceList().contains(ALGORITHM_RESEARCH));
        command = SearchCommand.COMMAND_WORD + TITLE_PREFIX_AI;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, AI_RESEARCH);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: search source in source manager,
        keyword is same as name but of different case -> 1 source found */
        command = SearchCommand.COMMAND_WORD + " " + PREFIX_TITLE + "ARTificiAL inTELLIgenCE ";
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
