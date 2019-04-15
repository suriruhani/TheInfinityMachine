package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a ListCommand object based on the arguments passed
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        try {
            if (args.trim().length() == 0) { //no argument case
                return new ListCommand();
            }
            String[] splitArgs = args.split("\\s+");
            if (splitArgs.length == 2) { //1 argument case
                if (Integer.parseInt(splitArgs[1]) >= 0) { //positve
                    Index targetIndex = ParserUtil.parseIndex(splitArgs[1]);
                    return new ListCommand(targetIndex, true);
                } else { //negative
                    int targetNum = Math.abs(Integer.parseInt(splitArgs[1]));
                    Index targetIndex = Index.fromOneBased(targetNum);
                    return new ListCommand(targetIndex, false);
                }
            } else { //2 argument case
                Index toIndex = ParserUtil.parseIndex(splitArgs[1]);
                Index fromIndex = ParserUtil.parseIndex(splitArgs[2]);
                return new ListCommand(toIndex, fromIndex);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), pe);
        } catch (IndexOutOfBoundsException ie) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), ie);
        }
    }

}
