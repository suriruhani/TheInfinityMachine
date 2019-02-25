package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Activate panic mode!
 */
public class PanicCommand extends Command {

    public static final String COMMAND_WORD = "panic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Keep your questionable research private. ";

    public static final String MESSAGE_SUCCESS = ""; // Obviously, we won'tt announce panic mode activation ;)

    public PanicCommand() {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        ((ModelManager) model).enablePanicMode();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
