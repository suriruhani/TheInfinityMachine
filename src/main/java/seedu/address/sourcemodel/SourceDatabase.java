package seedu.address.sourcemodel;

import java.util.ArrayList;

/**
 * Represents the model of a source database.
 */
public class SourceDatabase {

    private ArrayList<Source> database = new ArrayList<Source>();

    /**
     * Constructor for the main database object, will automatically load any existing entries in the database file
     * The database will be empty if there is nothing in the existing file to load
     */
    public SourceDatabase() {
        this.database = SourceStorageOperations.loadExistingDatabase();
    }

    /**
     * Series of functions to manipulate the sources in the database
     */
    public void addNewSource (Source sourceToAdd) {
        database.add(sourceToAdd);
    }
    public void deleteSourceAtIndex (int index) {
        database.remove(index);
    }
    public void deleteAllSources () {
        database.clear();
    }
    public void editSourceTitleAtIndex (int index, String newTitle) {
        database.get(index).setSourceTitle(newTitle);
    }
    public void editSourceTypeAtIndex (int index, String newType) {
        database.get(index).setSourceType(newType);
    }
    public void editSourceDetailsAtIndex (int index, String newDetails) {
        database.get(index).setSourceDetails(newDetails);
    }
    public void editSourceTagsAtIndex (int index, ArrayList<String> newSourceTags) {
        database.get(index).setSourceTags(newSourceTags);
    }

    /**
     * Function to save the file into the external file, only to be used when finishing or closing the application
     */
    public void saveDatabase () {
        SourceStorageOperations.writeToDatabase(this.database);
    }
}
