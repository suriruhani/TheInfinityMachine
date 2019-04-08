package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.AliasStorage;
import seedu.address.storage.ConcreteAliasStorage;

/**
 * Manages user-defined command aliases.
 */
class ConcreteAliasManager implements AliasManager {

    private static final String ERROR_INVALID_SYNTAX = "Aliases must be alphabetical only";
    private static final String ERROR_DISALLOWED_COMMAND = "This command cannot be aliased";
    private static final String ERROR_COMMAND_IS_ALIAS = "Provided command is another alias";
    private static final String ERROR_ALIAS_IS_COMMAND = "Provided alias is a command";
    private static final String REGEX_VALIDATOR = "([a-z]|[A-Z])+";

    private static final Logger logger = LogsCenter.getLogger(ConcreteAliasManager.class);

    private CommandValidator commandValidator;
    private Set<String> disallowedCommands;
    private HashMap<String, String> aliases = new HashMap<>();
    private AliasStorage aliasStorage;

    /**
     * Instantiates a ConcreteAliasManager.
     * @param commandValidator Validates commands, must not be null.
     * @param disallowedCommands A collection of commands that may not be aliased, must not be null.
     * @param aliasStorage An AliasStorage object to support storing aliases persistently. Pass null
     *                     to disable alias persistence (e.g. for unit testing).
     */
    ConcreteAliasManager(CommandValidator commandValidator,
                         Set<String> disallowedCommands,
                         AliasStorage aliasStorage) {
        Objects.requireNonNull(commandValidator);
        Objects.requireNonNull(disallowedCommands);

        this.commandValidator = commandValidator;
        this.disallowedCommands = disallowedCommands;
        this.aliasStorage = aliasStorage;

        initialize();
    }

    /**
     * Contains setup and initialization code that should be run before usage.
     */
    private void initialize() {
        if (aliasStorage == null) {
            return;
        }

        loadStoredAliases();
    }

    /**
     * Loads and restores previously-stored aliases from disk into memory,
     * if an AliasStorage object is provided. Otherwise, do nothing.
     * If an exception is thrown, log, terminate the method, and keep the database `aliases` empty.
     */
    private void loadStoredAliases() {
        HashMap<String, String> loadedAliases;
        logger.info("Loading aliases from storage into memory.");

        if (aliasStorage == null) {
            logger.info("No AliasStorage object provided. Skipping loading aliases.");
            return;
        }

        try {
            logger.info("Decoding aliases.");
            loadedAliases = aliasStorage.readAliases();
        } catch (Exception e) {
            logger.warning(
                    String.format("Encountered error decoding aliases: %s",
                            e.toString()));
            logger.warning("Terminating loading aliases.");
            return;
        }

        aliases = new HashMap(loadedAliases);
        logger.info("Loaded aliases from storage");
    }

    /**
     * Saves the current state of `aliases` into disk for persistent storage,
     * if an AliasStorage object is provided. Otherwise, do nothing.
     * Any exceptions thrown are logged.
     */
    private void saveAliases() {
        logger.info("Saving aliases into storage.");

        if (aliasStorage == null) {
            logger.info("No AliasStorage object provided. Skipping saving aliases.");
            return;
        }

        try {
            logger.info("Encoding aliases.");
            aliasStorage.saveAliases((HashMap) aliases.clone());
        } catch (Exception e) {
            logger.warning(
                    String.format("Encountered error encoding aliases: %s",
                            e.toString()));
            logger.warning("Terminating saving aliases.");
            return;
        }

        logger.info("Saved aliases into storage.");
    }

    public boolean isAlias(String alias) {
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
    public void registerAlias(String command, String alias) throws IllegalArgumentException {
        Objects.requireNonNull(alias);
        Objects.requireNonNull(command);

        // Guard against invalid alias syntax
        if (!alias.matches(REGEX_VALIDATOR)) {
            throw new IllegalArgumentException(ERROR_INVALID_SYNTAX);
        }

        // Guard against commend being a disallowed command
        if (disallowedCommands.contains(command)) {
            throw new IllegalArgumentException(ERROR_DISALLOWED_COMMAND);
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

    public void unregisterAlias(String alias) {
        aliases.remove(alias);
        saveAliases();
    }

    public void clearAliases() {
        aliases.clear();
        saveAliases();
    }

    public Optional<String> getCommand(String alias) {
        Objects.requireNonNull(alias);
        if (!isAlias(alias)) {
            return Optional.empty();
        }

        String command = aliases.get(alias);
        return Optional.of(command);
    }

    public HashMap<String, String> getAliasList() {
        return (HashMap) aliases.clone();
    }

}
