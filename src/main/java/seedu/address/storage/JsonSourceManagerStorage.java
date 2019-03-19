package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlySourceManager;

/**
 * A class to access Source Manager data stored as a json file on the hard disk.
 */
public class JsonSourceManagerStorage implements SourceManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSourceManagerStorage.class);

    private Path filePath;

    public JsonSourceManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSourceManagerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySourceManager> readSourceManager() throws DataConversionException {
        return readSourceManager(filePath);
    }

    /**
     * Similar to {@link #readSourceManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySourceManager> readSourceManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSourceManager> jsonSourceManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableSourceManager.class);
        if (!jsonSourceManager.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSourceManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSourceManager(ReadOnlySourceManager sourceManager) throws IOException {
        saveSourceManager(sourceManager, filePath);
    }

    /**
     * Similar to {@link #saveSourceManager(ReadOnlySourceManager, Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSourceManager(ReadOnlySourceManager sourceManager, Path filePath) throws IOException {
        requireNonNull(sourceManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSourceManager(sourceManager), filePath);
    }

}
