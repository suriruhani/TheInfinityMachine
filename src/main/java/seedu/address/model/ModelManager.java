package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.PanicMode;
import seedu.address.model.source.Source;
import seedu.address.model.source.exceptions.SourceNotFoundException;
import seedu.address.storage.PinnedSourcesStorageOperationsCenter;

/**
 * Represents the in-memory model of the source manager data.
 */
public class ModelManager implements Model, PanicMode {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final SimpleListProperty<Source> displayedSourceList;
    private VersionedSourceManager versionedSourceManager;
    private VersionedDeletedSources versionedDeletedSources;
    private final UserPrefs userPrefs;
    private final FilteredList<Source> filteredSources;
    private final SimpleObjectProperty<Source> selectedSource = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Source> selectedDeletedSource = new SimpleObjectProperty<>();
    private boolean panicMode = false;
    private VersionedSourceManager sourceManagerBackup = null;
    private int numPinnedSources;
    private ParserMode mode;
    private PinnedSourcesStorageOperationsCenter storageOps;

    /**
     * Initializes a ModelManager with the given sourceManager and userPrefs.
     */
    public ModelManager(ReadOnlySourceManager sourceManager, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyDeletedSources deletedSources) {
        super();
        requireAllNonNull(sourceManager, userPrefs, deletedSources);

        logger.fine("Initializing with source manager: " + sourceManager + " and user prefs " + userPrefs
                + " and deleted sources " + deletedSources);

        versionedSourceManager = new VersionedSourceManager(sourceManager);
        versionedDeletedSources = new VersionedDeletedSources((deletedSources));

        this.userPrefs = new UserPrefs(userPrefs);

        displayedSourceList = new SimpleListProperty<>(versionedSourceManager.getSourceList());
        filteredSources = new FilteredList<>(this.displayedSourceList);

        filteredSources.addListener(this::ensureSelectedSourceIsValid);

        logger.info("Loading number of pinned sources.");
        this.storageOps = new PinnedSourcesStorageOperationsCenter();
        this.numPinnedSources = storageOps.loadNumberOfPinnedSources();

        List<Source> completeSourceList = versionedSourceManager.getSourceList();
        for (int i = 0; i < this.numPinnedSources; i++) {
            Source pinnedSource = completeSourceList.get(i);
            Source enabledSource = pinnedSource;
            enabledSource.setPinnedState(true);
            versionedSourceManager.setSource(pinnedSource, enabledSource);
        }
    }

    /**
     * Alternate ModelManager constructor with option for loading pinned source number. To be used for tests only.
     * Writes the number of pinned sources stipulated into the file at the start start.
     */
    public ModelManager(ReadOnlySourceManager sourceManager, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyDeletedSources deletedSources, int numPinnedSources) {
        super();
        requireAllNonNull(sourceManager, userPrefs, deletedSources);

        logger.fine("Initializing with source manager: " + sourceManager + " and user prefs " + userPrefs
                + " and deleted sources " + deletedSources);

        versionedSourceManager = new VersionedSourceManager(sourceManager);
        versionedDeletedSources = new VersionedDeletedSources((deletedSources));

        this.userPrefs = new UserPrefs(userPrefs);

        displayedSourceList = new SimpleListProperty<>(versionedSourceManager.getSourceList());
        filteredSources = new FilteredList<>(this.displayedSourceList);

        filteredSources.addListener(this::ensureSelectedSourceIsValid);

        logger.info("Writing number of pinned sources.");
        this.storageOps = new PinnedSourcesStorageOperationsCenter("src/test/data/PinnedSourcesTest.txt");

        this.numPinnedSources = numPinnedSources;
        storageOps.writeNumberOfPinnedSourcesToFile(this.numPinnedSources);
    }

    /**
     * Alternate ModelManager constructor with option where the pinned sources is set to 0. Used in tests only.
     */
    public ModelManager() {
        this(new SourceManager(), new UserPrefs(), new DeletedSources(), 0);
    }

