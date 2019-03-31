package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents Recently deleted.
 */
public class DeletedSources implements ReadOnlyDeletedSources {

    private GuiSettings guiSettings = new GuiSettings();
    private Path deletedSourceFilePath = Paths.get("data" , "deletedsources.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public DeletedSources() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public DeletedSources(ReadOnlyDeletedSources recentlyDeleted) {
        this();
        resetData(recentlyDeleted);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyDeletedSources newRecentlyDeleted) {
        requireNonNull(newRecentlyDeleted);
        setGuiSettings(newRecentlyDeleted.getGuiSettings());
        setDeletedSourceFilePath(newRecentlyDeleted.getDeletedSourceFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
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
        if (!(other instanceof DeletedSources)) { //this handles null as well.
            return false;
        }

        DeletedSources o = (DeletedSources) other;

        return guiSettings.equals(o.guiSettings)
                && deletedSourceFilePath.equals(o.deletedSourceFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, deletedSourceFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + deletedSourceFilePath);
        return sb.toString();
    }

}
