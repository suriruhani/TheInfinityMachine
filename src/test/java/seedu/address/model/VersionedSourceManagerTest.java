package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalSources.ALICE;
import static seedu.address.testutil.TypicalSources.BENSON;
import static seedu.address.testutil.TypicalSources.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.SourceManagerBuilder;

public class VersionedSourceManagerTest {

    private final ReadOnlySourceManager sourceManagerWithAlice = new SourceManagerBuilder().withSource(ALICE).build();
    private final ReadOnlySourceManager sourceManagerWithBenson = new SourceManagerBuilder().withSource(BENSON).build();
    private final ReadOnlySourceManager sourceManagerWithCarl = new SourceManagerBuilder().withSource(CARL).build();
    private final ReadOnlySourceManager emptySourceManager = new SourceManagerBuilder().build();

    @Test
    public void commit_singleSourceManager_noStatesRemovedCurrentStateSaved() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(emptySourceManager);

        versionedSourceManager.commit();
        assertSourceManagerListStatus(versionedSourceManager,
                Collections.singletonList(emptySourceManager),
                emptySourceManager,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleSourceManagerPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);

        versionedSourceManager.commit();
        assertSourceManagerListStatus(versionedSourceManager,
                Arrays.asList(emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson),
                sourceManagerWithBenson,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleSourceManagerPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 2);

        versionedSourceManager.commit();
        assertSourceManagerListStatus(versionedSourceManager,
                Collections.singletonList(emptySourceManager),
                emptySourceManager,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleSourceManagerPointerAtEndOfStateList_returnsTrue() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);

