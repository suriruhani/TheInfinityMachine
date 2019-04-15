package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.PinnedSourcesCoordinationCenter;
import seedu.address.model.source.Source;

/**
 * Unpins a source
 */
public class UnpinCommand extends Command {
    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unpins a source from the list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Pinned source unpinned: %1$s";

    public static final String MESSAGE_SOURCE_NOT_PINNED_INVALID = "The source is not pinned.";
    public static final String MESSAGE_SOURCE_INDEX_INVALID = "The source index is invalid.";

    private final int targetIndex;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    public UnpinCommand(int targetIndex) {
        this.targetIndex = targetIndex - 1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        List<Source> completeSourceList = model.getFilteredSourceList();

        if (targetIndex >= completeSourceList.size() || targetIndex < 0) {
            logger.info("Index is invalid.");
            throw new CommandException(MESSAGE_SOURCE_INDEX_INVALID);
        }

        boolean isSourcePinned = PinnedSourcesCoordinationCenter.isPinnedSource(model, targetIndex);
        if (isSourcePinned == false) {
            logger.info("Source is not pinned.");
            throw new CommandException(MESSAGE_SOURCE_NOT_PINNED_INVALID);
        }

        PinnedSourcesCoordinationCenter.decrementPinnedSources(model);
        PinnedSourcesCoordinationCenter.saveCurrentPinnedSources(model);
        int numPinnedSources = model.getNumberOfPinnedSources();

        // essentially an "order" command where the source is moved to the top of unpinned list
        Source sourceToUnpin = completeSourceList.get(targetIndex);
        model.deleteSource(sourceToUnpin);
        sourceToUnpin.setPinnedState(false);
        model.addSourceAtIndex(sourceToUnpin, numPinnedSources);
        // model is not committed because the unpin command is not undoable

        return new CommandResult(String.format(MESSAGE_SUCCESS, sourceToUnpin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnpinCommand // instanceof handles nulls
                && targetIndex == (((UnpinCommand) other).targetIndex)); // state check
    }

    @Override
    public int hashCode() {
        return targetIndex;
    }
}
