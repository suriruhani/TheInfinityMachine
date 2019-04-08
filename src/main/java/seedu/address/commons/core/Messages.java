package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX = "The source index provided is invalid";
    public static final String MESSAGE_UNAVAILABLE_COMMAND = "This command is unavailable in this mode,\n"
            + " switch to the Recycle Bin mode to use it.";
    public static final String MESSAGE_DUPLICATE_SOURCE_TO_RESTORE = "The source you are trying to restore already "
            + "exists in the current source list.\nThe duplicate source will be removed from your deleted source list.";
    public static final String MESSAGE_SOURCES_LISTED_OVERVIEW = "%1$d sources listed!";

}
