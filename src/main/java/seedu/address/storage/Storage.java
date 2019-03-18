//Modified
package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of the Source Storage component
 */
public interface Storage extends SourceManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getSourceManagerFilePath();

    @Override
    Optional<ReadOnlySourceManager> readSourceManager() throws DataConversionException, IOException;

    @Override
    void saveSourceManager(ReadOnlySourceManager sourceManager) throws IOException;

}
