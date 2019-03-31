package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.source.Source;

/**
 * Unmodifiable view of deleted sources.
 */
public interface ReadOnlyDeletedSources extends Observable {


    /**
     * Returns an unmodifiable view of the deleted source list.
     * This list will not contain any duplicate sources.
     */
    ObservableList<Source> getDeletedSourceList();

}

