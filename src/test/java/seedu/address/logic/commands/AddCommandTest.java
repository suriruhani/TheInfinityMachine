package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ParserMode;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.SourceManager;
import seedu.address.model.source.Source;
import seedu.address.storage.PinnedSourcesStorageOperationsCenter;
import seedu.address.testutil.SourceBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullSource_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_sourceAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSourceAdded modelStub = new ModelStubAcceptingSourceAdded();
        Source validSource = new SourceBuilder().build();

        CommandResult commandResult = new AddCommand(validSource).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validSource), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validSource), modelStub.sourcesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateSource_throwsCommandException() throws Exception {
        Source validSource = new SourceBuilder().build();
        AddCommand addCommand = new AddCommand(validSource);
        ModelStub modelStub = new ModelStubWithSource(validSource);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_SOURCE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Source alice = new SourceBuilder().withTitle("Alice").build();
        Source bob = new SourceBuilder().withTitle("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getSourceManagerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSourceManagerFilePath(Path sourceManagerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getDeletedSourceFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeletedSourceFilePath(Path sourceManagerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSource(Source source) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeletedSource(Source source) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSourceAtIndex(Source source, int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDeletedSourceAtIndex(Source source, int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSourceManager(ReadOnlySourceManager newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeletedSources(ReadOnlyDeletedSources deletedSources) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySourceManager getSourceManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyDeletedSources getDeletedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSource(Source source) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDeletedSource(Source source) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSource(Source target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeDeletedSource(Source target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSource(Source target, Source editedSource) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDeletedSource(Source target, Source editedSource) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Source> getFilteredSourceList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSourceList(Predicate<Source> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoSourceManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoSourceManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoSourceManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoSourceManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitSourceManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoDeletedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoDeletedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoDeletedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoDeletedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitDeletedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<Source> selectedSourceProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Source getSelectedSource() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedSource(Source source) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void switchToDeletedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void switchToSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getNumberOfPinnedSources() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNumberOfPinnedSources(int newNumber) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setParserMode(ParserMode mode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ParserMode getParserMode() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PinnedSourcesStorageOperationsCenter getStorageOperationsCenter() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithSource extends ModelStub {
        private final Source source;

        ModelStubWithSource(Source source) {
            requireNonNull(source);
            this.source = source;
        }

        @Override
        public boolean hasSource(Source source) {
            requireNonNull(source);
            return this.source.isSameSource(source);
        }
    }

    /**
     * A Model stub that always accept the source being added.
     */
    private class ModelStubAcceptingSourceAdded extends ModelStub {
        final ArrayList<Source> sourcesAdded = new ArrayList<>();

        @Override
        public boolean hasSource(Source source) {
            requireNonNull(source);
            return sourcesAdded.stream().anyMatch(source::isSameSource);
        }

        @Override
        public void addSource(Source source) {
            requireNonNull(source);
            sourcesAdded.add(source);
        }

        @Override
        public void commitSourceManager() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlySourceManager getSourceManager() {
            return new SourceManager();
        }
    }

}
