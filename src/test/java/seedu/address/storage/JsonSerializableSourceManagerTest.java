package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.SourceManager;
import seedu.address.testutil.TypicalSources;

public class JsonSerializableSourceManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableSourceManagerTest");
    private static final Path TYPICAL_SOURCES_FILE = TEST_DATA_FOLDER.resolve("typicalSourcesSourceManager.json");
    private static final Path INVALID_SOURCE_FILE = TEST_DATA_FOLDER.resolve("invalidSourceSourceManager.json");
    private static final Path DUPLICATE_SOURCE_FILE = TEST_DATA_FOLDER.resolve("duplicateSourceSourceManager.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Ignore
    @Test
    public void toModelType_typicalSourcesFile_success() throws Exception {
        JsonSerializableSourceManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_SOURCES_FILE,
                JsonSerializableSourceManager.class).get();
        SourceManager sourceManagerFromFile = dataFromFile.toModelType();
        SourceManager typicalSourcesSourceManager = TypicalSources.getTypicalSourceManager();
        assertEquals(sourceManagerFromFile, typicalSourcesSourceManager);
    }

    @Test
    public void toModelType_invalidSourceFile_throwsIllegalValueException() throws Exception {
        JsonSerializableSourceManager dataFromFile = JsonUtil.readJsonFile(INVALID_SOURCE_FILE,
                JsonSerializableSourceManager.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateSources_throwsIllegalValueException() throws Exception {
        JsonSerializableSourceManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_SOURCE_FILE,
                JsonSerializableSourceManager.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableSourceManager.MESSAGE_DUPLICATE_SOURCE);
        dataFromFile.toModelType();
    }

}
