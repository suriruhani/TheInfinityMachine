//Modified
package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of the Source Storage component
 */
public interface Storage extends SourceDatabaseStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getSourceDatabaseFilePath();

    @Override
    Optional<ReadOnlySourceDatabase> readSourceDatabase() throws DataConversionException, IOException;

    @Override
    void saveSourceDatabase(ReadOnlySourceDatabase sourceDatabase) throws IOException;

}
