package seedu.address.logic.parser;

/**
 * Represents a CommandValidator that is able to verify if a string is a valid command.
 */
public interface CommandValidator {

    /**
     * Checks whether command is a valid command.
     */
    boolean isValidCommand(String command);
}
