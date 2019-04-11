package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CountCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EmptyBinCommand;
import seedu.address.logic.commands.ExitBinCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RecycleBinCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for Recycle Bin.
 */
public class RecycleBinParser extends SourceManagerParser {

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
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

        case CountCommand.COMMAND_WORD:
            return new CountCommand();

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExitBinCommand.COMMAND_WORD:
            return new ExitBinCommand();

        // Meta-commands (pertaining to AliasManager)
        // Meta-commands (pertaining to AliasManager):
        // For these, we include implementation details because these are meta-commands
        // that relate directly to AliasManager (and by association, SourceManagerParser).

        case COMMAND_ALIAS_ADD:
            return new AliasAddMetaCommandParser(aliasManager, COMMAND_ALIAS_ADD)
                    .parse(arguments);

        case COMMAND_ALIAS_REMOVE:
            return new AliasRemoveMetaCommandParser(aliasManager, COMMAND_ALIAS_REMOVE)
                    .parse(arguments);

        case COMMAND_ALIAS_CLEAR:
            return new AliasClearMetaCommandParser(aliasManager, COMMAND_ALIAS_CLEAR)
                    .parse(arguments);

        case COMMAND_ALIAS_LIST:
            return new AliasListMetaCommandParser(aliasManager, COMMAND_ALIAS_LIST)
                    .parse(arguments);

        default:
            // Throw ParseException if input is not an alias
            if (!aliasManager.isAlias(commandWord)) {
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

            // This should never throw NoSuchElementException because we ensured the validity of the alias
            String actualCommand = aliasManager.getCommand(commandWord).get();

            String actualUserInput = userInput.replaceFirst(commandWord, actualCommand);
            return parseCommand(actualUserInput);
        }
    }

}
