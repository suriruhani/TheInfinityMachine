package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DeletedSources;
import seedu.address.model.ReadOnlyDeletedSources;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.DeletedSources}.
 */
public interface DeletedSourcesStorage {

    /**
     * Returns the file path of the DeletedSources data file.
     */
    Path getDeletedSourceFilePath();

    /**
     * Returns DeletedSources data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<DeletedSources> readDeletedSources() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.ReadOnlyDeletedSources} to the storage.
     * @param deletedSources cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDeletedSources(ReadOnlyDeletedSources deletedSources) throws IOException;

}