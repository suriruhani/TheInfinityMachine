package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DeletedSources;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class EmptyBinCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyRecycleBin_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitDeletedSources();

        assertCommandSuccess(new EmptyBinCommand(), model, commandHistory,
                EmptyBinCommand.MESSAGE_EMPTY_BIN_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyRecycleBin_success() {
        Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(),
                getTypicalDeletedSources(), 0);
        Model expectedModel = new ModelManager(getTypicalSourceManager(), new UserPrefs(),
                getTypicalDeletedSources(), 0);
        expectedModel.setDeletedSources(new DeletedSources());
        expectedModel.commitDeletedSources();
        assertCommandSuccess(new EmptyBinCommand(), model, commandHistory,
                EmptyBinCommand.MESSAGE_EMPTY_BIN_SUCCESS, expectedModel);
    }
}
