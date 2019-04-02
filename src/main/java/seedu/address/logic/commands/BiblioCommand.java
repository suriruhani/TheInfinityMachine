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
 * Generates a bibliography from a source at the specified index of appropriate style.
 */

public class BiblioCommand extends Command {
    public static final String COMMAND_WORD = "biblio";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a bibliography in appropriate style\n"
            + "Parameters: \n"
            + "FORMAT (must be a non-empty string)\n"
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + "APA 1";

    public static final String MESSAGE_SUCCESS = "Bibliography generated";

    private final String format;
    private final Index targetIndex;

    public BiblioCommand(String format, Index targetIndex) {
        this.format = format;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Source> lastShownList = model.getFilteredSourceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        Source targetSource = lastShownList.get(targetIndex.getZeroBased());
        String biblioEntry;
        switch (format) {
        case "APA":
            biblioEntry = generateApa(targetSource);
            return new CommandResult(String.format(MESSAGE_SUCCESS + biblioEntry));
        case "MLA":
            biblioEntry = generateMla(targetSource);
            return new CommandResult(String.format(MESSAGE_SUCCESS + biblioEntry));
        default:
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Generates a bibliography of APA style.
     */
    private String generateApa(Source targetSource) {
        String targetTitle = targetSource.getTitle().title;
        String targetType = targetSource.getType().type;
        String targetDetail = targetSource.getDetail().detail;
        String biblioEntry = String.format("\n%s;\n%s;\n%s;\n", targetTitle, targetType, targetDetail);
        return biblioEntry;
    }

    /**
     * Generates a bibliography of MLA style.
     */
    private String generateMla(Source targetSource) {
        String targetTitle = targetSource.getTitle().title;
        String targetType = targetSource.getType().type;
        String targetDetail = targetSource.getDetail().detail;
        String biblioEntry = String.format("\n%s;\n%s;\n%s;\n", targetTitle, targetType, targetDetail);
        return biblioEntry;
    }
}
