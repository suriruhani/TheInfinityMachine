package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import seedu.address.commons.exceptions.DataConversionException;

/**
 * Represents a storage for user-defined command aliases.
 */
public interface AliasStorage {

    /**
     * Clears aliases in storage.
     */
    public void clearAliasesInStorage() throws IOException;

    /**
     * Returns the file path of the Aliases data file.
     */
    Path getAliasesFilePath();

    /**
     * Returns Aliases data from storage.
     * @returns a HashMap mapping aliases to commands (possibly empty).
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    HashMap<String, String> readAliases() throws DataConversionException, IOException;

    /**
     * Saves the provided Aliases to the storage.
     * @param aliases a HashMap mapping aliases to commands; cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAliases(HashMap<String, String> aliases) throws IOException;

}
