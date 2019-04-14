package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.source.Author;
import seedu.address.model.source.BiblioFields;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing source in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the source identified "
            + "by the index number used in the displayed source list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_TYPE + "TYPE] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_DETAILS + "DETAILS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "Algorithms IEEE "
            + PREFIX_DETAILS + "Basic algorithm controls and processes";

    public static final String MESSAGE_EDIT_SOURCE_SUCCESS = "Edited Source:\n--------------------------"
            + "---------\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SOURCE = "This source already exists in the database.";

    private final Index index;
    private final EditSourceDescriptor editSourceDescriptor;

    /**
     * @param index of the source in the filtered source list to edit
     * @param editSourceDescriptor details to edit the source with
     */
    public EditCommand(Index index, EditSourceDescriptor editSourceDescriptor) {
        requireNonNull(index);
        requireNonNull(editSourceDescriptor);

        this.index = index;
        this.editSourceDescriptor = new EditSourceDescriptor(editSourceDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Source> lastShownList = model.getFilteredSourceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        Source sourceToEdit = lastShownList.get(index.getZeroBased());
        Source editedSource = createEditedSource(sourceToEdit, editSourceDescriptor);

        if (!sourceToEdit.isSameSource(editedSource) && model.hasSource(editedSource)) {
            throw new CommandException(MESSAGE_DUPLICATE_SOURCE);
        }

        model.setSource(sourceToEdit, editedSource);
        model.updateFilteredSourceList(Model.PREDICATE_SHOW_ALL_SOURCES);
        model.commitSourceManager();
        return new CommandResult(String.format(MESSAGE_EDIT_SOURCE_SUCCESS, editedSource));
    }

    /**
     * Creates and returns a {@code Source} with the details of {@code sourceToEdit}
     * edited with {@code editSourceDescriptor}.
     */
    private static Source createEditedSource(Source sourceToEdit, EditSourceDescriptor editSourceDescriptor) {
        assert sourceToEdit != null;

        Title updatedTitle = editSourceDescriptor.getTitle().orElse(sourceToEdit.getTitle());
        Type updatedType = editSourceDescriptor.getType().orElse(sourceToEdit.getType());
        Author updatedAuthor = editSourceDescriptor.getAuthor().orElse(sourceToEdit.getAuthor());
        Detail updatedDetails = editSourceDescriptor.getDetails().orElse(sourceToEdit.getDetail());
        Set<Tag> updatedTags = editSourceDescriptor.getTags().orElse(sourceToEdit.getTags());
        BiblioFields biblioFields = sourceToEdit.getBiblioFields();

        return new Source(updatedTitle, updatedAuthor, updatedType, updatedDetails, updatedTags, biblioFields);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editSourceDescriptor.equals(e.editSourceDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditSourceDescriptor {
        private Title title;
        private Type type;
        private Author author;
        private Detail details;
        private Set<Tag> tags;

        public EditSourceDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditSourceDescriptor(EditSourceDescriptor toCopy) {
            setTitle(toCopy.title);
            setType(toCopy.type);
            setAuthor(toCopy.author);
            setDetails(toCopy.details);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, type, author, details, tags);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public Optional<Author> getAuthor() {
            return Optional.ofNullable(author);
        }

        public void setDetails(Detail details) {
            this.details = details;
        }

        public Optional<Detail> getDetails() {
            return Optional.ofNullable(details);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditSourceDescriptor)) {
                return false;
            }

            // state check
            EditSourceDescriptor e = (EditSourceDescriptor) other;

            return getTitle().equals(e.getTitle())
                    && getType().equals(e.getType())
                    && getAuthor().equals(e.getAuthor())
                    && getDetails().equals(e.getDetails())
                    && getTags().equals(e.getTags());
        }
    }
}
