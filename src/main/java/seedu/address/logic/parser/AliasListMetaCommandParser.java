package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for the AliasManager "alias-ls" metacommand.
 */
public class AliasListMetaCommandParser extends AliasMetaCommandParser {
    private static final String MESSAGE_EMPTY = "There are no aliases to list";


    /**
     * Instantiates self with an instance of aliasManager.
     */
    public AliasListMetaCommandParser(AliasManager aliasManager, String command) {
        super(aliasManager, command);
    }

    /**
     * Parses the provided arguments in the context of the alias-ls meta-command
     * and returns a DummyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DummyCommand parse(String userInput) throws ParseException {
        HashMap<String, String> aliasList = getAliasManager().getAliasList();

        if (aliasList.isEmpty()) {
            return new DummyCommand(MESSAGE_EMPTY);
        }

        StringBuilder sb = new StringBuilder();
        aliasList.forEach((alias, command) -> sb.append(String.format("%s: %s\n", alias, command)));

        return new DummyCommand(sb.toString());
    }

}
