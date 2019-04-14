package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ParserMode;
import seedu.address.model.UserPrefs;


public class CountCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
        expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(), model.getDeletedSources(), 0);
    }

    @Test
    public void executeCountSourceManager() {
        String expectedMessage = String.format(CountCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredSourceList().size());
        assertCommandSuccess(new CountCommand(), model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeCountDeletedSources() {
        model.setParserMode(ParserMode.RECYCLE_BIN);
        expectedModel.setParserMode(ParserMode.RECYCLE_BIN);
        String expectedMessage = String.format(CountCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredSourceList().size());
        assertCommandSuccess(new CountCommand(), model, commandHistory, expectedMessage, expectedModel);
    }

}
