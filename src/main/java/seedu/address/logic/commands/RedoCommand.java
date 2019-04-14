package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s infinity machine to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoSourceManager() && !model.canRedoDeletedSources()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        // if redo only interacts with source manager
        if (model.canRedoSourceManager() && !model.canRedoDeletedSources()) {
            model.redoSourceManager();
            model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        // if redo only interacts with delete source mode
        if (!model.canRedoSourceManager() && model.canRedoDeletedSources()) {
            model.redoDeletedSources();
            model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
            return new CommandResult(MESSAGE_SUCCESS);
        }

        model.redoSourceManager();
        model.redoDeletedSources();
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
