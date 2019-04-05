package seedu.address.logic.parser;

/**
 * An abstract class that all AliasManager meta-command parsers extends from.
 */
public abstract class AliasMetaCommandParser {
    private final AliasManager aliasManager;
    private final String command;

    AliasMetaCommandParser(AliasManager aliasManager, String command) {
        this.aliasManager = aliasManager;
        this.command = command;
    }

    AliasManager getAliasManager() {
        return aliasManager;
    }
}
