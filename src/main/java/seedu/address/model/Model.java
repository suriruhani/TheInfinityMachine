package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.PanicMode;
import seedu.address.model.source.Source;
import seedu.address.storage.PinnedSourcesStorageOperationsCenter;

/**
 * The API of the Model component.
 */
public interface Model extends PanicMode {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Source> PREDICATE_SHOW_ALL_SOURCES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' source manager file path.
     */
    Path getSourceManagerFilePath();

    /**
     * Returns the user prefs' source manager file path.
     */
    Path getDeletedSourceFilePath();

    /**
     * Sets the user prefs' source manager file path.
     */
    void setSourceManagerFilePath(Path sourceManagerFilePath);

    /**
     * Sets the user prefs' source manager file path.
     */
    void setDeletedSourceFilePath(Path deletedSourceFilePath);

    /**
     * Replaces source manager data with the data in {@code sourceManager}.
     */
    void setSourceManager(ReadOnlySourceManager sourceManager);

    /** Returns the SourceManager */
    ReadOnlySourceManager getSourceManager();

    /**
     * Replaces source manager data with the data in {@code sourceManager}.
     */
    void setDeletedSources(ReadOnlyDeletedSources deletedSources);

    /** Returns the SourceManager */
    ReadOnlyDeletedSources getDeletedSources();

    /**
     * Returns true if a source with the same identity as {@code source} exists in the source manager.
     */
    boolean hasSource(Source source);

    /**
     * Returns true if a source with the same identity as {@code source} exists in the source manager.
     */
    boolean hasDeletedSource(Source source);

    /**
     * Deletes the given source.
     * The source must exist in the source manager.
     */
    void deleteSource(Source target);

    /**
     * Deletes the given source.
     * The source must exist in the source manager.
     */
    void removeDeletedSource(Source target);

    /**
     * Adds the given source.
     * {@code source} must not already exist in the source manager.
     */
    void addSource(Source source);

    /**
     * Adds the given source.
     * {@code source} must not already exist in the source manager.
     */
    void addDeletedSource(Source source);

    /**
     * Adds the given source to an index.
     * {@code source} must not already exist in the source manager.
     */
    void addSourceAtIndex(Source source, int index);

    /**
     * Adds the given source to an index.
     * {@code source} must not already exist in the source manager.
     */
    void addDeletedSourceAtIndex(Source source, int index);

    /**
     * Replaces the given source {@code target} with {@code editedSource}.
     * {@code target} must exist in the source manager.
     * The source identity of {@code editedSource} must not be the same
     * as another existing source in the source manager.
     */
    void setSource(Source target, Source editedSource);

    /**
     * Replaces the given source {@code target} with {@code editedSource}.
     * {@code target} must exist in the source manager.
     * The source identity of {@code editedSource} must not be the same
     * as another existing source in the source manager.
     */
    void setDeletedSource(Source target, Source editedSource);

    /** Returns an unmodifiable view of the filtered source list */
    ObservableList<Source> getFilteredSourceList();

    /**
     * Updates the filter of the filtered source list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSourceList(Predicate<Source> predicate);

    /**
     * Returns true if the model has previous source manager states to restore.
     */
    boolean canUndoSourceManager();

    /**
     * Returns true if the model has previous source manager states to restore.
     */
    boolean canUndoDeletedSources();

    /**
     * Returns true if the model has undone source manager states to restore.
     */
    boolean canRedoSourceManager();

    /**
     * Returns true if the model has undone source manager states to restore.
     */
    boolean canRedoDeletedSources();

    /**
     * Restores the model's source manager to its previous state.
     */
    void undoSourceManager();

    /**
     * Restores the model's source manager to its previous state.
     */
    void undoDeletedSources();

    /**
     * Restores the model's source manager to its previously undone state.
     */
    void redoSourceManager();

    /**
     * Restores the model's source manager to its previously undone state.
     */
    void redoDeletedSources();

    /**
     * Saves the current source manager state for undo/redo.
     */
    void commitSourceManager();

    /**
     * Saves the current source manager state for undo/redo.
     */
    void commitDeletedSources();

    /**
     * Selected source in the filtered source list.
     * null if no source is selected.
     */
    ReadOnlyProperty<Source> selectedSourceProperty();

    /**
     * Returns the selected source in the filtered source list.
     * null if no source is selected.
     */
    Source getSelectedSource();

    /**
     * Sets the selected source in the filtered source list.
     */
    void setSelectedSource(Source source);

    /**
     * Switch the list in the filtered source list to deleted sources.
     */
    void switchToDeletedSources();

    /**
     * Switch the list in the filtered source list to sources.
     */
    void switchToSources();

    /**
     * Default implementation to prevent compilation errors when implementors of Model
     * do not implement PanicMode.
     */
    default void enablePanicMode() {}

    /**
     * Default implementation to prevent compilation errors when implementors of Model
     * do not implement PanicMode.
     */
    default void disablePanicMode() {}

    /**
     * Gets the number of pinned sources.
     */
    int getNumberOfPinnedSources();

    /**
     * Sets the number of pinned sources.
     */
    void setNumberOfPinnedSources(int newNumber);

    /**
     * Sets the current parser to SourceManagerParser or RecycleBinParser.
     */
    void setParserMode(ParserMode mode);

    /**
     * Gets the parser currently being used.
     */
    ParserMode getParserMode();

    /**
     * Gets the storage operations center for pinned sources.
     */
    PinnedSourcesStorageOperationsCenter getStorageOperationsCenter();
}
