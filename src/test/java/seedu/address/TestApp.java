package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.SourceManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonDeletedSourcesStorage;
import seedu.address.storage.JsonSourceManagerStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleData.json");
    public static final Path SAVE_LOCATION_FOR_TESTING_DELETED_SOURCES =
            TestUtil.getFilePathInSandboxFolder("sampleDeletedData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlySourceManager> initialDataSupplier = () -> null;
    protected Supplier<ReadOnlyDeletedSources> deletedDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected Path saveFileLocationDeletedSources = SAVE_LOCATION_FOR_TESTING_DELETED_SOURCES;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlySourceManager> initialDataSupplier,
                   Supplier<ReadOnlyDeletedSources> deletedDataSupplier,
                   Path saveFileLocation,
                   Path saveFileLocationDeletedSources) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.deletedDataSupplier = deletedDataSupplier;
        this.saveFileLocation = saveFileLocation;
        this.saveFileLocationDeletedSources = saveFileLocationDeletedSources;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null && deletedDataSupplier.get() != null) {
            JsonSourceManagerStorage jsonSourceManagerStorage =
                    new JsonSourceManagerStorage(saveFileLocation);
            JsonDeletedSourcesStorage jsonDeletedSourcesStorage =
                    new JsonDeletedSourcesStorage(saveFileLocationDeletedSources);
            try {
                jsonSourceManagerStorage.saveSourceManager(initialDataSupplier.get());
                jsonDeletedSourcesStorage.saveDeletedSources(deletedDataSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.setGuiSettings(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setSourceManagerFilePath(saveFileLocation);
        userPrefs.setDeletedSourceFilePath(saveFileLocationDeletedSources);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the source manager data stored inside the storage file.
     */
    public SourceManager readStorageSourceManager() {
        try {
            return new SourceManager(storage.readSourceManager().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the SourceManager format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }


    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getSourceManagerFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getSourceManager()), new UserPrefs(), model.getDeletedSources());
        ModelHelper.setFilteredList(copy, model.getFilteredSourceList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
