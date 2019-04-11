package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.Source;
import seedu.address.testutil.SourceBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
    }

    @Test
    public void execute_newSource_success() {
        Source validSource = new SourceBuilder().build();

        Model expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(), model.getDeletedSources());
        expectedModel.addSource(validSource);
        expectedModel.commitSourceManager();

        assertCommandSuccess(new AddCommand(validSource), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validSource), expectedModel);
    }

    @Test
    public void execute_duplicateSource_throwsCommandException() {
        Source sourceInList = model.getSourceManager().getSourceList().get(0);
        assertCommandFailure(new AddCommand(sourceInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_SOURCE);
    }

}
