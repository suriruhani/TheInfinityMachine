package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the AliasManager "alias" metacommand.
 */
public class AliasAddMetaCommandParser extends AliasMetaCommandParser {

    private static final String MESSAGE_SUCCESS = "Alias created successfully";
    private static final String MESSAGE_INVALID_SYNTAX = "Arguments are invalid. "
            + "Usage guide: %s COMMAND ALIAS";

    /**
     * Instantiates self with an instance of aliasManager.
     */
    public AliasAddMetaCommandParser(AliasManager aliasManager, String command) {
        super(aliasManager, command);
    }

    /**
     * Parses the provided arguments in the context of the alias (add) meta-command
     * and returns a DummyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DummyCommand parse(String userInput) throws ParseException {
        String[] splitArguments = userInput.trim().split(" ");
        if (splitArguments.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_SYNTAX, getCommand()));
        }

        try {
            getAliasManager().registerAlias(splitArguments[0], splitArguments[1]);
            return new DummyCommand(MESSAGE_SUCCESS);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

}
