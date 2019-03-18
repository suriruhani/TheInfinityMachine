package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SourceDatabaseStorage sourceDatabaseStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(SourceDatabaseStorage sourceDatabaseStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.sourceDatabaseStorage = sourceDatabaseStorage;
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
    public Path getSourceDatabaseFilePath() {
        return sourceDatabaseStorage.getSourceDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlySourceDatabase> readSourceDatabase() throws DataConversionException, IOException {
        return readSourceDatabase(sourceDatabaseStorage.getSourceDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlySourceDatabase> readSourceDatabase(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return sourceDatabaseStorage.readSourceDatabase(filePath);
    }

    @Override
    public void saveSourceDatabase(ReadOnlySourceDatabase sourceDatabase) throws IOException {
        saveSourceDatabase(sourceDatabase, sourceDatabaseStorage.getSourceDatabaseFilePath());
    }

    @Override
    public void saveSourceDatabase(ReadOnlySourceDatabase sourceDatabase, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        sourceDatabaseStorage.saveSourceDatabase(sourceDatabase, filePath);
    }

}
