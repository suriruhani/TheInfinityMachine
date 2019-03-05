package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class CountCommand extends Command{

    public static final String COMMAND_WORD = "count";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Counts the number of source(s) in the Infinity Machine. ";

    public static final String MESSAGE_SUCCESS = "Total number of Source(s): %1$s";

    public CountCommand() {}

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        int count = model.getCount();
        return new CommandResult(String.format(MESSAGE_SUCCESS, count));
    }

}
