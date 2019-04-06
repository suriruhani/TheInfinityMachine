package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Center for all pinned source storage related operations
 */
public class PinnedSourcesStorageOperationsCenter {
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static final String DATABASE_PATH = "data/PinnedSources.txt";

    // Establishes a path object using the name of the database file defined above
    private static final Path FILE_PATH = Paths.get(DATABASE_PATH);

    /**
     * Returns the number of pinned sources.
     *
     * @return Integer number of pinned sources.
     */
    public static int loadNumberOfPinnedSources () {
        // Create a new integer which starts as 0
        int numPinnedSources = 0;

        // Create a new empty List to store the single encoded integer
        List<String> encodedPinnedSourceInteger = new ArrayList<String>();

        logger.info("Attempting to read data from file: " + FILE_PATH);
        try {
            // Attempt to load the data from the txt file into the empty list created earlier
            encodedPinnedSourceInteger = Files.readAllLines(FILE_PATH);
        } catch (IOException e) {
            logger.severe("Unable to read file. Assume no pinned sources.");
            return numPinnedSources;
        }

        // If the returned list is empty, then there is nothing to load, return 0
        if (encodedPinnedSourceInteger.isEmpty() == true) {
            logger.severe("File is empty. Assume no pinned sources.");
            return numPinnedSources;
        }

        // If the returned list has more than 1 element, then the file has been corrupted, return 0
        if (encodedPinnedSourceInteger.size() != 1) {
            logger.severe("File format is invalid. Assume no pinned sources.");
            return numPinnedSources;
        }

        try {
            numPinnedSources = Integer.parseInt(encodedPinnedSourceInteger.get(0));
            logger.info("File character accessed. Number of pinned sources: " + numPinnedSources);
        } catch (NumberFormatException nfe) {
            // the item could not parsed, it is an invalid character, assume corruption, return 0
            logger.severe("File character is invalid. Assume no pinned sources.");
            return numPinnedSources;
        }

        return numPinnedSources;
    }

    /**
     * Saves the number of pinned sources to the external text file.
     *
     * @param numPinnedSourcesToSave The number of pinned sources which is to be saved into the external txt file.
     */
    public static void writeNumberOfPinnedSourcesToFile (int numPinnedSourcesToSave) {
        // Create a new empty List to contain the single integer
        List<String> encodedPinnedSourceInteger = new ArrayList<String>();

        String encodedPinnedSourceString = Integer.toString(numPinnedSourcesToSave);

        encodedPinnedSourceInteger.add(encodedPinnedSourceString);

        logger.info("Attempting to write the converted integer to file: " + FILE_PATH);
        try {
            Files.write(FILE_PATH, encodedPinnedSourceInteger);
        } catch (IOException e) {
            logger.severe("Unable to write to file.");
        }
    }
}
