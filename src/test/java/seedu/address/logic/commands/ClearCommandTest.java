package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DeletedSources;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SourceManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitSourceManager();
        expectedModel.commitDeletedSources();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
        Model expected = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(),0);
        expected.setSourceManager(new SourceManager());
        expected.setDeletedSources(new DeletedSources());
        expected.commitSourceManager();
        expected.commitDeletedSources();
        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expected);
    }

}
