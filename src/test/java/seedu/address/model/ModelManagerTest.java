package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;
import static seedu.address.testutil.TypicalSources.ALICE;
import static seedu.address.testutil.TypicalSources.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.source.Source;
import seedu.address.model.source.exceptions.SourceNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
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
    public void hasSource_nullSource_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasSource(null);
    }

    @Test
    public void hasSource_sourceNotInSourceManager_returnsFalse() {
        assertFalse(modelManager.hasSource(ALICE));
    }

    @Test
    public void hasSource_sourceInSourceManager_returnsTrue() {
        modelManager.addSource(ALICE);
        assertTrue(modelManager.hasSource(ALICE));
    }

    @Test
    public void deleteSource_sourceIsSelectedAndFirstSourceInFilteredSourceList_selectionCleared() {
        modelManager.addSource(ALICE);
        modelManager.setSelectedSource(ALICE);
        modelManager.deleteSource(ALICE);
        assertEquals(null, modelManager.getSelectedSource());
    }

    @Test
    public void deleteSource_sourceIsSelectedAndSecondSourceInFilteredSourceList_firstSourceSelected() {
        modelManager.addSource(ALICE);
        modelManager.addSource(BENSON);
        assertEquals(Arrays.asList(ALICE, BENSON), modelManager.getFilteredSourceList());
        modelManager.setSelectedSource(BENSON);
        modelManager.deleteSource(BENSON);
        assertEquals(ALICE, modelManager.getSelectedSource());
    }

    @Test
    public void setSource_sourceIsSelected_selectedSourceUpdated() {
        modelManager.addSource(ALICE);
        modelManager.setSelectedSource(ALICE);
        Source updatedAlice = new SourceBuilder(ALICE).withType("foo").build();
        modelManager.setSource(ALICE, updatedAlice);
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
        modelManager.setSelectedSource(ALICE);
    }

    @Test
    public void setSelectedSource_sourceInFilteredSourceList_setsSelectedSource() {
        modelManager.addSource(ALICE);
        assertEquals(Collections.singletonList(ALICE), modelManager.getFilteredSourceList());
        modelManager.setSelectedSource(ALICE);
        assertEquals(ALICE, modelManager.getSelectedSource());
    }

    @Test
    public void equals() {
        SourceManager sourceManager = new SourceManagerBuilder().withSource(ALICE).withSource(BENSON).build();
        SourceManager differentSourceManager = new SourceManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(sourceManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(sourceManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentSourceManager, userPrefs)));

        // Temporarily comment out test for migration from address book to source manager.
        // Todo: Rewrite test in a way that makes sense for source manager.

//        // different filteredList -> returns false
//        String[] keywords = ALICE.getName().fullName.split("\\s+");
//        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
//        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSourceManagerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(sourceManager, differentUserPrefs)));
    }
}
