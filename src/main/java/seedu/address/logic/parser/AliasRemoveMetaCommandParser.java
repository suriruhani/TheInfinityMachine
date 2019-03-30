package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class AliasRemoveMetaCommandParser extends AliasMetaCommandParser implements Parser<Command>  {

    /**
     * Instantiates self with an instance of aliasManager.
     */
    public AliasRemoveMetaCommandParser(AliasManager aliasManager) {
        this.aliasManager = aliasManager;
    }

    /**
     * Parses the provided arguments in the context of the alias-rm meta-command
     * and returns a DummyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DummyCommand parse(String userInput) throws ParseException {
        final String ERROR_INVALID_ARGUMENTS = "You have provided an invalid number of arguments";

        // We need this because "".trim().split(" ").length == 1,
        // so the following if statement does not catch this condition
        if (userInput.equals("")) {
            throw new ParseException(ERROR_INVALID_ARGUMENTS);
        }

        String[] splitArguments = userInput.trim().split(" ");
        if (splitArguments.length != 1) {
            throw new ParseException(ERROR_INVALID_ARGUMENTS);
        }

        aliasManager.unregisterAlias(splitArguments[0]);
        return new DummyCommand("Alias removed");
    }

}
