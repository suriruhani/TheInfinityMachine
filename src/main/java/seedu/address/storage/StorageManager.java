package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of SourceManager data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SourceManagerStorage sourceManagerStorage;
    private DeletedSourcesStorage deletedSourcesStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SourceManagerStorage sourceManagerStorage, UserPrefsStorage userPrefsStorage,
                          DeletedSourcesStorage deletedSourcesStorage) {
        super();
        this.sourceManagerStorage = sourceManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.deletedSourcesStorage = deletedSourcesStorage;
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


    // ================ SourceManager methods ==============================

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

    // ================ DeletedSources methods ==============================

    @Override
    public Path getDeletedSourceFilePath() {
        return deletedSourcesStorage.getDeletedSourceFilePath();
    }

    @Override
    public Optional<ReadOnlyDeletedSources> readDeletedSources() throws DataConversionException, IOException {
        return readDeletedSources(deletedSourcesStorage.getDeletedSourceFilePath());
    }

    @Override
    public Optional<ReadOnlyDeletedSources> readDeletedSources(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return deletedSourcesStorage.readDeletedSources(filePath);
    }

    @Override
    public void saveDeletedSources(ReadOnlyDeletedSources deletedSources) throws IOException {
        saveDeletedSources(deletedSources, deletedSourcesStorage.getDeletedSourceFilePath());
    }

    @Override
    public void saveDeletedSources(ReadOnlyDeletedSources deletedSources, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        deletedSourcesStorage.saveDeletedSources(deletedSources, filePath);
    }
}
