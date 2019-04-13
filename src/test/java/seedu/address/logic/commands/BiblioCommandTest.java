package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code BiblioCommand}.
 */
public class BiblioCommandTest {

    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexValidStyle_success() {
        BiblioCommand biblioCommand = new BiblioCommand(INDEX_FIRST_SOURCE, "APA");

        try {
            String expectedMessage = new BiblioCommand(INDEX_FIRST_SOURCE, "APA")
                    .execute(new ModelManager(getTypicalSourceManager(), new UserPrefs(),
                                              getTypicalDeletedSources()), new CommandHistory())
                    .getFeedbackToUser();
            ModelManager expectedModel = new ModelManager(model.getSourceManager(),
                                                          new UserPrefs(), getTypicalDeletedSources());
            assertCommandSuccess(biblioCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (CommandException ce) {
            assert false : "CommandException thrown";
        }
    }

    @Test
    public void execute_validIndexInvalidStyle_failure() {
        BiblioCommand biblioCommand = new BiblioCommand(INDEX_FIRST_SOURCE, "Foo");
        assertCommandFailure(biblioCommand, model, commandHistory, BiblioCommand.MESSAGE_UNSUPPORTED_FORMAT);
    }

    @Test
    public void execute_invalidIndexValidStyle_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        BiblioCommand biblioCommand = new BiblioCommand(outOfBoundIndex, "APA");
        assertCommandFailure(biblioCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexInvalidStyle_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        BiblioCommand biblioCommand = new BiblioCommand(outOfBoundIndex, "Foo");
        assertCommandFailure(biblioCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_mlaStyle_success() {
        int maxIndex = model.getFilteredSourceList().size();
        for (int i = 0; i < maxIndex; i++) {
            Index currentIndex = Index.fromZeroBased(i);
            execute_singleSource_success(currentIndex, "MLA");
        }
    }

    @Test
    public void execute_apaStyle_success() {
        int maxIndex = model.getFilteredSourceList().size();
        for (int i = 0; i < maxIndex; i++) {
            Index currentIndex = Index.fromZeroBased(i);
            execute_singleSource_success(currentIndex, "APA");
        }
    }

    /**
     * Utility for creating a biblio for a single source.
     * @param index Index for source
     * @param format Format for biblio, must be "APA" or "MLA"
     */
    private void execute_singleSource_success(Index index, String format) {
        BiblioCommand biblioCommand = new BiblioCommand(index, format);

        try {
            String expectedMessage = new BiblioCommand(index, format)
                    .execute(new ModelManager(getTypicalSourceManager(), new UserPrefs(),
                            getTypicalDeletedSources()), new CommandHistory())
                    .getFeedbackToUser();
            ModelManager expectedModel = new ModelManager(model.getSourceManager(),
                    new UserPrefs(), getTypicalDeletedSources());
            assertCommandSuccess(biblioCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (CommandException ce) {
            assert false : "CommandException thrown";
        }
    }
}
