package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DeletedSources;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class JsonDeletedSourcesStorage implements DeletedSourcesStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonDeletedSourcesStorage.class);

    private Path filePath;

    public JsonDeletedSourcesStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDeletedSourceFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyDeletedSources> readDeletedSources() throws DataConversionException {
        return readDeletedSources(filePath);
    }

    /**
     * Similar to {@link #readDeletedSources()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyDeletedSources> readDeletedSources(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDeletedSources> jsonDeletedSources = JsonUtil.readJsonFile(
                filePath, JsonSerializableDeletedSources.class);
        if (!jsonDeletedSources.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonDeletedSources.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveDeletedSources(ReadOnlyDeletedSources deletedSources) throws IOException {
        saveDeletedSources(deletedSources, filePath);
    }

    /**
     * Similar to {@link #saveDeletedSources(ReadOnlyDeletedSources, Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDeletedSources(ReadOnlyDeletedSources deletedSources, Path filePath) throws IOException {
        requireNonNull(deletedSources);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDeletedSources(deletedSources), filePath);
    }

}
