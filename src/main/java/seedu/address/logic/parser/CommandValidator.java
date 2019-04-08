package seedu.address.logic.parser;

/**
 * Represents a CommandValidator that is able to verify if a string is a valid command.
 */
public interface CommandValidator {

    /**
     * Checks whether command is a valid command.
     * Aliases cannot be the same as valid commands.
     * This protects against overwriting existing valid commands.
     */
    boolean isValidCommand(String command);

    /**
     * Checks whether command is disallowed from being aliased.
     * This prevents certain commands from being eligible for aliasing.
     */
    boolean isUnaliasableCommand(String command);
}
