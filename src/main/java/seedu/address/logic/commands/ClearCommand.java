package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DeletedSources;
import seedu.address.model.Model;
import seedu.address.model.SourceManager;
import seedu.address.model.source.Source;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setSourceManager(new SourceManager());
        model.setDeletedSources(new DeletedSources());
        model.commitSourceManager();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
