package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Deactivate panic mode!
 */
public class UnpanicCommand extends Command {

    public static final String COMMAND_WORD = "unpanic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Keep your questionable research private. ";

    public static final String MESSAGE_SUCCESS = "Restored!";

    public UnpanicCommand() {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        model.disablePanicMode();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
