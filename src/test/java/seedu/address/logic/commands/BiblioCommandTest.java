package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.Assert;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code BiblioCommand}.
 */
public class BiblioCommandTest {

    private Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validStyle_validIndex_success() {
        BiblioCommand biblioCommand = new BiblioCommand("APA", INDEX_FIRST_SOURCE);

        try {
            String expectedMessage = new BiblioCommand("APA", INDEX_FIRST_SOURCE)
                    .execute(new ModelManager(getTypicalSourceManager(), new UserPrefs()), new CommandHistory())
                    .getFeedbackToUser();
            ModelManager expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs());
            assertCommandSuccess(biblioCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch(CommandException ce){
            assert false: "CommandException thrown";
        }
    }

    @Test
    public void execute_invalidStyle_validIndex_failure() {
        BiblioCommand biblioCommand = new BiblioCommand("Foo", INDEX_FIRST_SOURCE);
        assertCommandFailure(biblioCommand, model, commandHistory, Messages.MESSAGE_INVALID_COMMAND_FORMAT);
    }

    @Test
    public void execute_validStyle_invalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        BiblioCommand biblioCommand = new BiblioCommand("APA", outOfBoundIndex);
        assertCommandFailure(biblioCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidStyle_invalidIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        BiblioCommand biblioCommand = new BiblioCommand("Foo", outOfBoundIndex);
        assertCommandFailure(biblioCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }
}
