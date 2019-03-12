package seedu.address.model.source;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Source in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Source {

    private final Title title;
    private final Type type;
    private final Detail detail;

    private final Set<Tag> tags = new HashSet<>();

    public Source(Title title, Type type, Detail detail, Set<Tag> tags) {
        requireAllNonNull(title, type, detail, tags);
        this.title = title;
        this.type = type;
        this.detail = detail;
        this.tags.addAll(tags);
    }

    public Title getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }

    public Detail getDetail() {
        return detail;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both sources of the same title have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two sources.
     */
    public boolean isSameSource(Source otherSource) {
        if (otherSource == this) {
            return true;
        }

        return otherSource != null
                && otherSource.getTitle().equals(getTitle())
                && (otherSource.getType().equals(getType()) || otherSource.getDetail().equals(getDetail()));
    }

    /**
     * Returns true if both sources have the same identity and data fields.
     * This defines a stronger notion of equality between two sources.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Source)) {
            return false;
        }

        Source otherSource = (Source) other;
        return otherSource.getTitle().equals(getTitle())
                && otherSource.getType().equals(getType())
                && otherSource.getDetail().equals(getDetail())
                && otherSource.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, type, detail, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Type: ")
                .append(getType())
                .append(" Detail: ")
                .append(getDetail())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
