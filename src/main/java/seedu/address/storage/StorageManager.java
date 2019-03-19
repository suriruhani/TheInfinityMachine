package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SourceManagerStorage sourceManagerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SourceManagerStorage sourceManagerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.sourceManagerStorage = sourceManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getSourceManagerFilePath() {
        return sourceManagerStorage.getSourceManagerFilePath();
    }

    @Override
    public Optional<ReadOnlySourceManager> readSourceManager() throws DataConversionException, IOException {
        return readSourceManager(sourceManagerStorage.getSourceManagerFilePath());
    }

    @Override
    public Optional<ReadOnlySourceManager> readSourceManager(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return sourceManagerStorage.readSourceManager(filePath);
    }

    @Override
    public void saveSourceManager(ReadOnlySourceManager sourceManager) throws IOException {
        saveSourceManager(sourceManager, sourceManagerStorage.getSourceManagerFilePath());
    }

    @Override
    public void saveSourceManager(ReadOnlySourceManager sourceManager, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        sourceManagerStorage.saveSourceManager(sourceManager, filePath);
    }

}
