package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.AliasStorage;
import seedu.address.storage.ConcreteAliasStorage;

/**
 * Manages user-defined command aliases.
 */
class AliasManager {
    static final String COMMAND_WORD_ADD = "alias";
    static final String COMMAND_WORD_REMOVE = "alias-rm";
    static final String COMMAND_WORD_LIST = "alias-ls";
    private static final String ERROR_INVALID_SYNTAX = "Aliases must be alphabetical only";
    private static final String ERROR_COMMAND_IS_METACOMMAND = "This command cannot be aliased";
    private static final String ERROR_COMMAND_IS_ALIAS = "Provided command is another alias";
    private static final String ERROR_ALIAS_IS_COMMAND = "Provided alias is a command";
    private static final String REGEX_VALIDATOR = "([a-z]|[A-Z])+";

    private static final Logger logger = LogsCenter.getLogger(AliasManager.class);

    private boolean persistentMode = true; // Disable for unit testing

    private CommandValidator commandValidator;
    private HashMap<String, String> aliases = new HashMap<>();
    private AliasStorage aliasStorage = new ConcreteAliasStorage();

    AliasManager(CommandValidator commandValidator) {
        this(commandValidator, true);
    }

    AliasManager(CommandValidator commandValidator, boolean persistentMode) {
        Objects.requireNonNull(commandValidator);
        this.commandValidator = commandValidator;
        if (persistentMode) {
            loadStoredAliases();
        } else {
            this.persistentMode = false;
        }
    }

    /**
     * Getter for aliasStorage.
     * This is not safe to mutate.
     */
    AliasStorage getAliasStorage() {
        return aliasStorage;
    }

    /**
     * Loads and restores previously-stored aliases from disk into memory.
     * If an exception is thrown, log, terminate the method, and keep `aliases` empty.
     */
    private void loadStoredAliases() {
        HashMap<String, String> loadedAliases;
        try {
            logger.info("Attempting to load aliases from storage");
            loadedAliases = aliasStorage.readAliases();
        } catch (Exception e) {
            logger.warning(e.toString());
            return;
        }

        aliases = new HashMap(loadedAliases);
        logger.info("Restored aliases from storage");
    }

    /**
     * Saves the current state of `aliases` into disk for persistent storage.
     * Any exceptions thrown are logged.
     * Does nothing if persistent mode is disabled (for unit testing).
     */
    private void saveAliases() {
        if (!persistentMode) {
            return;
        }

        try {
            logger.info("Attempting to save aliases to storage");
            aliasStorage.saveAliases((HashMap) aliases.clone());
        } catch (Exception e) {
            logger.warning(
                    String.format("Skipping saving aliases to storage; encountered exception: %s",
                            e.toString()));
            return;
        }
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
     * @throws IllegalArgumentException if command has an invalid syntax (must be alphabetical only).
     * @throws IllegalArgumentException if command is a meta-command.
     * @throws IllegalArgumentException if command is another registered alias.
     * @throws IllegalArgumentException if alias is an existing command.
     */
    void registerAlias(String command, String alias) throws IllegalArgumentException {
        Objects.requireNonNull(alias);
        Objects.requireNonNull(command);

        // Guard against invalid alias syntax
        if (!alias.matches(REGEX_VALIDATOR)) {
            throw new IllegalArgumentException(ERROR_INVALID_SYNTAX);
        }

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
        saveAliases();
    }

    /**
     * Removes an alias. Does nothing if alias does not exist.
     */
    void unregisterAlias(String alias) {
        aliases.remove(alias);
        saveAliases();
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

    /**
     * Returns a HashMap listing all registered aliases to their commands.
     * The returned HashMap is a copy of the original and is safe to work with (i.e. safe to mutate).
     */
    HashMap<String, String> getAliasList() {
        return (HashMap) aliases.clone();
    }
}
