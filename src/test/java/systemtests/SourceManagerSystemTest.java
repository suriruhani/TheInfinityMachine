package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.MainWindowHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.SourceListPanelHandle;
import guitests.guihandles.SourcePanelHandle;
import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.TestApp;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.DeletedSources;
import seedu.address.model.Model;
import seedu.address.model.SourceManager;
import seedu.address.testutil.TypicalSources;
import seedu.address.ui.CommandBox;
import seedu.address.ui.SourcePanel;

/**
 * A system test class for SourceManager, which provides access to handles of GUI components and helper methods
 * for test verification.
 */
public abstract class SourceManagerSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initialize();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication(
                this::getInitialData,
                this::getDeletedData,
                getDataFileLocation(),
                getDeletedDataFileLocation());
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getSourcePanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() {
        setupHelper.tearDownStage();
    }

    /**
     * Returns the data to be loaded into the file in {@link #getDataFileLocation()}.
     */
    protected SourceManager getInitialData() {
        return TypicalSources.getTypicalSourceManager();
    }

    protected DeletedSources getDeletedData() {
        return TypicalSources.getTypicalDeletedSources();
    }

    /**
     * Returns the directory of the data file.
     */
    protected Path getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    protected Path getDeletedDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING_DELETED_SOURCES;
    }

    public MainWindowHandle getMainWindowHandle() {
        return mainWindowHandle;
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public SourceListPanelHandle getSourceListPanel() {
        return mainWindowHandle.getSourceListPanel();
    }

    public MainMenuHandle getMainMenu() {
        return mainWindowHandle.getMainMenu();
    }

    public SourcePanelHandle getSourcePanel() {
        return mainWindowHandle.getSourcePanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getSourcePanel());
    }

    /**
     * Displays all sources in the source manager.
     */
    protected void showAllSources() {
        executeCommand(ListCommand.COMMAND_WORD);
        assertEquals(getModel().getSourceManager().getSourceList().size(), getModel().getFilteredSourceList().size());
    }

    /**
     * Displays all sources with any parts of their titles matching {@code keyword} (case-insensitive).
     */
    protected void showSourcesWithTitle(String keyword) {
        executeCommand(SearchCommand.COMMAND_WORD + " " + keyword);
        assertTrue(getModel().getFilteredSourceList().size() < getModel().getSourceManager().getSourceList().size());
    }

    /**
     * Selects the source at {@code index} of the displayed list.
     */
    protected void selectSource(Index index) {
        executeCommand(SelectCommand.COMMAND_WORD + " " + index.getOneBased());
        assertEquals(index.getZeroBased(), getSourceListPanel().getSelectedCardIndex());
    }

    /**
     * Deletes all sources in the source manager.
     */
    protected void deleteAllSources() {
        executeCommand(ClearCommand.COMMAND_WORD);
        assertEquals(0, getModel().getSourceManager().getSourceList().size());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the storage contains the same source objects as {@code expectedModel}
     * and the source list panel displays the sources in the model correctly.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
            Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(new SourceManager(expectedModel.getSourceManager()), testApp.readStorageSourceManager());
        assertListMatching(getSourceListPanel(), expectedModel.getFilteredSourceList());
    }

    /**
     * Calls {@code SourcePanelHandle}, {@code SourceListPanelHandle} and {@code StatusBarFooterHandle} to remember
     * their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getSourcePanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getSourceListPanel().rememberSelectedSourceCard();
    }

    /**
     * Asserts that the previously selected card is now deselected and the browser's url is now displaying the
     * default page.
     * @see SourcePanelHandle#isUrlChanged()
     */
    protected void assertSelectedCardDeselected() {
        assertEquals(SourcePanel.DEFAULT_PAGE, getSourcePanel().getLoadedUrl());
        assertFalse(getSourceListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that the source's url is changed to display the details of the source in the source list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see SourcePanelHandle#isUrlChanged()
     * @see SourceListPanelHandle#isSelectedSourceCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        getSourceListPanel().navigateToCard(getSourceListPanel().getSelectedCardIndex());
        assertTrue(getSourcePanel().isLoaded());
        assertEquals(expectedSelectedCardIndex.getZeroBased(), getSourceListPanel().getSelectedCardIndex());
    }

    /**
     * Asserts that the browser's url and the selected card in the source list panel remain unchanged.
     * @see SourcePanelHandle#isUrlChanged()
     * @see SourceListPanelHandle#isSelectedSourceCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getSourcePanel().isUrlChanged());
        assertFalse(getSourceListPanel().isSelectedSourceCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that only the sync status in the status bar was changed to the timing of
     * {@code ClockRule#getInjectedClock()}, while the save location remains the same.
     */
    protected void assertStatusBarUnchangedExceptSyncStatus() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        String timestamp = new Date(clockRule.getInjectedClock().millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, handle.getSyncStatus());
        assertFalse(handle.isSaveLocationChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        assertEquals("", getCommandBox().getInput());
        assertEquals("", getResultDisplay().getText());
        assertListMatching(getSourceListPanel(), getModel().getFilteredSourceList());
        assertEquals(SourcePanel.DEFAULT_PAGE, getSourcePanel().getLoadedUrl());
        assertEquals(Paths.get(".").resolve(testApp.getStorageSaveLocation()).toString(),
                getStatusBarFooter().getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
