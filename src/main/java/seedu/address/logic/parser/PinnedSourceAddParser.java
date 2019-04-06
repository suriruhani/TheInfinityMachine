package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PinnedSourceAdd;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for the PinnedSourceAdd command
 */
public class PinnedSourceAddParser {
    /**
     * Parses the given {@code String} of arguments in the context of the PinnedSourceAdd command
     * and returns an PinnedSourceAdd object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PinnedSourceAdd parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new PinnedSourceAdd(index);
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinnedSourceAdd.MESSAGE_USAGE), nfe);
        }
    }
}
