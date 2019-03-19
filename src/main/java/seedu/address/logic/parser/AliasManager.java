package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CountCommand;

/**
 * Manages user-defined command aliases.
 * Non-persistent (valid for one session only).
 */
public class AliasManager {
    private HashMap<String, Class<? extends Command>> aliasMap = new HashMap<>();

    /**
     * A static utility method to initialize an instance of AliasManager with default aliases.
     * Currently, it only registers "c" for CountCommand.
     * @param am An instance of an AliasManager.
     */
    static void initializeWithDefaults(AliasManager am) {
        am.registerAlias("c", CountCommand.class);
    }

    /**
     * Associates an alias with a command. If alias already exists, do nothing.
     * @param alias An alias to register.
     * @param command A Command instance.
     */
    void registerAlias(String alias, Class<? extends Command> command) {
        if (aliasMap.containsKey(alias)) {
            return;
        }

        aliasMap.put(alias, command);
    }

    /**
     * Checks if `alias` is a registered alias.
     * @param alias The alias.
     * @return true if so, false otherwise.
     */
    boolean isAlias(String alias) {
        return aliasMap.containsKey(alias);
    }

    /**
     * Returns an instance of the mapped Command to a registered alias. Requires alias to be valid.
     * @param alias The alias.
     * @return An instance of the mapped Command.
     */
    Command getCommandForAlias(String alias) throws Exception {
        Class<? extends Command> commandClass = aliasMap.get(alias);
        Command instantiatedCommand = commandClass.getConstructor().newInstance();

        return instantiatedCommand;
    }
}
