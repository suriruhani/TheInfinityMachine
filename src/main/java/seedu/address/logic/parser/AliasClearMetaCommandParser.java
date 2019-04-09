package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the AliasManager "alias-rm" metacommand.
 */
public class AliasClearMetaCommandParser extends AliasMetaCommandParser {
    private static final String MESSAGE_SUCCESS = "%d alias(es) cleared successfully";
    private static final String MESSAGE_INVALID_SYNTAX = "Invalid syntax. "
            + "%s doesn't accept any arguments";

    /**
     * Instantiates self with an instance of aliasManager.
     */
    public AliasClearMetaCommandParser(AliasManager aliasManager, String command) {
        super(aliasManager, command);
    }

    /**
     * Parses the provided arguments in the context of the alias-clear meta-command
     * and returns a DummyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DummyCommand parse(String userInput) throws ParseException {

        if (!userInput.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_SYNTAX, getCommand()));
        }

        int aliasCount = getAliasManager().getAliasList().size();
        getAliasManager().clearAliases();
        return new DummyCommand(String.format(MESSAGE_SUCCESS, aliasCount));
    }

}
