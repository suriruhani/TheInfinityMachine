package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.source.Source;

/**
 * Panel containing the list of sources.
 */
public class SourceListPanel extends UiPart<Region> {
    private static final String FXML = "SourceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SourceListPanel.class);

    @FXML
    private ListView<Source> sourceListView;

    public SourceListPanel(ObservableList<Source> sourceList, ObservableValue<Source> selectedSource,
                           Consumer<Source> onSelectedSourceChange) {
        super(FXML);
        sourceListView.setItems(sourceList);
        sourceListView.setCellFactory(listView -> new SourceListViewCell());
        sourceListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in source list panel changed to : '" + newValue + "'");
            onSelectedSourceChange.accept(newValue);
        });
        selectedSource.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected source changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected source,
            // otherwise we would have an infinite loop.
            if (Objects.equals(sourceListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                sourceListView.getSelectionModel().clearSelection();
            } else {
                int index = sourceListView.getItems().indexOf(newValue);
                sourceListView.scrollTo(index);
                sourceListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Source} using a {@code SourceCard}.
     */
    class SourceListViewCell extends ListCell<Source> {
        @Override
        protected void updateItem(Source source, boolean empty) {
            super.updateItem(source, empty);

            if (empty || source == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SourceCard(source, getIndex() + 1).getRoot());
            }
        }
    }

}
