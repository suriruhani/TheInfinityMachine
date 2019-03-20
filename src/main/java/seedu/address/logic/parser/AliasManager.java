package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.Objects;

import seedu.address.logic.commands.Command;

/**
 * An enum representing the different Class types that an alias can be mapped to.
 */
enum AliasAssociateType {
    COMMAND,
    PARSER
}

/**
 * Manages user-defined command aliases.
 * Non-persistent (valid for one session only).
 */
class AliasManager {
    private static final String EXCEPTION_INVALID_ALIAS = "`alias` does not exist";
    private static final String EXCEPTION_WRONG_TYPE = "`associate` must be either a Command or Parser class, " +
            "or their subclasses";
    static final Class[] permittedClasses = {Command.class, Parser.class};

    private HashMap<String, Class<? extends Command>> aliasCommandMap = new HashMap<>();
    private HashMap<String, Class<? extends Parser<? extends Command>>> aliasParserMap = new HashMap<>();

    /**
     * Checks if a Class object is permissible under AliasManager.
     * AliasManager presently supports only the classes in `permittedClasses`.
     */
    private boolean isPermissibleClass(Class cls) {
        for (int i = 0; i < permittedClasses.length; i++) {
            // Check if `cls` is, or is a subclass of, any Class in permittedClasses
            Class permittedClass = permittedClasses[i];
            if (cls == permittedClass) {
                return true;
            } else if (permittedClass.isAssignableFrom(cls)) { // Check for subclass relationship
                return true;
            }
        }

        return false;
    }

    /**
     * Associates an alias with either a Command or a Parser<Command>.
     * Does nothing if alias already exists.
     * @param associate Either a Command class or a Parser<Command> class to be associated with `alias`.
     */
    void registerAlias(String alias, Class associate) throws IllegalArgumentException {
        Objects.requireNonNull(alias);
        Objects.requireNonNull(associate);

        // Guard against `alias` being already registered
        if (isAlias(alias)) {
            return;
        }
        // Guard against `associate` being an unsupported Class type
        if (!isPermissibleClass(associate)) {
            throw new IllegalArgumentException(EXCEPTION_WRONG_TYPE);
        }

        // Handle `associate` being either a Command or Parser class
        if (associate == Command.class) {
            aliasCommandMap.put(alias, associate);
        } else if (associate == Parser.class) {}
            aliasParserMap.put(alias, associate);
    }

    /**
     * Removes an alias. Does nothing if alias does not exist.
     */
    void unregisterAlias(String alias) {
        if (!isAlias(alias)) {
            return;
        }

        // Even though `alias` will only exist in one of the HashMaps,
        // we call .remove() on both, so we don't have to check which one it exists in.
        aliasCommandMap.remove(alias);
        aliasParserMap.remove(alias);
    }

    /**
     * Checks if `alias` is a registered alias.
     * @param alias The alias.
     * @return true if so, false otherwise.
     */
    boolean isAlias(String alias) {
        Objects.requireNonNull(alias);
        return aliasCommandMap.containsKey(alias) || aliasParserMap.containsKey(alias);
    }

    /**
     * Returns the AliasAssociateType associated with a registered alias. Alias must be valid.
     * @throws IllegalArgumentException if alias is invalid.
     */
    AliasAssociateType getAliasAssociateType(String alias) throws IllegalArgumentException {
        Objects.requireNonNull(alias);
        if (!isAlias(alias)) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_ALIAS);
        }

        if (aliasCommandMap.containsKey(alias)) {
            return AliasAssociateType.COMMAND;
        } else if (aliasParserMap.containsKey(alias)) {
            return AliasAssociateType.PARSER;
        } else {
            return null; // This should never happen because we guarantee isAlias(alias) == true
        }
    }

    /**
     * Returns the Class that `alias` is associated with (i.e. registered to).
     * @throws IllegalArgumentException if alias is invalid.
     * @return The associated Class. This is guaranteed to be one of those listed in `permittedClasses`.
     */
    Class getAliasAssociate(String alias) throws IllegalArgumentException {
        Objects.requireNonNull(alias);
        if (!isAlias(alias)) {
            throw new IllegalArgumentException(EXCEPTION_INVALID_ALIAS);
        }

        if (aliasCommandMap.containsKey(alias)) {
            return aliasCommandMap.get(alias);
        } else if (aliasParserMap.containsKey(alias)) {
            return aliasParserMap.get(alias);
        } else {
            return null; // This should never happen because we guarantee isAlias(alias) == true
        }
    }
}
