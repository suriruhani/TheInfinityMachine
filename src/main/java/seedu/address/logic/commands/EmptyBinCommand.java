package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DeletedSources;
import seedu.address.model.Model;

/**
 * Clears sources in the recycle bin.
 */
public class EmptyBinCommand extends Command {
    public static final String COMMAND_WORD = "empty-bin";

    public static final String MESSAGE_EMPTY_BIN_SUCCESS = "Recycle Bin has been emptied!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setDeletedSources(new DeletedSources());
        model.commitDeletedSources();
        return new CommandResult(MESSAGE_EMPTY_BIN_SUCCESS);
    }
}