    /**
     * Switches list in filteredSources list to deletedSourceList.
     */
    @Override
    public void switchToDeletedSources() {
        displayedSourceList.set(versionedDeletedSources.getDeletedSourceList());
    }

    /**
     * Switches list in filteredSources list to sourceList.
     */
    @Override
    public void switchToSources() {
        displayedSourceList.set(versionedSourceManager.getSourceList());
    }

    /**
     * Activates panic mode. Backs up source manager and displays an empty one.
     */
    public void enablePanicMode() {
        logger.fine("Enabling panic mode");

        if (panicMode) {
            // Prevent enabling panic mode twice, which causes a bug where by
            // the empty mock database is backed up and overwrites the actual database
            logger.fine("Panic mode already enabled.");
            return;
        }

        panicMode = true;
        sourceManagerBackup = new VersionedSourceManager(versionedSourceManager);
        logger.fine("Backed up source manager.");
        versionedSourceManager.resetData(new SourceManager());
        logger.fine("Reset visible source manager to an empty source manager.");
    }

    /**
     * Deactivates panic mode. Restores original source manager.
     */
    public void disablePanicMode() {
        logger.fine("Disabling panic mode");

        if (!panicMode) {
            logger.fine("Panic mode already disabled.");
            return;
        }

        panicMode = false;

        if (sourceManagerBackup == null) {
            logger.fine("Nothing to restore, sourceManagerBackup is null");
            return;
        }

        versionedSourceManager.resetData(sourceManagerBackup);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getSourceManagerFilePath() {
        return userPrefs.getSourceManagerFilePath();
    }

    @Override
    public void setSourceManagerFilePath(Path sourceManagerFilePath) {
        requireNonNull(sourceManagerFilePath);
        userPrefs.setSourceManagerFilePath(sourceManagerFilePath);
    }

    @Override
    public Path getDeletedSourceFilePath() {
        return userPrefs.getDeletedSourceFilePath();
    }

    @Override
    public void setDeletedSourceFilePath(Path deletedSourcesFilePath) {
        requireNonNull(deletedSourcesFilePath);
        userPrefs.setDeletedSourceFilePath(deletedSourcesFilePath);
    }

    //=========== SourceManager ================================================================================

    @Override
    public void setSourceManager(ReadOnlySourceManager sourceManager) {
        versionedSourceManager.resetData(sourceManager);
    }

    @Override
    public ReadOnlySourceManager getSourceManager() {
        return versionedSourceManager;
    }

    @Override
    public boolean hasSource(Source source) {
        requireNonNull(source);
        return versionedSourceManager.hasSource(source);
    }

    @Override
    public void deleteSource(Source target) {
        versionedSourceManager.removeSource(target);
    }

    @Override
    public void addSource(Source source) {
        versionedSourceManager.addSource(source);
        updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
    }

    @Override
    public void addSourceAtIndex(Source source, int index) {
        versionedSourceManager.addSourceAtIndex(source, index);
        updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
    }

    @Override
    public void setSource(Source target, Source editedSource) {
        requireAllNonNull(target, editedSource);

        versionedSourceManager.setSource(target, editedSource);
    }

    //=========== DeletedSources ================================================================================

    @Override
    public void setDeletedSources(ReadOnlyDeletedSources deletedSources) {
        versionedDeletedSources.resetData(deletedSources);
    }

    @Override
    public ReadOnlyDeletedSources getDeletedSources() {
        return versionedDeletedSources;
    }

    @Override
    public boolean hasDeletedSource(Source deletedSources) {
        requireNonNull(deletedSources);
        return versionedDeletedSources.hasDeletedSource(deletedSources);
    }

    @Override
    public void removeDeletedSource(Source target) {
        versionedDeletedSources.removeDeletedSource(target);
    }

    @Override
    public void addDeletedSource(Source source) {
        versionedDeletedSources.addDeletedSource(source);
        updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
    }

    @Override
    public void addDeletedSourceAtIndex(Source source, int index) {
        versionedDeletedSources.addDeletedSourceAtIndex(source, index);
        updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
    }

    @Override
    public void setDeletedSource(Source target, Source editedSource) {
        requireAllNonNull(target, editedSource);

        versionedDeletedSources.setDeletedSource(target, editedSource);
    }

    //=========== Filtered Source List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Source} backed by the internal list of
     * {@code versionedSourceManager}
     */
    @Override
    public ObservableList<Source> getFilteredSourceList() {
        return filteredSources;
    }

    @Override
    public void updateFilteredSourceList(Predicate<Source> predicate) {
        requireNonNull(predicate);
        filteredSources.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoSourceManager() {
        return versionedSourceManager.canUndo();
    }

    @Override
    public boolean canUndoDeletedSources() {
        return versionedDeletedSources.canUndo();
    }

    @Override
    public boolean canRedoSourceManager() {
        return versionedSourceManager.canRedo();
    }

    @Override
    public boolean canRedoDeletedSources() {
        return versionedDeletedSources.canRedo();
    }

    @Override
    public void undoSourceManager() {
        versionedSourceManager.undo();
    }

    @Override
    public void undoDeletedSources() {
        versionedDeletedSources.undo();
    }

    @Override
    public void redoSourceManager() {
        versionedSourceManager.redo();
    }

    @Override
    public void redoDeletedSources() {
        versionedDeletedSources.redo();
    }

    @Override
    public void commitSourceManager() {
        versionedSourceManager.commit();
    }

    @Override
    public void commitDeletedSources() {
        versionedDeletedSources.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public ReadOnlyProperty<Source> selectedSourceProperty() {
        return selectedSource;
    }

    @Override
    public Source getSelectedSource() {
        return selectedSource.getValue();
    }

    @Override
    public void setSelectedSource(Source source) {
        if (source != null && !filteredSources.contains(source)) {
            throw new SourceNotFoundException();
        }
        selectedSource.setValue(source);
    }

    /**
     * Ensures {@code selectedSource} is a valid source in {@code filteredSources}.
     */
    private void ensureSelectedSourceIsValid(ListChangeListener.Change<? extends Source> change) {
        while (change.next()) {
            if (selectedSource.getValue() == null) {
                // null is always a valid selected source, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedSourceReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedSource.getValue());
            if (wasSelectedSourceReplaced) {
                // Update selectedSource to its new value.
                int index = change.getRemoved().indexOf(selectedSource.getValue());
                selectedSource.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedSourceRemoved = change.getRemoved().stream()
                    .anyMatch(removedSource -> selectedSource.getValue().isSameSource(removedSource));
            if (wasSelectedSourceRemoved) {
                // Select the source that came before it in the list,
                // or clear the selection if there is no such source.
                selectedSource.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedSourceManager.equals(other.versionedSourceManager)
                && versionedDeletedSources.equals(other.versionedDeletedSources)
                && userPrefs.equals(other.userPrefs)
                && filteredSources.equals(other.filteredSources)
                && Objects.equals(selectedSource.get(), other.selectedSource.get())
                && Objects.equals(selectedDeletedSource.get(), other.selectedDeletedSource.get());
    }

    @Override
    public int getNumberOfPinnedSources() {
        return this.numPinnedSources;
    }

    @Override
    public void setNumberOfPinnedSources(int newNumber) {
        this.numPinnedSources = newNumber;
    }

    @Override
    public void setParserMode(ParserMode mode) {
        switch (mode) {
        case SOURCE_MANAGER:
            switchToSources();
            break;
        case RECYCLE_BIN:
            switchToDeletedSources();
            break;
        default:
            switchToSources();
            break;
        }
        this.mode = mode;
    }

    @Override
    public ParserMode getParserMode() {
        return this.mode;
    }

    @Override
    public PinnedSourcesStorageOperationsCenter getStorageOperationsCenter () {
        return this.storageOps;
    }
}
