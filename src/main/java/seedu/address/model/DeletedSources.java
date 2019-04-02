package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.source.Source;
import seedu.address.model.source.UniqueDeletedSourceList;

/**
 * Wraps all data and operations related to DeletedSource objects
 * Duplicates are not allowed (by .isSameSource comparison)
 */
public class DeletedSources implements ReadOnlyDeletedSources {


    private final UniqueDeletedSourceList deletedSources;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    {
        deletedSources = new UniqueDeletedSourceList();
    }

    public DeletedSources() {}

    /**
     * Creates a DeletedSources using the Sources in the {@code toBeCopied}
     */
    public DeletedSources(ReadOnlyDeletedSources toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the source list with {@code sources}.
     * {@code sources} must not contain duplicate sources.
     */
    public void setDeletedSources(List<Source> sources) {
        this.deletedSources.setSources(sources);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code DeletedSources} with {@code newData}.
     */
    public void resetData(ReadOnlyDeletedSources newData) {
        requireNonNull(newData);

        setDeletedSources(newData.getDeletedSourceList());
    }

    //// deletedsource-level operations

    /**
     * Returns true if a source with the same identity as {@code source} exists in the deleted sources.
     */
    public boolean hasDeletedSource(Source source) {
        requireNonNull(source);
        return deletedSources.contains(source);
    }

    /**
     * Adds a source to the deleted source list.
     * The source must not already exist in the deleted source list.
     */
    public void addDeletedSource(Source source) {
        deletedSources.add(source);
        indicateModified();
    }

    /**
     * Adds a source to the deleted sources list.
     * The source must not already exist in the deleted sources list.
     */
    public void addDeletedSourceAtIndex(Source s, int index) {
        deletedSources.addAtIndex(s, index);
        indicateModified();
    }

    /**
     * Replaces the given source {@code target} in the list with {@code editedSource}.
     * {@code target} must exist in the address book.
     * The source identity of {@code editedSource} must not be the same as another existing source
     * in the deleted sources list.
     */
    public void setDeletedSource(Source target, Source editSource) {
        requireNonNull(editSource);

        deletedSources.setSource(target, editSource);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code DeletedSources}.
     * {@code key} must exist in the deleted source list.
     */
    public void removeDeletedSource(Source key) {
        deletedSources.remove(key);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the deleted source list has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return deletedSources.asUnmodifiableObservableList().size() + " sources";
        // TODO: refine later
    }

    @Override
    public ObservableList<Source> getDeletedSourceList() {
        return deletedSources.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletedSources // instanceof handles nulls
                && deletedSources.equals(((DeletedSources) other).deletedSources));
    }

    @Override
    public int hashCode() {
        return deletedSources.hashCode();
    }
}
