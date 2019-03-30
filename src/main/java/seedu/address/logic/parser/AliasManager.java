package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * Manages user-defined command aliases.
 * Non-persistent (valid for one session only).
 */
class AliasManager {
    static final String COMMAND_WORD_ADD = "alias";
    static final String COMMAND_WORD_REMOVE = "alias-rm";
    static final String COMMAND_WORD_LIST = "alias-ls";
    private static final String ERROR_COMMAND_IS_METACOMMAND = "This command cannot be aliased";
    private static final String ERROR_COMMAND_IS_ALIAS = "Provided command is another alias";
    private static final String ERROR_ALIAS_IS_COMMAND = "Provided alias is a command";

    private CommandValidator commandValidator;
    private HashMap<String, String> aliases = new HashMap<>();

    AliasManager(CommandValidator commandValidator) {
        Objects.requireNonNull(commandValidator);
        this.commandValidator = commandValidator;
    }

    /**
     * Checks if alias is registered.
     */
    boolean isAlias(String alias) {
        Objects.requireNonNull(alias);
        return aliases.containsKey(alias);
    }

    /**
     * Associates an alias with a command.
     * If alias already exists, it will be overwritten.
     * @throws IllegalArgumentException if command is another registered alias.
     * @throws IllegalArgumentException if alias is an existing command.
     */
    void registerAlias(String command, String alias) throws IllegalArgumentException {
        Objects.requireNonNull(alias);
        Objects.requireNonNull(command);

        // Guard against commend being an AliasManager meta-command (e.g. alias, alias-rm, etc.)
        if (command.equals(COMMAND_WORD_ADD)
                || command.equals(COMMAND_WORD_REMOVE)
                || command.equals(COMMAND_WORD_LIST)) {
            throw new IllegalArgumentException(ERROR_COMMAND_IS_METACOMMAND);
        }

        // Guard against command being another registered alias
        if (isAlias(command)) {
            throw new IllegalArgumentException(ERROR_COMMAND_IS_ALIAS);
        }

        // Guard against alias being an existing command
        if (commandValidator.isValidCommand(alias)) {
            throw new IllegalArgumentException(ERROR_ALIAS_IS_COMMAND);
        }

        aliases.put(alias, command);
    }

    /**
     * Removes an alias. Does nothing if alias does not exist.
     */
    void unregisterAlias(String alias) {
        aliases.remove(alias);
    }

    /**
     * Looks up and returns the command that alias is associated with.
     * @returns A String optional if alias is registered, and an empty optional otherwise.
     */
    Optional<String> getCommand(String alias) {
        Objects.requireNonNull(alias);
        if (!isAlias(alias)) {
            return Optional.empty();
        }

        String command = aliases.get(alias);
        return Optional.of(command);
    }
}