        assertTrue(versionedSourceManager.canUndo());
    }

    @Test
    public void canUndo_multipleSourceManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 1);

        assertTrue(versionedSourceManager.canUndo());
    }

    @Test
    public void canUndo_singleSourceManager_returnsFalse() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(emptySourceManager);

        assertFalse(versionedSourceManager.canUndo());
    }

    @Test
    public void canUndo_multipleSourceManagerPointerAtStartOfStateList_returnsFalse() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 2);

        assertFalse(versionedSourceManager.canUndo());
    }

    @Test
    public void canRedo_multipleSourceManagerPointerNotAtEndOfStateList_returnsTrue() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 1);

        assertTrue(versionedSourceManager.canRedo());
    }

    @Test
    public void canRedo_multipleSourceManagerPointerAtStartOfStateList_returnsTrue() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 2);

        assertTrue(versionedSourceManager.canRedo());
    }

    @Test
    public void canRedo_singleSourceManager_returnsFalse() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(emptySourceManager);

        assertFalse(versionedSourceManager.canRedo());
    }

    @Test
    public void canRedo_multipleSourceManagerPointerAtEndOfStateList_returnsFalse() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);

        assertFalse(versionedSourceManager.canRedo());
    }

    @Test
    public void undo_multipleSourceManagerPointerAtEndOfStateList_success() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);

        versionedSourceManager.undo();
        assertSourceManagerListStatus(versionedSourceManager,
                Collections.singletonList(emptySourceManager),
                sourceManagerWithAlice,
                Collections.singletonList(sourceManagerWithBenson));
    }

    @Test
    public void undo_multipleSourceManagerPointerNotAtStartOfStateList_success() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 1);

        versionedSourceManager.undo();
        assertSourceManagerListStatus(versionedSourceManager,
                Collections.emptyList(),
                emptySourceManager,
                Arrays.asList(sourceManagerWithAlice, sourceManagerWithBenson));
    }

    @Test
    public void undo_singleSourceManager_throwsNoUndoableStateException() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(emptySourceManager);

        assertThrows(VersionedSourceManager.NoUndoableStateException.class, versionedSourceManager::undo);
    }

    @Test
    public void undo_multipleSourceManagerPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 2);

        assertThrows(VersionedSourceManager.NoUndoableStateException.class, versionedSourceManager::undo);
    }

    @Test
    public void redo_multipleSourceManagerPointerNotAtEndOfStateList_success() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 1);

        versionedSourceManager.redo();
        assertSourceManagerListStatus(versionedSourceManager,
                Arrays.asList(emptySourceManager, sourceManagerWithAlice),
                sourceManagerWithBenson,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleSourceManagerPointerAtStartOfStateList_success() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 2);

        versionedSourceManager.redo();
        assertSourceManagerListStatus(versionedSourceManager,
                Collections.singletonList(emptySourceManager),
                sourceManagerWithAlice,
                Collections.singletonList(sourceManagerWithBenson));
    }

    @Test
    public void redo_singleSourceManager_throwsNoRedoableStateException() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(emptySourceManager);

        assertThrows(VersionedSourceManager.NoRedoableStateException.class, versionedSourceManager::redo);
    }

    @Test
    public void redo_multipleSourceManagerPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedSourceManager versionedSourceManager = prepareSourceManagerList(
                emptySourceManager, sourceManagerWithAlice, sourceManagerWithBenson);

        assertThrows(VersionedSourceManager.NoRedoableStateException.class, versionedSourceManager::redo);
    }

    @Test
    public void equals() {
        VersionedSourceManager versionedSourceManager =
                prepareSourceManagerList(sourceManagerWithAlice, sourceManagerWithBenson);

        // same values -> returns true
        VersionedSourceManager copy = prepareSourceManagerList(sourceManagerWithAlice, sourceManagerWithBenson);
        assertTrue(versionedSourceManager.equals(copy));

        // same object -> returns true
        assertTrue(versionedSourceManager.equals(versionedSourceManager));

        // null -> returns false
        assertFalse(versionedSourceManager.equals(null));

        // different types -> returns false
        assertFalse(versionedSourceManager.equals(1));

        // different state list -> returns false
        VersionedSourceManager differentSourceManagerList =
                prepareSourceManagerList(sourceManagerWithBenson, sourceManagerWithCarl);
        assertFalse(versionedSourceManager.equals(differentSourceManagerList));

        // different current pointer index -> returns false
        VersionedSourceManager differentCurrentStatePointer = prepareSourceManagerList(
                sourceManagerWithAlice, sourceManagerWithBenson);
        shiftCurrentStatePointerLeftwards(versionedSourceManager, 1);
        assertFalse(versionedSourceManager.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedSourceManager}
     * is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedSourceManager#currentStatePointer}
     * is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedSourceManager#currentStatePointer}
     * is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertSourceManagerListStatus(VersionedSourceManager versionedSourceManager,
                                               List<ReadOnlySourceManager> expectedStatesBeforePointer,
                                               ReadOnlySourceManager expectedCurrentState,
                                               List<ReadOnlySourceManager> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new SourceManager(versionedSourceManager), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedSourceManager.canUndo()) {
            versionedSourceManager.undo();
        }

        // check states before pointer are correct
        for (ReadOnlySourceManager expectedSourceManager : expectedStatesBeforePointer) {
            assertEquals(expectedSourceManager, new SourceManager(versionedSourceManager));
            versionedSourceManager.redo();
        }

        // check states after pointer are correct
        for (ReadOnlySourceManager expectedSourceManager : expectedStatesAfterPointer) {
            versionedSourceManager.redo();
            assertEquals(expectedSourceManager, new SourceManager(versionedSourceManager));
        }

        // check that there are no more states after pointer
        assertFalse(versionedSourceManager.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedSourceManager.undo());
    }

    /**
     * Creates and returns a {@code VersionedSourceManager} with the {@code sourceManagerStates} added into it, and the
     * {@code VersionedSourceManager#currentStatePointer} at the end of list.
     */
    private VersionedSourceManager prepareSourceManagerList(ReadOnlySourceManager... sourceManagerStates) {
        assertFalse(sourceManagerStates.length == 0);

        VersionedSourceManager versionedSourceManager = new VersionedSourceManager(sourceManagerStates[0]);
        for (int i = 1; i < sourceManagerStates.length; i++) {
            versionedSourceManager.resetData(sourceManagerStates[i]);
            versionedSourceManager.commit();
        }

        return versionedSourceManager;
    }

    /**
     * Shifts the {@code versionedSourceManager#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedSourceManager versionedSourceManager, int count) {
        for (int i = 0; i < count; i++) {
            versionedSourceManager.undo();
        }
    }
}
