package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the AliasManager "alias-rm" metacommand.
 */
public class AliasRemoveMetaCommandParser extends AliasMetaCommandParser {
    private static final String MESSAGE_SUCCESS = "Alias removed successfully";
    private static final String MESSAGE_INVALID_SYNTAX = "Arguments are invalid. "
            + "Usage guide: %s ALIAS";

    /**
     * Instantiates self with an instance of aliasManager.
     */
    public AliasRemoveMetaCommandParser(AliasManager aliasManager, String command) {
        super(aliasManager, command);
    }

    /**
     * Parses the provided arguments in the context of the alias-rm meta-command
     * and returns a DummyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DummyCommand parse(String userInput) throws ParseException {

        // We need this because "".trim().split(" ").length == 1,
        // so the following if statement does not catch this condition
        if (userInput.equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_SYNTAX, getCommand()));
        }

        String[] splitArguments = userInput.trim().split(" ");
        if (splitArguments.length != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_SYNTAX, getCommand()));
        }

        try {
            getAliasManager().unregisterAlias(splitArguments[0]);
            return new DummyCommand(MESSAGE_SUCCESS);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

}
