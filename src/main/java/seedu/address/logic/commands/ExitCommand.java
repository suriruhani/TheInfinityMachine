package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.PinnedSourcesCoordinationCenter;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Source Manager as requested ...";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        PinnedSourcesCoordinationCenter.saveCurrentPinnedSources(model);
        // Fix bug whereby model permanently loses data when exiting while in panic mode.
        // This is because the actual source manager is preserved in memory,
        // while the mock source manager has its contents written to disk (persistent storage).

        model.disablePanicMode(); // Automatically disable panic mode before exiting program

        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
