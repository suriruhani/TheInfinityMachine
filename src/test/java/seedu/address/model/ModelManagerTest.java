package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;
import static seedu.address.testutil.TypicalSources.ALGORITHM_RESEARCH;
import static seedu.address.testutil.TypicalSources.SENSOR_RESEARCH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.source.Source;
import seedu.address.model.source.exceptions.SourceNotFoundException;
import seedu.address.testutil.SourceBuilder;
import seedu.address.testutil.SourceManagerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new SourceManager(), new SourceManager(modelManager.getSourceManager()));
        assertEquals(null, modelManager.getSelectedSource());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setUserPrefs(null);
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setSourceManagerFilePath(Paths.get("source/manager/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setSourceManagerFilePath(Paths.get("new/source/manager/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setGuiSettings(null);
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setSourceManagerFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setSourceManagerFilePath(null);
    }

    @Test
    public void setSourceManagerFilePath_validPath_setsSourceManagerFilePath() {
        Path path = Paths.get("source/manager/file/path");
        modelManager.setSourceManagerFilePath(path);
        assertEquals(path, modelManager.getSourceManagerFilePath());
    }

    @Test
    public void setDeletedSourcesFilePath_nullPath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setDeletedSourceFilePath(null);
    }

    @Test
    public void setDeleteManagerFilePath_validPath_setsDeleteManagerFilePath() {
        Path path = Paths.get("source/manager/file/path");
        modelManager.setDeletedSourceFilePath(path);
        assertEquals(path, modelManager.getDeletedSourceFilePath());
    }

    @Test
    public void hasSource_nullSource_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasSource(null);
    }

    @Test
    public void hasSource_sourceNotInSourceManager_returnsFalse() {
        assertFalse(modelManager.hasSource(ALGORITHM_RESEARCH));
    }

    @Test
    public void hasSource_sourceInSourceManager_returnsTrue() {
        modelManager.addSource(ALGORITHM_RESEARCH);
        assertTrue(modelManager.hasSource(ALGORITHM_RESEARCH));
    }

    @Test
    public void deleteSource_sourceIsSelectedAndFirstSourceInFilteredSourceList_selectionCleared() {
        modelManager.addSource(ALGORITHM_RESEARCH);
        modelManager.setSelectedSource(ALGORITHM_RESEARCH);
        modelManager.deleteSource(ALGORITHM_RESEARCH);
        assertEquals(null, modelManager.getSelectedSource());
    }

    @Test
    public void deleteSource_sourceIsSelectedAndSecondSourceInFilteredSourceList_firstSourceSelected() {
        modelManager.addSource(ALGORITHM_RESEARCH);
        modelManager.addSource(SENSOR_RESEARCH);
        assertEquals(Arrays.asList(ALGORITHM_RESEARCH, SENSOR_RESEARCH), modelManager.getFilteredSourceList());
        modelManager.setSelectedSource(SENSOR_RESEARCH);
        modelManager.deleteSource(SENSOR_RESEARCH);
        assertEquals(ALGORITHM_RESEARCH, modelManager.getSelectedSource());
    }

    @Test
    public void setSource_sourceIsSelected_selectedSourceUpdated() {
        modelManager.addSource(ALGORITHM_RESEARCH);
        modelManager.setSelectedSource(ALGORITHM_RESEARCH);
        Source updatedAlice = new SourceBuilder(ALGORITHM_RESEARCH).withType("foo").build();
        modelManager.setSource(ALGORITHM_RESEARCH, updatedAlice);
        assertEquals(updatedAlice, modelManager.getSelectedSource());
    }

    @Test
    public void getFilteredSourceList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredSourceList().remove(0);
    }

    @Test
    public void setSelectedSource_sourceNotInFilteredSourceList_throwsSourceNotFoundException() {
        thrown.expect(SourceNotFoundException.class);
        modelManager.setSelectedSource(ALGORITHM_RESEARCH);
    }

    @Test
    public void setSelectedSource_sourceInFilteredSourceList_setsSelectedSource() {
        modelManager.addSource(ALGORITHM_RESEARCH);
        assertEquals(Collections.singletonList(ALGORITHM_RESEARCH), modelManager.getFilteredSourceList());
        modelManager.setSelectedSource(ALGORITHM_RESEARCH);
        assertEquals(ALGORITHM_RESEARCH, modelManager.getSelectedSource());
    }

    @Test
    public void equals() {
        SourceManager sourceManager = new SourceManagerBuilder().withSource(ALGORITHM_RESEARCH)
                .withSource(SENSOR_RESEARCH).build();
        SourceManager differentSourceManager = new SourceManager();
        UserPrefs userPrefs = new UserPrefs();
        DeletedSources differentDeletedSources = new DeletedSources();

        // same values -> returns true
        modelManager = new ModelManager(sourceManager, userPrefs, differentDeletedSources);
        ModelManager modelManagerCopy = new ModelManager(sourceManager, userPrefs, differentDeletedSources);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(
                differentSourceManager,
                userPrefs,
                differentDeletedSources,
                0)));

        // Temporarily comment out test for migration from address book to source manager.
        // Todo: Rewrite test in a way that makes sense for source manager.

        // different filteredList -> returns false
        // String[] keywords = ALGORITHM_RESEARCH.getTitle().fullName.split("\\s+");
        // modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        // assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSourceManagerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(sourceManager, differentUserPrefs, differentDeletedSources)));
    }
}
