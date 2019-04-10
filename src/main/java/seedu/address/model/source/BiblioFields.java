package seedu.address.model.source;

import java.util.Arrays;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a field for bibliographical information in the address book.
 * Guarantees: All entries are populated by placeholders if not defined by user.
 */
public class BiblioFields {

    public static final String[] ACCEPTED_FIELD_TYPES =
            {"City", "Journal", "Pages", "Publisher", "URL", "Webpage", "Website", "Day", "Month", "Year"};


    private final String[] fieldEntries = new String[ACCEPTED_FIELD_TYPES.length];

    /**
     * Constructs an empty {@code BiblioFields object}.
     *
     */
    public BiblioFields(){
        Arrays.fill(fieldEntries, "");
    }

    public void replaceField (String type, String entry) throws IllegalArgumentException{
        requireAllNonNull(type, entry);
        if (getTypeIndex(type) == 0) {
            throw new IllegalArgumentException("Invalid field type. Field Type must be one of the following\n"
            + ACCEPTED_FIELD_TYPES.toString());
        } else {
            fieldEntries[getTypeIndex(type)] = entry;
        }
    }

    public String[] getFieldEntries(){
        return fieldEntries;
    }

    public String getField(String type){
        return fieldEntries[getTypeIndex(type)];
    }

    private int getTypeIndex(String type){
        for (int i = 0; i < ACCEPTED_FIELD_TYPES.length; i++){
            if (ACCEPTED_FIELD_TYPES[i].equals(type)){
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BiblioFields // instanceof handles nulls
                && fieldEntries.equals(((BiblioFields) other).getFieldEntries())); // State check
    }

    @Override
    public int hashCode() {
        return fieldEntries.toString().hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        String output = "Biblio fields:\n";
        for (int i = 0; i < ACCEPTED_FIELD_TYPES.length; i++){
            output = output + String.format("[%s: %s]\n", ACCEPTED_FIELD_TYPES[i], fieldEntries[i]);
        }
        return output;
    }
}
