package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.SourceManager;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonSourceManagerStorage sourceManagerStorage = new JsonSourceManagerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonDeletedSourcesStorage deletedSourcesStorage = new JsonDeletedSourcesStorage(getTempFilePath("ds"));
        storageManager = new StorageManager(sourceManagerStorage, userPrefsStorage, deletedSourcesStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void sourceManagerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonSourceManagerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonSourceManagerStorageTest} class.
         */
        SourceManager original = getTypicalSourceManager();
        storageManager.saveSourceManager(original);
        ReadOnlySourceManager retrieved = storageManager.readSourceManager().get();
        assertEquals(original, new SourceManager(retrieved));
    }

    @Test
    public void getSourceManagerFilePath() {
        assertNotNull(storageManager.getSourceManagerFilePath());
    }

}
