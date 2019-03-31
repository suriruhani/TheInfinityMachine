package seedu.address.model;

import seedu.address.commons.core.GuiSettings;

import java.nio.file.Path;

/**
 * Unmodifiable view of recently deleted.
 */
public interface ReadOnlyDeletedSources {

    GuiSettings getDeletedSourceGuiSettings();

    Path getDeletedSourceFilePath();

}

