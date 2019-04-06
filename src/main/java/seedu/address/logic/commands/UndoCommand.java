package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DELETED_SOURCES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s infinity machine to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoSourceManager()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        if (!model.canUndoSourceManager()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoSourceManager();
        model.undoDeletedSources();
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        model.updateFilteredDeletedSourceList(PREDICATE_SHOW_ALL_DELETED_SOURCES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
