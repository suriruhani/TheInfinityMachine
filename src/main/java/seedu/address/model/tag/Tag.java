package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Tag names should be alphanumeric";
    public static final String MESSAGE_TYPE_CONSTRAINTS = "Tag types should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String type;
    public final String tagName;

    /**
     * Constructs a {@code Tag} with type "Search".
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_NAME_CONSTRAINTS);
        this.type = "Search";
        this.tagName = tagName;
    }

    /**
     * Constructs a {@code Tag} with variable type.
     *
     * @param type A valid type.
     * @param tagName A valid tag name.
     */
    public Tag(String type, String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_NAME_CONSTRAINTS);
        checkArgument(isValidType(type), MESSAGE_TYPE_CONSTRAINTS);
        this.type = type;
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)
                && type.equals(((Tag) other).type)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[%s: %s]", type, tagName);
    }
}
