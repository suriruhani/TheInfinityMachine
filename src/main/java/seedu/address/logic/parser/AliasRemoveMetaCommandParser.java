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
        String[] splitArguments = userInput.trim().split(" ");
        splitArguments = userInput.trim().split(" ");
        if (splitArguments.length != 1) {
            throw new ParseException("You have provided an invalid number of arguments");
        }

        aliasManager.unregisterAlias(splitArguments[0]);
        return new DummyCommand("Alias removed");
    }

}
