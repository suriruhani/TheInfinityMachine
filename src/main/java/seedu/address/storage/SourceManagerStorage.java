//Modified
package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySourceManager;

/**
 * Represents a storage for the sources.
 */
public interface SourceManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSourceManagerFilePath();

    /**
     * Returns Source Manager data as a ReadOnlySourceManager.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySourceManager> readSourceManager() throws DataConversionException, IOException;

    /**
     * @see #getSourceManagerFilePath()
     */
    Optional<ReadOnlySourceManager> readSourceManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given ReadOnlySourceManager to the storage.
     * @param sourceManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSourceManager(ReadOnlySourceManager sourceManager) throws IOException;

    /**
     * @see #saveSourceManager(ReadOnlySourceManager)
     */
    void saveSourceManager(ReadOnlySourceManager sourceManager, Path filePath) throws IOException;

}
