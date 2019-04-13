package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BiblioCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BiblioCommand object
 */
public class BiblioCommandParser implements Parser<BiblioCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BiblioCommand
     * and returns an BiblioCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BiblioCommand parse(String args) throws ParseException {
        try {
            String[] tokenizedArguments = args.split(" ");
            if (tokenizedArguments.length != 3) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BiblioCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(tokenizedArguments[1]);
            String format = tokenizedArguments[2];

            return new BiblioCommand(index, format);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BiblioCommand.MESSAGE_USAGE), pe);
        }
    }
}
