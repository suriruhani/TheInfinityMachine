package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for the UnpinCommand command
 */
public class UnpinCommandParser implements Parser<UnpinCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnpinCommand command
     * and returns an UnpinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnpinCommand parse(String args) throws ParseException {
        try {
            int index = Integer.parseInt(args.trim());
            return new UnpinCommand(index);
        } catch (NumberFormatException nfe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpinCommand.MESSAGE_USAGE), nfe);
        }
    }
}
