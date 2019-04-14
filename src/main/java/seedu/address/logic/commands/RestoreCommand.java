package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.source.Source;

/**
 * Restores a source identified using it's displayed index from the recently deleted list of sources.
 */
public class RestoreCommand extends Command {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores a source identified by the index number used in the recently deleted source list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESTORE_DUPLICATE_SOURCE =
            "Since the same source exists in the Source Manager. It cannot be restored.\n"
            + "To remove it, use the empty-bin or delete command.\n"
            + "Refer to the help section to find out more about duplicate sources.\n";

    public static final String MESSAGE_RESTORE_SOURCE_SUCCESS = "Restored Source:\n---------------------"
            + "--------------\n%1$s";

    private final Index targetIndex;

    public RestoreCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Source> lastShownDeletedList = model.getFilteredSourceList();

        if (targetIndex.getZeroBased() >= lastShownDeletedList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        Source toRestore = lastShownDeletedList.get(targetIndex.getZeroBased());

        // To guard against conflicts rising from restoring a duplicate source
        // if the exact same source exists in source manager list, restoration is blocked.
        if (model.hasSource(toRestore)) {
            throw new CommandException(MESSAGE_RESTORE_DUPLICATE_SOURCE);
        }

        // add deleted source back to source manager list
        model.addSource(toRestore);
        model.commitSourceManager();

        // remove deleted source back from deleted sources list
        model.removeDeletedSource(toRestore);
        model.commitDeletedSources();
        return new CommandResult(String.format(MESSAGE_RESTORE_SOURCE_SUCCESS, toRestore));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestoreCommand // instanceof handles nulls
                && targetIndex.equals(((RestoreCommand) other).targetIndex)); // state check
    }
}
