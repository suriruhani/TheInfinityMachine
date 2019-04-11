package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalSources.ALGORITHM_RESEARCH;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.source.Source;
import seedu.address.model.source.exceptions.DuplicateSourceException;
import seedu.address.testutil.SourceBuilder;

public class SourceManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final SourceManager sourceManager = new SourceManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), sourceManager.getSourceList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        sourceManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlySourceManager_replacesData() {
        SourceManager newData = getTypicalSourceManager();
        sourceManager.resetData(newData);
        assertEquals(newData, sourceManager);
    }

    @Test
    public void resetData_withDuplicateSources_throwsDuplicateSourceException() {
        // Two sources with the same fields
        Source editedAlgorithm = new SourceBuilder(ALGORITHM_RESEARCH)
                .withDetail("A research about researchers on algorithms.").withTags("research").build();
        List<Source> newSources = Arrays.asList(ALGORITHM_RESEARCH, editedAlgorithm);
        SourceManagerStub newData = new SourceManagerStub(newSources);

        thrown.expect(DuplicateSourceException.class);
        sourceManager.resetData(newData);
    }

    @Test
    public void hasSource_nullSource_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        sourceManager.hasSource(null);
    }

    @Test
    public void hasSource_sourceNotInSourceManager_returnsFalse() {
        assertFalse(sourceManager.hasSource(ALGORITHM_RESEARCH));
    }

    @Test
    public void hasSource_sourceInSourceManager_returnsTrue() {
        sourceManager.addSource(ALGORITHM_RESEARCH);
        assertTrue(sourceManager.hasSource(ALGORITHM_RESEARCH));
    }

    @Test
    public void hasSource_sourceWithSameFieldsInSourceManager_returnsTrue() {
        sourceManager.addSource(ALGORITHM_RESEARCH);
        Source editedAlgorithm = new SourceBuilder(ALGORITHM_RESEARCH)
                .withDetail("A research about researchers on algorithms.").withTags("research").build();
        assertTrue(sourceManager.hasSource(editedAlgorithm));
    }

    @Test
    public void getSourceList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        sourceManager.getSourceList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        sourceManager.addListener(listener);
        sourceManager.addSource(ALGORITHM_RESEARCH);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        sourceManager.addListener(listener);
        sourceManager.removeListener(listener);
        sourceManager.addSource(ALGORITHM_RESEARCH);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlySourceManager whose sources list can violate interface constraints.
     */
    private static class SourceManagerStub implements ReadOnlySourceManager {
        private final ObservableList<Source> sources = FXCollections.observableArrayList();

        SourceManagerStub(Collection<Source> sources) {
            this.sources.setAll(sources);
        }

        @Override
        public ObservableList<Source> getSourceList() {
            return sources;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
