package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.RecycleBinParser;
import seedu.address.logic.parser.SourceManagerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ParserMode;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.source.Source;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CommandHistory history;
    private final SourceManagerParser sourceManagerParser;
    private final RecycleBinParser recycleBinParser;
    private SourceManagerParser mainParser;
    private boolean sourceManagerModified;
    private boolean deletedSourcesModified;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        history = new CommandHistory();
        sourceManagerParser = new SourceManagerParser();
        recycleBinParser = new RecycleBinParser();
        model.setParserMode(ParserMode.SOURCE_MANAGER); // starts with source manager parser

        // Set sourceManagerModified to true whenever the models' source manager is modified.
        model.getSourceManager().addListener(observable -> sourceManagerModified = true);
        model.getDeletedSources().addListener(observable -> deletedSourcesModified = true);
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        sourceManagerModified = false;
        deletedSourcesModified = false;

        switch(model.getParserMode()) {
        case RECYCLE_BIN:
            mainParser = recycleBinParser;
            break;
        case SOURCE_MANAGER:
            mainParser = sourceManagerParser;
            break;
        default:
            mainParser = sourceManagerParser;
            break;

        }

        CommandResult commandResult;
        try {
            Command command = mainParser.parseCommand(commandText);
            commandResult = command.execute(model, history);
        } finally {
            history.add(commandText);
        }

        if (sourceManagerModified) {
            logger.info("Source manager modified, saving to file.");
            try {
                storage.saveSourceManager(model.getSourceManager());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        if (deletedSourcesModified) {
            logger.info("Delete Sources modified, saving to file.");
            try {
                storage.saveDeletedSources(model.getDeletedSources());
            } catch (IOException ioe) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
            }
        }

        return commandResult;
    }

    @Override
    public ReadOnlySourceManager getSourceManager() {
        return model.getSourceManager();
    }

    @Override
    public ReadOnlyDeletedSources getDeletedSources() {
        return model.getDeletedSources();
    }

    @Override
    public ObservableList<Source> getFilteredSourceList() {
        return model.getFilteredSourceList();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public Path getSourceManagerFilePath() {
        return model.getSourceManagerFilePath();
    }

    @Override
    public Path getDeletedSourceFilePath() {
        return model.getDeletedSourceFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyProperty<Source> selectedSourceProperty() {
        return model.selectedSourceProperty();
    }

    @Override
    public void setSelectedSource(Source source) {
        model.setSelectedSource(source);
    }
}
