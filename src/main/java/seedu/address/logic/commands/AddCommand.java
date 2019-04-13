package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.source.Source;

/**
 * Adds a source to the infinity machine.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new source to the database. "
            + "Parameters: "
            + PREFIX_TITLE + "SOURCE_TITLE "
            + PREFIX_TYPE + "SOURCE_TYPE "
            + PREFIX_AUTHOR + "AUTHOR_NAME"
            + PREFIX_DETAILS + "SOURCE_DETAILS "
            + "[" + PREFIX_TAG + "SOURCE_TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Wikipedia Algorithms "
            + PREFIX_TYPE + "Website "
            + PREFIX_AUTHOR + "Jason Smith James "
            + PREFIX_DETAILS + "Basic definitions of algorithms "
            + PREFIX_TAG + "Algorithms "
            + PREFIX_TAG + "Introduction";

    public static final String MESSAGE_SUCCESS = "New source added:\n-----------------------------------\n%1$s";
    public static final String MESSAGE_DUPLICATE_SOURCE = "This source already exists in the database.";

    private final Source toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Source source) {
        requireNonNull(source);
        toAdd = source;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasSource(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SOURCE);
        }

        model.addSource(toAdd);
        model.commitSourceManager();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    @Override
    public int hashCode() {
        return toAdd.toString().hashCode();
    }
}
