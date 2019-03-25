package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMultiset;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.source.Source;

/**
 * Provides a handle to a source card in the source list panel.
 */
public class SourceCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String TYPE_FIELD_ID = "#type";
    private static final String DETAIL_FIELD_ID = "#detail";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label typeLabel;
    private final Label detailLabel;
    private final List<Label> tagLabels;

    public SourceCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        titleLabel = getChildNode(TITLE_FIELD_ID);
        typeLabel = getChildNode(TYPE_FIELD_ID);
        detailLabel = getChildNode(DETAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getType() {
        return typeLabel.getText();
    }

    public String getDetail() {
        return detailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code source}.
     */
    public boolean equals(Source source) {
        return getTitle().equals(source.getTitle().title)
                && getType().equals(source.getType().type)
                && getDetail().equals(source.getDetail().detail)
                && ImmutableMultiset.copyOf(getTags()).equals(ImmutableMultiset.copyOf(source.getTags().stream()
                        .map(tag -> tag.tagName)
                        .collect(Collectors.toList())));
    }
}
