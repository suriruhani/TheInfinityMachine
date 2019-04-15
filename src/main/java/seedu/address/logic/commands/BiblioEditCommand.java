package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.source.BiblioFields;
import seedu.address.model.source.Source;

/**
 * Deletes a source identified using it's displayed index from the source manager.
 */
public class BiblioEditCommand extends Command {

    public static final String COMMAND_WORD = "biblioEdit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Replaces the biblio field indicated by the header in the selected source.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + "HEADER (must be a non-empty string)\n"
            + "   Furthermore, HEADER must be one of the following:\n"
            + "   " + Arrays.toString(BiblioFields.ACCEPTED_FIELD_HEADERS) + "\n"
            + "BODY (must be a non-empty string)\n"
            + "Example: " + COMMAND_WORD + " 1 City London";

    public static final String MESSAGE_BIBLIO_EDIT_SUCCESS = "Field Successfully modified:\n";
    public static final String MESSAGE_BIBLIO_EDIT_FAILURE = "Something went wrong!";
    private final Index targetIndex;
    private final String header;
    private final String body;

    public BiblioEditCommand(Index targetIndex, String header, String body) {
        this.targetIndex = targetIndex;
        this.header = header;
        this.body = body;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.switchToSources(); // sets source manager data to list
        List<Source> lastShownList = model.getFilteredSourceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        Source sourceToEdit = lastShownList.get(targetIndex.getZeroBased());
        BiblioFields editedFields = sourceToEdit.getBiblioFields().replaceField(header, body);

        if (editedFields != null) {
            Source editedSource = new Source(sourceToEdit.getTitle(), sourceToEdit.getAuthor(), sourceToEdit.getType(),
                    sourceToEdit.getDetail(), sourceToEdit.getTags(), editedFields);
            model.setSource(sourceToEdit, editedSource);
            model.updateFilteredSourceList(Model.PREDICATE_SHOW_ALL_SOURCES);
            model.commitSourceManager();

            return new CommandResult(MESSAGE_BIBLIO_EDIT_SUCCESS + editedSource.getBiblioFields());
        } else {
            throw new CommandException(MESSAGE_BIBLIO_EDIT_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BiblioEditCommand // instanceof handles nulls
                && targetIndex.equals(((BiblioEditCommand) other).targetIndex)
                && header.equals(((BiblioEditCommand) other).header)
                && body.equals(((BiblioEditCommand) other).body)); // state check
    }
}
