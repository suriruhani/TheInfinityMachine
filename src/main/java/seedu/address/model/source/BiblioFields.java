package seedu.address.model.source;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;

/**
 * Represents a field for bibliographical information in the address book.
 * Guarantees: All entries are populated by placeholders if not defined by user.
 */
public class BiblioFields {

    public static final String[] ACCEPTED_FIELD_HEADERS =
        {"City", "Journal", "Pages", "Publisher", "URL", "Webpage", "Website", "Day", "Month", "Year"};


    private final String[] fieldBodies = new String[ACCEPTED_FIELD_HEADERS.length];

    /**
     * Constructs an empty {@code BiblioFields object}.
     *
     */
    public BiblioFields() {
        Arrays.fill(fieldBodies, "");
    }

    /**
     * Replaces a field in BiblioFields.
     *
     * @param header A valid biblio field type
     * @param body A valid biblio field body
     * @return true if edit is successful
     */
    public boolean replaceField (String header, String body) throws IllegalArgumentException {
        requireAllNonNull(header, body);
        if (getHeaderIndex(header) == 0) {
            return false;
        } else {
            fieldBodies[getHeaderIndex(header)] = body;
            return true;
        }
    }

    public String[] getFieldBodies() {
        return fieldBodies;
    }

    public String getField(String header) {
        return fieldBodies[getHeaderIndex(header)];
    }

    private int getHeaderIndex(String header) {
        for (int i = 0; i < ACCEPTED_FIELD_HEADERS.length; i++) {
            if (ACCEPTED_FIELD_HEADERS[i].equals(header)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BiblioFields // instanceof handles nulls
                && fieldBodies.equals(((BiblioFields) other).getFieldBodies())); // State check
    }

    @Override
    public int hashCode() {
        return fieldBodies.toString().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        String output = "Biblio fields:\n";
        for (int i = 0; i < ACCEPTED_FIELD_HEADERS.length; i++) {
            output = output + String.format("[%s: %s]\n", ACCEPTED_FIELD_HEADERS[i], fieldBodies[i]);
        }
        return output;
    }
}
