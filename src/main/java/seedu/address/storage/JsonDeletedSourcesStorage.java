package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DeletedSources;
import seedu.address.model.ReadOnlyDeletedSources;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class JsonDeletedSourcesStorage implements DeletedSourcesStorage {


    private Path filePath;

    public JsonDeletedSourcesStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getDeletedSourceFilePath() {
        return filePath;
    }

    @Override
    public Optional<DeletedSources> readDeletedSources() throws DataConversionException {
        return readDeletedSources(filePath);
    }

    /**
     * Similar to {@link #readDeletedSources()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<DeletedSources> readDeletedSources(Path prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, DeletedSources.class);
    }

    @Override
    public void saveDeletedSources(ReadOnlyDeletedSources deletedSources) throws IOException {
        JsonUtil.saveJsonFile(deletedSources, filePath);
    }

}
