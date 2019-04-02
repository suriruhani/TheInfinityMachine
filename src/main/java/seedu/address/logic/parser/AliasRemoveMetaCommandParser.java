package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the AliasManager "alias-rm" metacommand.
 */
public class AliasRemoveMetaCommandParser extends AliasMetaCommandParser implements Parser<Command> {
    static final String ERROR_INVALID_ARGUMENTS = "You have provided an invalid number of arguments";

    /**
     * Instantiates self with an instance of aliasManager.
     */
    public AliasRemoveMetaCommandParser(AliasManager aliasManager) {
        setAliasManager(aliasManager);
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
            throw new ParseException(ERROR_INVALID_ARGUMENTS);
        }

        String[] splitArguments = userInput.trim().split(" ");
        if (splitArguments.length != 1) {
            throw new ParseException(ERROR_INVALID_ARGUMENTS);
        }

        getAliasManager().unregisterAlias(splitArguments[0]);
        return new DummyCommand("Alias removed");
    }

}
