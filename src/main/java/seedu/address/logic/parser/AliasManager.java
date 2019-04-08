package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Optional;

/**
 * An interface to manage user-defined command aliases.
 */
public interface AliasManager {

    /**
     * Checks if alias is registered.
     */
    boolean isAlias(String alias);

    /**
     * Associates an alias with a command.
     * If alias already exists, it will be overwritten.
     * @throws IllegalArgumentException if either command or alias is invalid.
     * What constitutes an invalid command or alias is defined by the implementing class.
     */
    void registerAlias(String command, String alias) throws IllegalArgumentException;

    /**
     * Removes an alias.
     * @throws IllegalArgumentException if alias is not registered.
     */
    void unregisterAlias(String alias);

    /**
     * Clears all aliases.
     */
    void clearAliases();

    /**
     * Looks up and returns the command that alias is associated with.
     * @returns A String optional if alias is registered, and an empty optional otherwise.
     */
    Optional<String> getCommand(String alias);

    /**
     * Returns a HashMap listing all registered aliases to their commands.
     * The returned HashMap is a copy of the original and is safe to work with (i.e. safe to mutate).
     */
    HashMap<String, String> getAliasList();

}
