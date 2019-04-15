package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.source.Source;

/**
 * An UI component that displays information of a {@code Source}.
 */
public class SourceCard extends UiPart<Region> {

    private static final String FXML = "SourceListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exceptions will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Source source;

    @FXML
    private HBox cardPane;
    @FXML
    private FlowPane pinned;
    @FXML
    private Label id;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label type;
    @FXML
    private Label detail;
    @FXML
    private FlowPane tags;

    public SourceCard(Source source, int displayedIndex) {
        super(FXML);
        this.source = source;

        if (source.getPinnedState() == true) {
            pinned.getChildren().add(new Label("Pinned"));
        }

        id.setText(displayedIndex + ". ");
        title.setText(source.getTitle().title);
        author.setText(source.getAuthor().author);
        type.setText(source.getType().type);
        detail.setText(source.getDetail().detail);
        source.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SourceCard)) {
            return false;
        }

        // state check
        SourceCard card = (SourceCard) other;
        return id.getText().equals(card.id.getText())
                && source.equals(card.source);
    }
}
