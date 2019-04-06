package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PinnedSourceRemove;
import seedu.address.logic.parser.exceptions.ParseException;

public class PinnedSourceRemoveParser {
    /**
     * Parses the given {@code String} of arguments in the context of the PinnedSourceRemove command
     * and returns an PinnedSourceRemove object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PinnedSourceRemove parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new PinnedSourceRemove(index);
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PinnedSourceRemove.MESSAGE_USAGE), nfe);
        }
    }
}
