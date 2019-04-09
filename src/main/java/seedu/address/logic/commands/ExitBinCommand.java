package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ParserMode;

/**
 * Exits the recycle bin mode.
 */
public class ExitBinCommand extends Command {
    public static final String COMMAND_WORD = "exit-bin";

    public static final String MESSAGE_EXIT_BIN_SUCCESS = "Exited Recycle Bin! \nListed all sources.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setParserMode(ParserMode.SOURCE_MANAGER); // switch parser mode to source manager
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        return new CommandResult(MESSAGE_EXIT_BIN_SUCCESS);
    }
}
