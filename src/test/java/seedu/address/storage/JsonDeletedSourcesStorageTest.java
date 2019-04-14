package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalSources.ALGORITHM_RESEARCH;
import static seedu.address.testutil.TypicalSources.PROGRAMMING;
import static seedu.address.testutil.TypicalSources.RESEARCH_METHOD;
import static seedu.address.testutil.TypicalSources.STRUCTURE;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DeletedSources;
import seedu.address.model.ReadOnlyDeletedSources;

public class JsonDeletedSourcesStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonDeletedSourcesStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readDeletedSources_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readDeletedSources(null);
    }

    private java.util.Optional<ReadOnlyDeletedSources> readDeletedSources(String filePath) throws Exception {
        return new JsonDeletedSourcesStorage(Paths.get(filePath))
                .readDeletedSources(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDeletedSources("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readDeletedSources("notJsonFormatDeletedSources.json");

        // IMPORTANT: Any code below an exceptions-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exceptions test in one method
    }

    @Test
    public void readDeletedSources_invalidSourceDeletedSources_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDeletedSources("invalidSourceDeletedSources.json");
    }

    @Test
    public void readDeletedSources_invalidAndValidSourceDeletedSources_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDeletedSources("invalidAndValidSourceDeletedSources.json");
    }

    @Test
    public void readAndSaveDeletedSources_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempDeletedSources.json");
        DeletedSources original = getTypicalDeletedSources();
        JsonDeletedSourcesStorage jsonDeletedSourcesStorage = new JsonDeletedSourcesStorage(filePath);

        // Save in new file and read back
        jsonDeletedSourcesStorage.saveDeletedSources(original, filePath);
        ReadOnlyDeletedSources readBack = jsonDeletedSourcesStorage.readDeletedSources(filePath).get();
        assertEquals(original, new DeletedSources(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDeletedSource(STRUCTURE);
        original.removeDeletedSource(RESEARCH_METHOD);
        jsonDeletedSourcesStorage.saveDeletedSources(original, filePath);
        readBack = jsonDeletedSourcesStorage.readDeletedSources(filePath).get();
        assertEquals(original, new DeletedSources(readBack));

        // Save and read without specifying file path
        original.addDeletedSource(PROGRAMMING);
        jsonDeletedSourcesStorage.saveDeletedSources(original); // file path not specified
        readBack = jsonDeletedSourcesStorage.readDeletedSources().get(); // file path not specified
        assertEquals(original, new DeletedSources(readBack));

    }

    @Test
    public void saveDeletedSources_nullDeletedSources_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDeletedSources(null, "SomeFile.json");
    }

    /**
     * Saves {@code sourceManager} at the specified {@code filePath}.
     */
    private void saveDeletedSources(ReadOnlyDeletedSources deletedSources, String filePath) {
        try {
            new JsonDeletedSourcesStorage(Paths.get(filePath))
                    .saveDeletedSources(deletedSources, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDeletedSources_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDeletedSources(new DeletedSources(), null);
    }
}
