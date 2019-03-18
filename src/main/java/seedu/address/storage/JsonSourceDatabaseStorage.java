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
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A class to access Source Database data stored as a json file on the hard disk.
 */
public class JsonSourceDatabaseStorage implements SourceDatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonSourceDatabaseStorage.class);

    private Path filePath;

    public JsonSourceDatabaseStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSourceDatabaseFilePath() {
        return filePath;
    }

    @Override
    Optional<ReadOnlySourceDatabase> readSourceDatabase() throws DataConversionException {
        return readSourceDatabase(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySourceDatabase> readSourceDatabase(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSourceDatabase> jsonSourceDB = JsonUtil.readJsonFile(
                filePath, JsonSerializableSourceDatabase.class);
        if (!jsonSourceDB.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSourceDB.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSourceDatabase(ReadOnlySourceDatabase sourceDatabase) throws IOException {
        saveSourceDatabase(sourceDatabase, filePath);
    }

    /**
     * Similar to {@link #saveSourceDatabase(ReadOnlySourceDatabase, Path)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSourceDatabase(ReadOnlySourceDatabase sourceDatabase, Path filePath) throws IOException {
        requireNonNull(sourceDatabase);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSourceDatabase(sourceDatabase), filePath);
    }

}
