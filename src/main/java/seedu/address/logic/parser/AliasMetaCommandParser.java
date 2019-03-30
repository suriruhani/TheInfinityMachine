package seedu.address.logic.parser;

/**
 * An abstract class that all AliasManager meta-command parsers extends from.
 */
public abstract class AliasMetaCommandParser {
    private AliasManager aliasManager;

    AliasManager getAliasManager() {
        return aliasManager;
    }

    void setAliasManager(AliasManager aliasManager) {
        this.aliasManager = aliasManager;
    }
}
