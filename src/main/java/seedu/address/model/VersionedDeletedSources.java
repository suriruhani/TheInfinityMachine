package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DeletedSources} that keeps track of its own history.
 */
public class VersionedDeletedSources extends DeletedSources {

    private final List<ReadOnlyDeletedSources> deletedSourcesStateList;
    private int currentStatePointer;

    public VersionedDeletedSources(ReadOnlyDeletedSources initialState) {
        super(initialState);

        deletedSourcesStateList = new ArrayList<>();
        deletedSourcesStateList.add(new DeletedSources(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code DeletedSources} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        deletedSourcesStateList.add(new DeletedSources(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        deletedSourcesStateList.subList(currentStatePointer + 1, deletedSourcesStateList.size()).clear();
    }

    /**
     * Restores the deleted sources list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(deletedSourcesStateList.get(currentStatePointer));
    }

    /**
     * Restores the deleted sources to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(deletedSourcesStateList.get(currentStatePointer));
    }
    /**
     * Returns true if {@code undo()} has deleted sources states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has deleted sources states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < deletedSourcesStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedDeletedSources)) {
            return false;
        }

        VersionedDeletedSources otherVersionedDeletedSources = (VersionedDeletedSources) other;

        // state check
        return super.equals(otherVersionedDeletedSources)
                && deletedSourcesStateList.equals(otherVersionedDeletedSources.deletedSourcesStateList)
                && currentStatePointer == otherVersionedDeletedSources.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of deletedSourcesState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of deletedSourcesState list, unable to redo.");
        }
    }
}
