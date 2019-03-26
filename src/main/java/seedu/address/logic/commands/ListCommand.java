package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all sources in the Source Database to the user, or the top N sources where N may
 * be an optional argument supplied by the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all the sources in the database to the user, or optionally, the top N sources if"
            + " an argument is supplied.\n"
            + "Parameters: [INDEX] (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 20";

    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all sources!";

    public static final String MESSAGE_LIST_N_SUCCESS = "Listed top %d sources!";

    private final Index targetIndex;

    public ListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
    }
}
