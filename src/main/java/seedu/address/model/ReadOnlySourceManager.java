package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.source.Source;

/**
 * Unmodifiable view of a source manager
 */
public interface ReadOnlySourceManager extends Observable {

    /**
     * Returns an unmodifiable view of the source list.
     * This list will not contain any duplicate sources.
     */
    ObservableList<Source> getSourceList();

}
