package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.exceptions.ParseException;

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

    private AliasManager aliasManager;

    public SourceManagerParser() {
        aliasManager = new AliasManager(this);
        initializeValidCommands();
    }

    /**
     * Alternative constructor for dependency injection.
     */
    public SourceManagerParser(AliasManager aliasManager) {
        this.aliasManager = aliasManager;
        initializeValidCommands();
    }

    /**
     * Initializes the set of valid commands.
     */
    private void initializeValidCommands() {
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
        validCommands.add(RestoreCommand.COMMAND_WORD);
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
            return new ListCommandParser().parse(arguments);

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

        case RestoreCommand.COMMAND_WORD:
            return new RestoreCommandParser().parse(arguments);

        case PinnedSourceAdd.COMMAND_WORD:
            return new PinnedSourceAddParser().parse(arguments);

        case PinnedSourceRemove.COMMAND_WORD:
            return new PinnedSourceRemoveParser().parse(arguments);

        // Meta-commands (pertaining to AliasManager)
        // Meta-commands (pertaining to AliasManager):
        // For these, we include implementation details because these are meta-commands
        // that relate directly to AliasManager (and by association, SourceManagerParser).

        case AliasManager.COMMAND_WORD_ADD:
            return new AliasAddMetaCommandParser(aliasManager).parse(arguments);

        case AliasManager.COMMAND_WORD_REMOVE:
            return new AliasRemoveMetaCommandParser(aliasManager).parse(arguments);

        case AliasManager.COMMAND_WORD_LIST:
            return new AliasListMetaCommandParser(aliasManager).parse(arguments);


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
