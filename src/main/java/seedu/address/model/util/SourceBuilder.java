package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.source.Author;
import seedu.address.model.source.BiblioFields;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;


/**
 * A utility class to help with building Source objects.
 */
public class SourceBuilder {

    public static final String DEFAULT_TITLE = "Default Title";
    public static final String DEFAULT_TYPE = "Default Type";
    public static final String DEFAULT_AUTHOR = "Default Author";
    public static final String DEFAULT_DETAIL = "Default Detail";

    private Title title;
    private Type type;
    private Author author;
    private Detail detail;
    private Set<Tag> tags;
    private BiblioFields biblioFields = new BiblioFields();

    public SourceBuilder() {
        title = new Title(DEFAULT_TITLE);
        type = new Type(DEFAULT_TYPE);
        author = new Author(DEFAULT_AUTHOR);
        detail = new Detail(DEFAULT_DETAIL);

        tags = new HashSet<>();
    }

    /**
     * Initializes the SourceBuilder with the data of {@code sourceToCopy}.
     */
    public SourceBuilder(Source sourceToCopy) {
        title = sourceToCopy.getTitle();
        type = sourceToCopy.getType();
        author = sourceToCopy.getAuthor();
        detail = sourceToCopy.getDetail();
        tags = new HashSet<>(sourceToCopy.getTags());
        biblioFields = sourceToCopy.getBiblioFields();
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
     * Sets the {@code Type} of the {@code Source} that we are building.
     */
    public SourceBuilder withAuthor(String author) {
        this.author = new Author(author);
        return this;
    }

    /**
     * Sets the {@code Detail} of the {@code Source} that we are building.
     */
    public SourceBuilder withDetail(String detail) {
        this.detail = new Detail(detail);
        return this;
    }

    /**
     * Sets the {@code Detail} of the {@code Source} that we are building.
     */
    public SourceBuilder withBiblioFields() {
        for (int i = 0; i < BiblioFields.ACCEPTED_FIELD_HEADERS.length; i++) {
            this.biblioFields.replaceField(BiblioFields.ACCEPTED_FIELD_HEADERS[i],
                    "Foo" + BiblioFields.ACCEPTED_FIELD_HEADERS[i]);
        }
        return this;
    }

    public Source build() {
        return new Source(title, author, type, detail, tags, biblioFields);
    }

}
