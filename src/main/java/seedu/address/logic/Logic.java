package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.source.Source;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the SourceManager.
     *
     * @see seedu.address.model.Model#getSourceManager()
     */
    ReadOnlySourceManager getSourceManager();

    /**
     * Returns the DeletedSources.
     *
     * @see seedu.address.model.Model#getDeletedSources()
     */
    ReadOnlyDeletedSources getDeletedSources();

    /** Returns an unmodifiable view of the filtered list of sources */
    ObservableList<Source> getFilteredSourceList();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns the user prefs' source manager file path.
     */
    Path getSourceManagerFilePath();

    /**
     * Returns the user prefs' deleted sources file path.
     */
    Path getDeletedSourceFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Selected source in the filtered source list.
     * null if no source is selected.
     *
     * @see seedu.address.model.Model#selectedSourceProperty()
     */
    ReadOnlyProperty<Source> selectedSourceProperty();

    /**
     * Sets the selected source in the filtered source list.
     *
     * @see seedu.address.model.Model#setSelectedSource(Source)
     */
    void setSelectedSource(Source source);

}
