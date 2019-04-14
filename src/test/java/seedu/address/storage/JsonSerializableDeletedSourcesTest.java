package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DeletedSources;
import seedu.address.testutil.TypicalSources;

public class JsonSerializableDeletedSourcesTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableDeletedSourcesTest");
    private static final Path TYPICAL_DELETED_SOURCE_FILE =
            TEST_DATA_FOLDER.resolve("typicalSourcesDeletedSources.json");
    private static final Path INVALID_DELETED_SOURCE_FILE =
            TEST_DATA_FOLDER.resolve("invalidSourceDeletedSources.json");
    private static final Path DUPLICATE_DELETED_SOURCE_FILE =
            TEST_DATA_FOLDER.resolve("duplicateSourceDeletedSources.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalSourcesFile_success() throws Exception {
        JsonSerializableDeletedSources dataFromFile = JsonUtil.readJsonFile(TYPICAL_DELETED_SOURCE_FILE,
                JsonSerializableDeletedSources.class).get();
        DeletedSources deletedSourcesFromFile = dataFromFile.toModelType();
        DeletedSources typicalSourcesDeletedSources = TypicalSources.getTypicalDeletedSources();
        assertEquals(deletedSourcesFromFile, typicalSourcesDeletedSources);
    }

    @Test
    public void toModelType_invalidSourceFile_throwsIllegalValueException() throws Exception {
        JsonSerializableDeletedSources dataFromFile = JsonUtil.readJsonFile(INVALID_DELETED_SOURCE_FILE,
                JsonSerializableDeletedSources.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateSources_throwsIllegalValueException() throws Exception {
        JsonSerializableDeletedSources dataFromFile = JsonUtil.readJsonFile(DUPLICATE_DELETED_SOURCE_FILE,
                JsonSerializableDeletedSources.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableDeletedSources.MESSAGE_DUPLICATE_SOURCE);
        dataFromFile.toModelType();
    }

}
