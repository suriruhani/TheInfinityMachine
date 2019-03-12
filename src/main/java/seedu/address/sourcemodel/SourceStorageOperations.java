package seedu.address.sourcemodel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.StorageManager;

/**
 * Class to handle storage options.
 */
public class SourceStorageOperations {

    private static final String DATABASE_PATH = "database.txt";
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    // Adapted from addressbook, '/' used a delimiter since nothing else should use it, can accept any number of tags
    private static final Pattern DATABASE_STORAGE_FORMAT = Pattern.compile(
        "(?<sourceTitle>[^/]+)"
        + " y/(?<sourceType>[^/]+)"
        + " d/(?<sourceDetails>[^/]+)"
        + "(?<sourceTags>(?: t/[^/]+)*)");

    // Establishes a path object using the name of the database file defined above
    private static final Path FILE_PATH = Paths.get(DATABASE_PATH);

    /**
     * Returns an array list of decoded sources.
     * If there are no sources in the database, then an empty list is returned.
     *
     * @return ArrayList of decoded sources.
     */
    public static ArrayList<Source> loadExistingDatabase () {

        // Create a new empty ArrayList of decoded elements
        ArrayList<Source> decodedSources = new ArrayList<Source>();

        // Create a new empty List of encoded elements
        List<String> encodedSources = new ArrayList<String>();

        try {
            // Attempt to load the data from the txt file into the empty list created earlier
            encodedSources = Files.readAllLines(FILE_PATH);
        } catch (IOException e) {
            logger.severe("Error loading file.");
        }

        // If the returned list is empty, then there is nothing to load, return an empty decoded list
        if (encodedSources.isEmpty() == true) {
            return decodedSources;
        }

        // Cycle through the list of encoded sources to get all the sources stored within
        for (int i = 0; i < encodedSources.size(); i++) {
            // Adapted from addressbook, see if the format of the database is in the form of the designated pattern
            final Matcher matcher = DATABASE_STORAGE_FORMAT.matcher(encodedSources.get(i));

            if (!matcher.matches()) {
                logger.severe("Error decoding source from file. Skipping entry...");
                continue;
            }

            // Loads and stores basic information into Strings for use later
            String loadedTitle = matcher.group("sourceTitle");
            String loadedType = matcher.group("sourceType");
            String loadedDetails = matcher.group("sourceDetails");

            // sourceTags contains a line of tags separated by "/t", need to split them up and store into an ArrayList
            String loadedTags = matcher.group("sourceTags");

            // Adapted from addressbook, replaces first delimiter prefix, then splits into an array of strings
            final String[] loadedTagArray = loadedTags.replaceFirst(" t/", "").split(" t/");

            // Cycles through the generated tag array and creates an ArrayList of tags
            ArrayList<String> sourceTagArray = new ArrayList<String>();
            for (int j = 0; j < loadedTagArray.length; j++) {
                sourceTagArray.add(loadedTagArray[j]);
            }

            // New Source object generated from the database file
            Source decodedSource = new Source(loadedTitle, loadedType, loadedDetails, sourceTagArray);

            // Add the new source to the ArrayList of sources
            decodedSources.add(decodedSource);
        }

        return decodedSources;
    }

    /**
     * Saves the input database into an external file in the appropriate format.
     *
     * @param databaseToSave The database which is to be saved into the external txt file.
     */
    public static void writeToDatabase (ArrayList<Source> databaseToSave) {
        // Create a new empty List of encoded elements
        List<String> encodedSources = new ArrayList<String>();

        // Cycles through all items in the ArrayList of sources
        for (int i = 0; i < databaseToSave.size(); i++) {
            // Adapted from addressbook, creates a string for each entry to the new encoded elements
            StringBuilder encodedSourceBuilder = new StringBuilder();

            encodedSourceBuilder.append(databaseToSave.get(i).getSourceTitle());

            encodedSourceBuilder.append(" y/");
            encodedSourceBuilder.append(databaseToSave.get(i).getSourceType());

            encodedSourceBuilder.append(" d/");
            encodedSourceBuilder.append(databaseToSave.get(i).getSourceDetails());

            // Tags are stored as an ArrayList of strings and so must be accessed as such
            for (int j = 0; j < databaseToSave.get(i).getSourceTags().size(); j++) {
                encodedSourceBuilder.append(" t/");
                encodedSourceBuilder.append(databaseToSave.get(i).getSourceTags().get(j));
            }

            // Add the completed string to the list of strings to be saved
            encodedSources.add(encodedSourceBuilder.toString());
        }

        // Attempt to write the encoded list to the file
        try {
            Files.write(FILE_PATH, encodedSources);
        } catch (IOException e) {
            logger.severe("Error writing to file.");
        }
    }
}
