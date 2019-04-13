package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ParserMode;
import seedu.address.model.UserPrefs;


public class CountCommandTest {

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
        expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(), model.getDeletedSources(), 0);
    }

    @Test
    public void executeCountSourceManager() {
        String actualMessage = String.format(CountCommand.MESSAGE_SUCCESS,
                model.getFilteredSourceList().size());
        String expectedMessage = String.format(CountCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredSourceList().size());
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void executeCountDeletedSources() {
        model.setParserMode(ParserMode.RECYCLE_BIN);
        expectedModel.setParserMode(ParserMode.RECYCLE_BIN);
        String actualMessage = String.format(CountCommand.MESSAGE_SUCCESS,
                model.getFilteredSourceList().size());
        String expectedMessage = String.format(CountCommand.MESSAGE_SUCCESS,
                expectedModel.getFilteredSourceList().size());
        assertEquals(expectedMessage, actualMessage);
    }

}
