package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path sourceManagerFilePath = Paths.get("data" , "sourcemanager.json");
    private Path deletedSourceFilePath = Paths.get("data" , "deletedsource.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setSourceManagerFilePath(newUserPrefs.getSourceManagerFilePath());
        setDeletedSourceFilePath(newUserPrefs.getDeletedSourceFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getSourceManagerFilePath() {
        return sourceManagerFilePath;
    }

    public void setSourceManagerFilePath(Path sourceManagerFilePath) {
        requireNonNull(sourceManagerFilePath);
        this.sourceManagerFilePath = sourceManagerFilePath;
    }

    public Path getDeletedSourceFilePath() {
        return deletedSourceFilePath;
    }

    public void setDeletedSourceFilePath(Path deletedSourceFilePath) {
        requireNonNull(deletedSourceFilePath);
        this.deletedSourceFilePath = deletedSourceFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && sourceManagerFilePath.equals(o.sourceManagerFilePath)
                && deletedSourceFilePath.equals(o.deletedSourceFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, sourceManagerFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nSource Manager Local data file location : " + sourceManagerFilePath);
        sb.append("\nDeleted Source Local data file location : " + deletedSourceFilePath);
        return sb.toString();
    }

}
