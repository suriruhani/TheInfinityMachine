package seedu.address.logic.parser;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A concrete implementation of Command that doesn't do anything except return a CommandResult.
 */
public class DummyCommand extends Command {
    private CommandResult commandResult;

    DummyCommand(String feedback) {
        commandResult = new CommandResult(feedback);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return commandResult;
    }
}
