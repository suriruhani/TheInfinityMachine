package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code SourceManager} that keeps track of its own history.
 */
public class VersionedSourceManager extends SourceManager {

    private final List<ReadOnlySourceManager> sourceManagerStateList;
    private int currentStatePointer;

    public VersionedSourceManager(ReadOnlySourceManager initialState) {
        super(initialState);

        sourceManagerStateList = new ArrayList<>();
        sourceManagerStateList.add(new SourceManager(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code SourceManager} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        sourceManagerStateList.add(new SourceManager(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        sourceManagerStateList.subList(currentStatePointer + 1, sourceManagerStateList.size()).clear();
    }

    /**
     * Restores the source manager to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(sourceManagerStateList.get(currentStatePointer));
    }

    /**
     * Restores the source manager to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(sourceManagerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has source manager states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has source manager states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < sourceManagerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedSourceManager)) {
            return false;
        }

        VersionedSourceManager otherVersionedSourceManager = (VersionedSourceManager) other;

        // state check
        return super.equals(otherVersionedSourceManager)
                && sourceManagerStateList.equals(otherVersionedSourceManager.sourceManagerStateList)
                && currentStatePointer == otherVersionedSourceManager.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of sourceManagerState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of sourceManagerState list, unable to redo.");
        }
    }
}
