package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BiblioCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CountCommand;
import seedu.address.logic.commands.CustomOrderCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.GreetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PanicCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnpanicCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Parses user input.
 */
public class SourceManagerParser implements CommandValidator {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    // Set of all valid commands
    private HashSet<String> validCommands = new HashSet<>();

    private AliasManager aliasManager = new AliasManager(this);

    public SourceManagerParser() {
        validCommands.add(AddCommand.COMMAND_WORD);
        validCommands.add(EditCommand.COMMAND_WORD);
        validCommands.add(SelectCommand.COMMAND_WORD);
        validCommands.add(DeleteCommand.COMMAND_WORD);
        validCommands.add(ClearCommand.COMMAND_WORD);
        validCommands.add(SearchCommand.COMMAND_WORD);
        validCommands.add(ListCommand.COMMAND_WORD);
        validCommands.add(HistoryCommand.COMMAND_WORD);
        validCommands.add(ExitCommand.COMMAND_WORD);
        validCommands.add(HelpCommand.COMMAND_WORD);
        validCommands.add(UndoCommand.COMMAND_WORD);
        validCommands.add(RedoCommand.COMMAND_WORD);
        validCommands.add(PanicCommand.COMMAND_WORD);
        validCommands.add(UnpanicCommand.COMMAND_WORD);
        validCommands.add(CountCommand.COMMAND_WORD);
        validCommands.add(GreetCommand.COMMAND_WORD);
    }

    /**
     * Checks whether command is a valid command.
     */
    public boolean isValidCommand(String command) {
        return validCommands.contains(command);
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        String[] splitArguments;

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case SearchCommand.COMMAND_WORD:
            return new SearchCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case CustomOrderCommand.COMMAND_WORD:
            return new CustomOrderCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case PanicCommand.COMMAND_WORD:
            return new PanicCommand();

        case UnpanicCommand.COMMAND_WORD:
            return new UnpanicCommand();

        case CountCommand.COMMAND_WORD:
            return new CountCommand();

        case GreetCommand.COMMAND_WORD:
            return new GreetCommand();

        case BiblioCommand.COMMAND_WORD:
            return new BiblioCommandParser().parse(arguments);

        // Meta-commands (pertaining to AliasManager):
        // For these, we include implementation details because these are meta-commands
        // that relate directly to AliasManager (and by association, SourceManagerParser).

        case AliasManager.COMMAND_WORD_ADD:
            splitArguments = arguments.trim().split(" ");
            if (splitArguments.length != 2) {
                throw new ParseException("You have provided an invalid number of arguments");
            }

            try {
                aliasManager.registerAlias(splitArguments[0], splitArguments[1]);
                return new DummyCommand("Alias created");
            } catch (IllegalArgumentException e) {
                return new DummyCommand(e.getMessage());
            }

        case AliasManager.COMMAND_WORD_REMOVE:
            splitArguments = arguments.trim().split(" ");
            if (splitArguments.length != 1) {
                throw new ParseException("You have provided an invalid number of arguments");
            }

            aliasManager.unregisterAlias(splitArguments[0]);
            return new DummyCommand("Alias removed");

        case AliasManager.COMMAND_WORD_LIST:
            HashMap<String, String> aliasList = aliasManager.getAliasList();

            if (aliasList.isEmpty()) {
                return new DummyCommand("There are no aliases to list");
            }

            StringBuilder sb = new StringBuilder();
            aliasList.forEach((alias, command) -> sb.append(String.format("%s: %s\n", alias, command)));

            return new DummyCommand(sb.toString());


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

    /**
     * A concrete implementation of Command that doesn't do anything except return a CommandResult.
     */
    private class DummyCommand extends Command {
        private CommandResult commandResult;

        DummyCommand(String feedback) {
            commandResult = new CommandResult(feedback);
        }

        @Override
        public CommandResult execute(Model model, CommandHistory history) throws CommandException {
            return commandResult;
        }
    }

}
