package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ParserMode;

/**
 * Switches from Source Manager to Recycle Bin mode.
 * Lists all deleted sources in the Deleted Sources database to the user.
 */
public class RecycleBinCommand extends Command {

    public static final String COMMAND_WORD = "recycle-bin";

    public static final String MESSAGE_LIST_ALL_DELETED_SUCCESS = "Switched to Recycle Bin."
            + "\nListed all deleted sources!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setParserMode(ParserMode.RECYCLE_BIN); //switch parser to recycle bin
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        return new CommandResult(MESSAGE_LIST_ALL_DELETED_SUCCESS);
    }
}
