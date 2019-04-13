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
public class BiblioEditCommandTest {

    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexValidChange_success() {
        BiblioEditCommand biblioEditCommand = new BiblioEditCommand(INDEX_FIRST_SOURCE, "City", "London");

        try {
            String expectedMessage = new BiblioEditCommand(INDEX_FIRST_SOURCE, "City", "London")
                    .execute(new ModelManager(getTypicalSourceManager(), new UserPrefs(),
                            getTypicalDeletedSources()), new CommandHistory())
                    .getFeedbackToUser();
            Model expectedModel = model;
            assertCommandSuccess(biblioEditCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (CommandException ce) {
            assert false : "CommandException thrown";
        }
    }

    @Test
    public void execute_validIndexInvalidChange_failure() {
        BiblioEditCommand biblioEditCommand = new BiblioEditCommand(INDEX_FIRST_SOURCE, "Foo", "Foo");
        assertCommandFailure(biblioEditCommand, model, commandHistory, BiblioEditCommand.MESSAGE_BIBLIO_EDIT_FAILURE);
    }

    @Test
    public void execute_invalidIndexValidChange_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        BiblioEditCommand biblioEditCommand = new BiblioEditCommand(outOfBoundIndex, "City", "London");
        assertCommandFailure(biblioEditCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexInvalidChange_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        BiblioEditCommand biblioEditCommand = new BiblioEditCommand(outOfBoundIndex, "Foo", "Foo");
        assertCommandFailure(biblioEditCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }
}
