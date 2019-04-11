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
 * Moves a source to a designated position
 */
public class CustomOrderCommand extends Command {
    public static final String COMMAND_WORD = "order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Swaps the source in a given index to a position in the list.\n"
            + "Parameters: "
            + "SOURCE_INDEX (must be a positive integer) "
            + "MOVE_POSITION (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2 6";

    public static final String MESSAGE_SOURCE_INDEX_INVALID = "The source index to move is invalid.";
    public static final String MESSAGE_MOVE_POSITION_INVALID = "The position to move to is invalid.";
    public static final String MESSAGE_INDEX_IDENTICAL = "The source index and move position are the same.";
    public static final String MESSAGE_SUCCESS = "Source at position %d moved to position %d.";
    public static final String MESSAGE_POSITION_PINNED = "The position is pinned.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private int initialIndex;
    private int newPosition;

    public CustomOrderCommand(int initialIndex, int newPosition) {
        this.initialIndex = initialIndex - 1;
        this.newPosition = newPosition - 1;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        List<Source> completeSourceList = model.getFilteredSourceList();

        if (initialIndex >= completeSourceList.size() || initialIndex < 0) {
            logger.info("--- CustomOrderCommand: Initial index given is out of bounds.");
            throw new CommandException(MESSAGE_SOURCE_INDEX_INVALID);
        }

        if (newPosition >= completeSourceList.size() || newPosition < 0) {
            logger.info("--- CustomOrderCommand: Move position index given is out of bounds.");
            throw new CommandException(MESSAGE_MOVE_POSITION_INVALID);
        }

        if (initialIndex == newPosition) {
            logger.info("--- CustomOrderCommand: Initial index and move position are the same.");
            throw new CommandException(MESSAGE_INDEX_IDENTICAL);
        }

        boolean isSourcePinned = PinnedSourcesCoordinationCenter.isPinnedSource(model, initialIndex);
        boolean isPositionPinned = PinnedSourcesCoordinationCenter.isPinnedSource(model, newPosition);
        if (isSourcePinned == true) {
            logger.info("Source is pinned.");
            throw new CommandException(PinnedSourcesCoordinationCenter.MESSAGE_SOURCE_PINNED);
        }
        if (isPositionPinned == true) {
            logger.info("Position is pinned.");
            throw new CommandException(MESSAGE_POSITION_PINNED);
        }

        Source sourceToMove = completeSourceList.get(initialIndex);
        model.deleteSource(sourceToMove);
        model.addSourceAtIndex(sourceToMove, newPosition);
        model.commitSourceManager();

        return new CommandResult(String.format(MESSAGE_SUCCESS, initialIndex + 1, newPosition + 1));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomOrderCommand // instanceof handles nulls
                && initialIndex == (((CustomOrderCommand) other).initialIndex)
                && newPosition == (((CustomOrderCommand) other).newPosition)); // state check
    }

    @Override
    public int hashCode() {
        return (initialIndex + newPosition);
    }
}
