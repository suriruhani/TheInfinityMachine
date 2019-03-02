package seedu.address.sourcemodel;

import java.util.ArrayList;

public class SourceDatabase {

    private ArrayList<Source> database = new ArrayList<Source>();

    // Constructor for the main database object, will automatically load any existing entries in the database file
    // The database will be empty otherwise
    public SourceDatabase() {
        this.database = SourceStorageOperations.loadExistingDatabase();
    }

    // Functions to manipulate source objects in the database
    public void addNewSource (Source sourceToAdd) {
        this.database.add(sourceToAdd);
    }
    public void deleteSourceAtIndex (int index) {
        this.database.remove(index);
    }
    public void deleteAllSources () {
        this.database.clear();
    }
    public void editSourceTitleAtIndex (int index, String newTitle) {
        this.database.get(index).setSourceTitle(newTitle);
    }
    public void editSourceTypeAtIndex (int index, String newType) {
        this.database.get(index).setSourceType(newType);
    }
    public void editSourceDetailsAtIndex (int index, String newDetails) {
        this.database.get(index).setSourceDetails(newDetails);
    }
    public void editSourceTagsAtIndex (int index, ArrayList<String> newSourceTags) {
        this.database.get(index).setSourceTags(newSourceTags);
    }

    // Function to save the database to the txt file, only use when about to close
    public void saveDatabase () {
        SourceStorageOperations.writeToDatabase(this.database);
    }
}
