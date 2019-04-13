package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BiblioEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.source.BiblioFields;

/**
 * Parses input arguments and creates a new BiblioEditCommand object
 */
public class BiblioEditCommandParser implements Parser<BiblioEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an BiblioEdit object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BiblioEditCommand parse(String args) throws ParseException {
        try {
            String[] tokenizedArguments = args.split(" ");
            if (tokenizedArguments.length != 4) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        BiblioEditCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(tokenizedArguments[1]);
            String header = tokenizedArguments[2];
            String body = tokenizedArguments[3];

            boolean isValidHeader = false;
            for (int i = 0; i < BiblioFields.ACCEPTED_FIELD_HEADERS.length; i++) {
                if (header.equals(BiblioFields.ACCEPTED_FIELD_HEADERS[i])) {
                    isValidHeader = true;
                }
            }

            if (!isValidHeader) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        BiblioEditCommand.MESSAGE_USAGE));
            }

            return new BiblioEditCommand(index, header, body);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BiblioEditCommand.MESSAGE_USAGE), pe);
        }
    }
}
