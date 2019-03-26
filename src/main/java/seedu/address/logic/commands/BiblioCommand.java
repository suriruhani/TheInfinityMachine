package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.source.Source;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Generates a bibliography from a source at the specified index.
 */

public class BiblioCommand extends Command {
    public static final String COMMAND_WORD = "biblio";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a bibliography in appropriate style\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Bibliography generated";

    public BiblioCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    private final Index targetIndex;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Source> lastShownList = model.getFilteredSourceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        Source targetSource = lastShownList.get(targetIndex.getZeroBased());

        String targetTitle = targetSource.getTitle().title;
        String targetType = targetSource.getType().type;
        String targetDetail = targetSource.getDetail().detail;

        return new CommandResult(String.format("%s;\n%s;\n%s\n", targetTitle, targetType, targetDetail));
    }
}
