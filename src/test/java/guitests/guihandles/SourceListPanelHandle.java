package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.source.Source;

/**
 * Provides a handle for {@code SourceListPanel} containing the list of {@code SourceCard}.
 */
public class SourceListPanelHandle extends NodeHandle<ListView<Source>> {
    public static final String SOURCE_LIST_VIEW_ID = "#sourceListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Source> lastRememberedSelectedSourceCard;

    public SourceListPanelHandle(ListView<Source> sourceListPanelNode) {
        super(sourceListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code SourceCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public SourceCardHandle getHandleToSelectedCard() {
        List<Source> selectedSourceList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedSourceList.size() != 1) {
            throw new AssertionError("Source list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(SourceCardHandle::new)
                .filter(handle -> handle.equals(selectedSourceList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Source> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code source}.
     */
    public void navigateToCard(Source source) {
        if (!getRootNode().getItems().contains(source)) {
            throw new IllegalArgumentException("Source does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(source);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code SourceCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the source card handle of a source associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public SourceCardHandle getSourceCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(SourceCardHandle::new)
                .filter(handle -> handle.equals(getSource(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Source getSource(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code SourceCard} in the list.
     */
    public void rememberSelectedSourceCard() {
        List<Source> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedSourceCard = Optional.empty();
        } else {
            lastRememberedSelectedSourceCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code SourceCard} is different from the value remembered by the most recent
     * {@code rememberSelectedSourceCard()} call.
     */
    public boolean isSelectedSourceCardChanged() {
        List<Source> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedSourceCard.isPresent();
        } else {
            return !lastRememberedSelectedSourceCard.isPresent()
                    || !lastRememberedSelectedSourceCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
