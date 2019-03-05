package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;


/**
 * Generated a bibliography of the appropriate style.
 */

public class BiblioCommand extends Command {
    public static final String COMMAND_WORD = "biblio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a bibliography in appropriate style";

    public static final String MESSAGE_SUCCESS = "Bibliography generated";

    public BiblioCommand() {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
