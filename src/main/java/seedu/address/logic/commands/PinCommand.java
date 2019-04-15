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
 * Pins a source
 */
public class PinCommand extends Command {
    public static final String COMMAND_WORD = "pin";

    public static final int PINNED_LIMIT = 5;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the source identified by the index number used in the displayed source list to the top.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Pinned source: %1$s";

    public static final String MESSAGE_SOURCE_PINNED_INVALID = "The source is already pinned.";
    public static final String MESSAGE_SOURCE_INDEX_INVALID = "The source index is invalid.";
    public static final String MESSAGE_MAX_PINNED_INVALID =
            "You have reached the maximum number of pinned sources. (" + PINNED_LIMIT + ")";

    private final int targetIndex;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    public PinCommand(int targetIndex) {
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
        if (isSourcePinned == true) {
            logger.info("Source is already pinned.");
            throw new CommandException(MESSAGE_SOURCE_PINNED_INVALID);
        }

        if ((model.getNumberOfPinnedSources() + 1) > PINNED_LIMIT) {
            logger.info("Max pinned sources reached.");
            throw new CommandException(MESSAGE_MAX_PINNED_INVALID);
        }

        // essentially an "order" command where the source is moved to the top
        Source sourceToPin = completeSourceList.get(targetIndex);
        model.deleteSource(sourceToPin);
        sourceToPin.setPinnedState(true);
        model.addSourceAtIndex(sourceToPin, 0);
        // model is not committed because the pin command is not undoable

        PinnedSourcesCoordinationCenter.incrementPinnedSources(model);
        PinnedSourcesCoordinationCenter.saveCurrentPinnedSources(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, sourceToPin));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PinCommand // instanceof handles nulls
                && targetIndex == (((PinCommand) other).targetIndex)); // state check
    }

    @Override
    public int hashCode() {
        return targetIndex;
    }
}
