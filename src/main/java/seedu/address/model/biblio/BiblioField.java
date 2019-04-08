package seedu.address.model.biblio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a field for bibliographical information in the address book.
 * Guarantees: immutable;
 * type is valid as declared in {@link #isValidType(String)}
 * body is valid as declared in {@link #isValidBody(String)};
 */
public class BiblioField {

    public static final String MESSAGE_CONSTRAINTS = "Field bodies can take any value, and it should not be blank";
    public static final String MESSAGE_TYPE_CONSTRAINTS = "Field type should be one of the following:\n"
            + "City\n"
            + "Date\n"
            + "Journal\n"
            + "Pages\n"
            + "Publisher\n"
            + "URL\n"
            + "Webpage\n"
            + "Website\n";
    public static final String BODY_VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String[] ACCEPTED_BIBLIO_TYPES =
        {"City", "Date", "Journal", "Pages", "Publisher", "URL", "Webpage", "Website"};

    public final String type;
    public final String body;

    /**
     * Constructs a {@code Tag}.
     *
     * @param type A valid field type.
     * @param body A valid body.
     */
    public BiblioField(String type, String body) {
        requireNonNull(type);
        requireNonNull(body);
        checkArgument(isValidType(body), MESSAGE_TYPE_CONSTRAINTS);
        checkArgument(isValidBody(body), MESSAGE_CONSTRAINTS);
        this.type = type;
        this.body = body;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        for (String type: ACCEPTED_BIBLIO_TYPES) {
            if (test.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a given string is a valid body.
     */
    public static boolean isValidBody(String test) {
        return test.matches(BODY_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BiblioField // instanceof handles nulls
                && type.equals(((BiblioField) other).type)
                && body.equals(((BiblioField) other).body)); // state check
    }

    @Override
    public int hashCode() {
        String hashString = String.format("%s: %s", type, body);
        return hashString.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[%s: %s]", type, body);
    }
}
