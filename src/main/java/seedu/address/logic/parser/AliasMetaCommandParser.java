package seedu.address.logic.parser;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * An abstract parser class that all meta-command parsers should subclass.
 */
public abstract class AliasMetaCommandParser implements Parser<DummyCommand> {
    private final AliasManager aliasManager;
    private final String command;

    AliasMetaCommandParser(AliasManager aliasManager, String command) {
        this.aliasManager = aliasManager;
        this.command = command;
    }

    public abstract DummyCommand parse(String userInput) throws ParseException;

    AliasManager getAliasManager() {
        return aliasManager;
    }

    String getCommand() {
        return command;
    }
}
