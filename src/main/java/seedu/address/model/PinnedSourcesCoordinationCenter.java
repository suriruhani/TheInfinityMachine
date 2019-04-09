package seedu.address.model;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager;
import seedu.address.model.source.Source;
import seedu.address.storage.PinnedSourcesStorageOperationsCenter;

/**
 * Center for coordinating all pinned source related operations
 */
public class PinnedSourcesCoordinationCenter {
    public static final String MESSAGE_SOURCE_PINNED = "The source is a pinned source.";

    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Returns a list of all the pinned sources currently in the database.
     *
     * @param model The model which is currently being used.
     *
     * @return A list containing all the pinned sources. Returns an empty list if there are none.
     */
    public static List<Source> getPinnedSources (Model model) {
        List<Source> pinnedSourcesList = new ArrayList<Source>();

        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        List<Source> completeSourceList = model.getFilteredSourceList();

        int numPinnedSources = model.getNumberOfPinnedSources();
        logger.info("Number of pinned sources: " + numPinnedSources);

        for (int i = 0; i < numPinnedSources; i++) {
            Source pinnedSource = completeSourceList.get(i);
            pinnedSourcesList.add(pinnedSource);
        }

        return pinnedSourcesList;
    }

    /**
     * Checks if a source is a pinned source.
     *
     * @param model The model which is currently being used.
     * @param index The index of the source to be analysed.
     *
     * @return Returns true if the source is pinned, return false if the source is not pinned.
     */
    public static boolean isPinnedSource (Model model, int index) {
        int numPinnedSources = model.getNumberOfPinnedSources();
        if (numPinnedSources == 0) {
            logger.info("There are no pinned sources.");
            return false;
        }

        if (index > numPinnedSources - 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Increases the number of pinned sources by 1.
     *
     * @param model The model which is currently being used.
     */
    public static void incrementPinnedSources (Model model) {
        int numPinnedSources = model.getNumberOfPinnedSources();
        numPinnedSources++;
        model.setNumberOfPinnedSources(numPinnedSources);
    }

    /**
     * Decreases the number of pinned sources by 1.
     *
     * @param model The model which is currently being used.
     */
    public static void decrementPinnedSources (Model model) {
        int numPinnedSources = model.getNumberOfPinnedSources();
        if (numPinnedSources == 0) {
            return;
        } else {
            numPinnedSources--;
            model.setNumberOfPinnedSources(numPinnedSources);
        }
    }

    /**
     * Writes the current number of pinned sources to an external file for storage.
     *
     * @param model The model which is currently being used.
     */
    public static void saveCurrentPinnedSources (Model model) {
        int numPinnedSources = model.getNumberOfPinnedSources();
        logger.info("Writing number of sources to file.");

        PinnedSourcesStorageOperationsCenter ops = model.getStorageOperationsCenter();
        ops.writeNumberOfPinnedSourcesToFile(numPinnedSources);
    }
}
