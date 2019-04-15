package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BiblioCommand;
import seedu.address.logic.commands.BiblioEditCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CountCommand;
import seedu.address.logic.commands.CustomOrderCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PanicCommand;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.RecycleBinCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnpanicCommand;
import seedu.address.logic.commands.UnpinCommand;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.ConcreteAliasStorage;

/**
 * Parses user input for Source Manager.
 */
public class SourceManagerParser implements CommandValidator {
    /**
     * Used for initial separation of command word and args.
     */
    protected static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final String COMMAND_ALIAS_ADD = "alias";
    private static final String COMMAND_ALIAS_REMOVE = "alias-rm";
    private static final String COMMAND_ALIAS_CLEAR = "alias-clear";
    private static final String COMMAND_ALIAS_LIST = "alias-ls";

    private Set<String> metaCommands = new HashSet<>();
    private Set<String> validCommands = new HashSet<>();

    private AliasManager aliasManager;

    /**
     * Convenience constructor that instantiates SourceManagerParser with ConcreteAliasManager.
     */
    public SourceManagerParser() { // This supports dependency injection
        this(null);
    }

    /**
     * Instantiates SourceManagerParser with an AliasManager object.
     * @param aliasManager If null, ConcreteAliasManager will be instantiated to be used.
     */
    public SourceManagerParser(AliasManager aliasManager) {
        if (aliasManager == null) {
            this.aliasManager = new ConcreteAliasManager(this, new ConcreteAliasStorage());
        } else {
            this.aliasManager = aliasManager;
        }

        initializeMetaCommands();
        initializeValidCommands();
    }

    /**
     * Initializes the a set of meta-commands.
     */
    private void initializeMetaCommands() {
        metaCommands.add(COMMAND_ALIAS_ADD);
        metaCommands.add(COMMAND_ALIAS_REMOVE);
        metaCommands.add(COMMAND_ALIAS_CLEAR);
        metaCommands.add(COMMAND_ALIAS_LIST);
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
        validCommands.add(BiblioCommand.COMMAND_WORD);
        validCommands.add(BiblioEditCommand.COMMAND_WORD);
        validCommands.add(RecycleBinCommand.COMMAND_WORD);
    }

    public boolean isValidCommand(String command) {
        return validCommands.contains(command);
    }

    public boolean isUnaliasableCommand(String command) {
        return metaCommands.contains(command);
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

        case BiblioCommand.COMMAND_WORD:
            return new BiblioCommandParser().parse(arguments);

        case BiblioEditCommand.COMMAND_WORD:
            return new BiblioEditCommandParser().parse(arguments);

        case PinCommand.COMMAND_WORD:
            return new PinCommandParser().parse(arguments);

        case UnpinCommand.COMMAND_WORD:
            return new UnpinCommandParser().parse(arguments);

        case RecycleBinCommand.COMMAND_WORD:
            return new RecycleBinCommand();

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
