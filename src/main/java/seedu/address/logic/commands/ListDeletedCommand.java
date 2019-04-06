package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELETED_SOURCES;

public class ListDeletedCommand extends Command {

    public static final String COMMAND_WORD = "list-deleted";

    public static final String MESSAGE_LIST_ALL_DELETED_SUCCESS = "Listed all deleted sources!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws IndexOutOfBoundsException {
        requireNonNull(model);
        //shortcut to obtain the entire list of all sources by first displaying an unfiltered list
        model.updateFilteredDeletedSourceList(PREDICATE_SHOW_ALL_DELETED_SOURCES);
        return new CommandResult(MESSAGE_LIST_ALL_DELETED_SUCCESS);
    }
}
