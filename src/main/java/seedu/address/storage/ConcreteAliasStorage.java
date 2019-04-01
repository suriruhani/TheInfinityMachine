package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;

/**
 * A concrete implementation of AliasStorage to access aliases stored persistently.
 */
public class ConcreteAliasStorage implements AliasStorage {
    private static final String ALIAS_COMMAND_SEPERATOR = ":"; // i.e. alias:command
    private static final String PAIR_SEPARATOR = ";"; // i.e. alias1:command1;alias2:command2
    private static final Path ALIASES_STORAGE_PATH = Paths.get("data" , "aliases.data");

    private Path filePath;

    public ConcreteAliasStorage() {
        this.filePath = ALIASES_STORAGE_PATH;
    }

    public ConcreteAliasStorage(Path filePath) {
        this();
        this.filePath = filePath;
    }

    @Override
    public void clearAliasesInStorage() throws IOException {
        FileUtil.writeToFile(filePath, "");
    }

    @Override
    public Path getAliasesFilePath() {
        return filePath;
    }

    @Override
    public HashMap<String, String> readAliases() throws DataConversionException, IOException {
        return readAliases(filePath);
    }

    /**
     * Similar to {@link #readAliases()}
     * @param aliasesFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public HashMap<String, String> readAliases(Path aliasesFilePath) throws DataConversionException, IOException {
        Objects.requireNonNull(aliasesFilePath);

        // Guard against non-existent file
        if (!FileUtil.isFileExists(aliasesFilePath)) {
            throw new IOException("File does not exist");
        }

        // Should never throw, since we guarded against a non-existent file
        String fileContents = FileUtil.readFromFile(aliasesFilePath);

        HashMap<String, String> aliases = new HashMap<>();
        String[] aliasCommandPairs = fileContents.split(PAIR_SEPARATOR);
        for (int i = 0; i < aliasCommandPairs.length; i++) {
            String[] aliasCommandPair = aliasCommandPairs[i].split(ALIAS_COMMAND_SEPERATOR);
            // Guard against invalid format
            if (aliasCommandPair.length != 2) {
                throw new DataConversionException(new Exception("Invalid format"));
            }
            aliases.put(aliasCommandPair[0], aliasCommandPair[1]);
        }

        return aliases;
    }

    @Override
    public void saveAliases(HashMap<String, String> aliases) throws IOException {
        Objects.requireNonNull(aliases);

        StringBuilder sb = new StringBuilder();
        aliases.forEach((alias, command) -> {
            sb.append(alias);
            sb.append(ALIAS_COMMAND_SEPERATOR);
            sb.append(command);
            sb.append(PAIR_SEPARATOR);
        });

        FileUtil.writeToFile(filePath, sb.toString());
    }

}
