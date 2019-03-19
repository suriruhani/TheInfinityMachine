package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Source objects.
 */
public class SourceBuilder {

    public static final String DEFAULT_TITLE = "Default Title";
    public static final String DEFAULT_TYPE = "Default Type";
    public static final String DEFAULT_DETAIL = "Default Detail";

    private Title title;
    private Type type;
    private Detail detail;
    private Set<Tag> tags;

    public SourceBuilder() {
        title = new Title(DEFAULT_TITLE);
        type = new Type(DEFAULT_TYPE);
        detail = new Detail(DEFAULT_DETAIL);
        tags = new HashSet<>();
    }

    /**
     * Initializes the SourceBuilder with the data of {@code sourceToCopy}.
     */
    public SourceBuilder(Source sourceToCopy) {
        title = sourceToCopy.getTitle();
        type = sourceToCopy.getType();
        detail = sourceToCopy.getDetail();
        tags = new HashSet<>(sourceToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code Source} that we are building.
     */
    public SourceBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Source} that we are building.
     */
    public SourceBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Source} that we are building.
     */
    public SourceBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Detail} of the {@code Source} that we are building.
     */
    public SourceBuilder withDetail(String detail) {
        this.detail = new Detail(detail);
        return this;
    }

    public Source build() {
        return new Source(title, type, detail, tags);
    }

}
