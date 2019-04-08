package seedu.address.logic.parser;

import seedu.address.logic.EmptyBinCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.ExitBinCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RecycleBinCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

public class RecycleBinParser extends BasicParser {

    @Override
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case RecycleBinCommand.COMMAND_WORD:
            return new RecycleBinCommand();

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case RestoreCommand.COMMAND_WORD:
            return new RestoreCommandParser().parse(arguments);

        case EmptyBinCommand.COMMAND_WORD:
            return new EmptyBinCommand();

        case ExitBinCommand.COMMAND_WORD:
            return new ExitBinCommand();

        default:
            throw new ParseException(String.format(MESSAGE_UNKNOWN_COMMAND));
            
        }
    }

}
