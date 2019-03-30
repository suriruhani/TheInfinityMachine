package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

public class AliasAddMetaCommandParser extends AliasMetaCommandParser implements Parser<Command> {

    /**
     * Instantiates self with an instance of aliasManager.
     */
    public AliasAddMetaCommandParser(AliasManager aliasManager) {
        this.aliasManager = aliasManager;
    }

    /**
     * Parses the provided arguments in the context of the alias (add) meta-command
     * and returns a DummyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DummyCommand parse(String userInput) throws ParseException {
        String[] splitArguments = userInput.trim().split(" ");
        if (splitArguments.length != 2) {
            throw new ParseException("You have provided an invalid number of arguments");
        }

        try {
            aliasManager.registerAlias(splitArguments[0], splitArguments[1]);
            return new DummyCommand("Alias created");
        } catch (IllegalArgumentException e) {
            return new DummyCommand(e.getMessage());
        }
    }

}
