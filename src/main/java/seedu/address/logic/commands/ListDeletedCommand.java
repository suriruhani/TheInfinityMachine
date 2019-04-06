package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

public class ListDeletedCommand extends Command {

    public static final String COMMAND_WORD = "list-deleted";

    public static final String MESSAGE_LIST_ALL_DELETED_SUCCESS = "Listed all deleted sources!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws IndexOutOfBoundsException {
        requireNonNull(model);
        model.switchToDeletedSources(); // sets delete sources data to list
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        return new CommandResult(MESSAGE_LIST_ALL_DELETED_SUCCESS);
    }
}
