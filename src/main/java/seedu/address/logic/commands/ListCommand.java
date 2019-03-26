package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;

import seedu.address.logic.CommandHistory;

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

    private Index targetIndex = null;

    public ListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public ListCommand() {}

    /*
    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
    }
    */

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        /*
//        List<Source> lastShownList = model.getFilteredSourceList();
//
//        if (targetIndex.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
//        }
//
//        Source sourceToDelete = lastShownList.get(targetIndex.getZeroBased());
//        model.deleteSource(sourceToDelete);
//        model.commitSourceManager();
        */

        if (targetIndex != null) {
            return new CommandResult(String.format(MESSAGE_LIST_N_SUCCESS, targetIndex.getOneBased()));
        } else {
            return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && targetIndex.equals(((ListCommand) other).targetIndex)); // state check
    }
}
