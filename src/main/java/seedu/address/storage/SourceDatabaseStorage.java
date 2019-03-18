//Modified
package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for the sources.
 */
public interface SourceDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSourceDatabaseFilePath();

    /**
     * Returns Source Database data as a ReadOnlySourceDatabase.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySourceDatabase> readSourceDatabase() throws DataConversionException, IOException;

    /**
     * @see #getSourceDatabaseFilePath()
     */
    Optional<ReadOnlySourceDatabase> readSourceDatabase(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given ReadOnlySourceDatabase to the storage.
     * @param sourceDatabase cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSourceDatabase(ReadOnlySourceDatabase sourceDatabase) throws IOException;

    /**
     * @see #saveSourceDatabase(ReadOnlySourceDatabase)
     */
    void saveSourceDatabase(ReadOnlySourceDatabase sourceDatabase, Path filePath) throws IOException;

}
