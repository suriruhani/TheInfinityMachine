package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ParserMode;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

public class ExitBinCommand extends Command {
    public static final String COMMAND_WORD = "exit-bin";

    public static final String MESSAGE_EMPTY_BIN_SUCCESS = "Exited Recycle Bin! \n Listed all sources.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setParserMode(ParserMode.SOURCE_MANAGER); //switch parser to recycle bin
        model.switchToSources(); // sets source manager data to list
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        return new CommandResult(MESSAGE_EMPTY_BIN_SUCCESS);
    }
}
