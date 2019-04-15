package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ParserMode;
import seedu.address.model.PinnedSourcesCoordinationCenter;
import seedu.address.model.source.Source;

/**
 * Deletes a source identified using it's displayed index from the source manager.
 * Can also be used in recycle bin mode to delete sources permanently from the deleted source list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the source identified by the index number used in the displayed source list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DUPLICATE_SOURCE = "Since the same source exists in the Recycle Bin. "
            + "It will be deleted permanently.\nRefer to the help section to find out more about duplicate sources.\n";

    public static final String MESSAGE_DELETE_SOURCE_SUCCESS = "Deleted Source:\n---------------------"
            + "--------------\n%1$s";
    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Source> lastShownList = model.getFilteredSourceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        boolean isSourcePinned = PinnedSourcesCoordinationCenter.isPinnedSource(model, targetIndex.getZeroBased());
        if (isSourcePinned == true) {
            PinnedSourcesCoordinationCenter.decrementPinnedSources(model);
            PinnedSourcesCoordinationCenter.saveCurrentPinnedSources(model);
        }

        Source sourceToDelete = lastShownList.get(targetIndex.getZeroBased());

        // for recycle bin mode, deletes source from recycle bin permanently
        if (model.getParserMode() == ParserMode.RECYCLE_BIN) {
            model.removeDeletedSource(sourceToDelete);
            model.commitDeletedSources();
            return new CommandResult(String.format(MESSAGE_DELETE_SOURCE_SUCCESS, sourceToDelete));
        }

        // Permanently deletes the source from source manager list
        // if the exact same source exists in deleted source list.
        if (model.hasDeletedSource(sourceToDelete)) {
            model.deleteSource(sourceToDelete);
            model.commitSourceManager();
            return new CommandResult(String.format(MESSAGE_DUPLICATE_SOURCE,
                    MESSAGE_DELETE_SOURCE_SUCCESS,
                    sourceToDelete));
        }

        // remove source from source manager database
        model.deleteSource(sourceToDelete);
        model.commitSourceManager();

        // set pinned flag for source to be false before moving it to deleted sources database and removing it
        sourceToDelete.setPinnedState(false);

        // add deleted source to deleted sources database
        model.addDeletedSource(sourceToDelete);
        model.commitDeletedSources();

        return new CommandResult(String.format(MESSAGE_DELETE_SOURCE_SUCCESS, sourceToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
