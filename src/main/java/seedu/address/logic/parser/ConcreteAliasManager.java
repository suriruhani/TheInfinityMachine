package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.AliasStorage;

/**
 * Manages user-defined command aliases.
 */
class ConcreteAliasManager implements AliasManager {

    private static final String ERROR_INVALID_SYNTAX = "Aliases must be alphabetical only";
    private static final String ERROR_DISALLOWED_COMMAND = "This command cannot be aliased";
    private static final String ERROR_INVALID_COMMAND = "The provided command is not a valid command";
    private static final String ERROR_COMMAND_IS_ALIAS = "The provided command is another alias";
    private static final String ERROR_ALIAS_IS_COMMAND = "The provided alias is an existing command";
    private static final String ERROR_ALIAS_IS_UNREGISTERED = "The provided alias is not registered";
    private static final String REGEX_VALIDATOR = "([a-z]|[A-Z])+";

    private static final Logger logger = LogsCenter.getLogger(ConcreteAliasManager.class);

    private CommandValidator commandValidator;
    private HashMap<String, String> aliases = new HashMap<>();
    private AliasStorage aliasStorage;

    /**
     * Instantiates a ConcreteAliasManager.
     * @param commandValidator Validates commands, must not be null.
     * @param aliasStorage An AliasStorage object to support storing aliases persistently. Pass null
     *                     to disable alias persistence (e.g. for unit testing).
     */
    ConcreteAliasManager(CommandValidator commandValidator, AliasStorage aliasStorage) {
        Objects.requireNonNull(commandValidator);

        logger.info("Instantiating ConcreteAliasManager.");
        this.commandValidator = commandValidator;
        this.aliasStorage = aliasStorage;

        logger.info("Instantiated ConcreteAliasManager.");
        initialize();
    }

    /**
     * Contains setup and initialization code that should be run before usage.
     */
    private void initialize() {
        logger.info("Initializing ConcreteAliasManager for use.");
        if (aliasStorage == null) {
            logger.info("No AliasStorage object provided. Terminating initialization.");
            return;
        }

        loadStoredAliases();
    }

    /**
     * Loads and restores previously-stored aliases from disk into memory,
     * if an AliasStorage object is provided. Otherwise, do nothing.
     * If an exception is thrown, log, terminate the method, and keep the `aliases` database empty.
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
     * @throws IllegalArgumentException if command is designated as un-aliasable.
     * @throws IllegalArgumentException if command is invalid.
     * @throws IllegalArgumentException if command is another registered alias.
     * @throws IllegalArgumentException if alias is an existing command.
     */
    public void registerAlias(String command, String alias) throws IllegalArgumentException {
        Objects.requireNonNull(alias);
        Objects.requireNonNull(command);

        logger.info(
                String.format("Registering alias=%s to command=%s.",
                        alias,
                        command));

        // Guard against invalid alias syntax
        if (!alias.matches(REGEX_VALIDATOR)) {
            logger.warning("Alias syntax is invalid. Throwing IllegalArgumentException.");
            throw new IllegalArgumentException(ERROR_INVALID_SYNTAX);
        }

        // Guard against command being designated as an un-aliasable command
        if (commandValidator.isUnaliasableCommand(command)) {
            logger.warning("Command cannot be aliased. Throwing IllegalArgumentException.");
            throw new IllegalArgumentException(ERROR_DISALLOWED_COMMAND);
        }

        // Guard against command being invalid
        if (!commandValidator.isValidCommand(command)) {
            logger.warning("Command is invalid. Throwing IllegalArgumentException.");
            throw new IllegalArgumentException(ERROR_INVALID_COMMAND);
        }

        // Guard against command being another registered alias
        if (isAlias(command)) {
            logger.warning("Command is an alias and cannot be aliased. Throwing IllegalArgumentException.");
            throw new IllegalArgumentException(ERROR_COMMAND_IS_ALIAS);
        }

        // Guard against alias being an existing command
        if (commandValidator.isValidCommand(alias)) {
            logger.warning("Alias is a command and cannot be registered. Throwing IllegalArgumentException.");
            throw new IllegalArgumentException(ERROR_ALIAS_IS_COMMAND);
        }

        aliases.put(alias, command);
        saveAliases();
    }

    /**
     * Removes an alias.
     * @throws IllegalArgumentException if alias is not registered.
     */
    public void unregisterAlias(String alias) throws IllegalArgumentException {
        logger.info(String.format("Unregistering alias=%s.", alias));

        // Guard against alias being unregistered
        if (!isAlias(alias)) {
            logger.warning("Alias is not registered. Throwing IllegalArgumentException.");
            throw new IllegalArgumentException(ERROR_ALIAS_IS_UNREGISTERED);
        }

        aliases.remove(alias);
        saveAliases();
    }

    public void clearAliases() {
        logger.info("Clearing all aliases.");
        aliases.clear();
        saveAliases();
    }

    public Optional<String> getCommand(String alias) {
        Objects.requireNonNull(alias);

        logger.info(String.format("Getting command for alias=%s.", alias));

        if (!isAlias(alias)) {
            return Optional.empty();
        }

        String command = aliases.get(alias);
        return Optional.of(command);
    }

    public HashMap<String, String> getAliasList() {
        logger.info("Getting list of aliases.");
        return (HashMap) aliases.clone();
    }

}
