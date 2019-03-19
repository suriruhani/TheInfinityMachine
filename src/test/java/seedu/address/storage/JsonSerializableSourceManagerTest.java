package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

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
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidSourcesourceManager.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateSourcesourceManager.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalSourcesFile_success() throws Exception {
        JsonSerializableSourceManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_SOURCES_FILE,
                JsonSerializableSourceManager.class).get();
        SourceManager sourceManagerFromFile = dataFromFile.toModelType();
        SourceManager typicalSourcesSourceManager = TypicalSources.getTypicalSourceManager();
        assertEquals(sourceManagerFromFile, typicalSourcesSourceManager);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableSourceManager dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableSourceManager.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateSources_throwsIllegalValueException() throws Exception {
        JsonSerializableSourceManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableSourceManager.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableSourceManager.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
