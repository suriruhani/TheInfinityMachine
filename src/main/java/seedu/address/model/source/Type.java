package seedu.address.model.source;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.source.Source.validateSourceAttribute;

/**
 * Represents a Source's Type in the infinity machine.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            "Type should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the type must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String type;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(validateSourceAttribute(type, VALIDATION_REGEX), MESSAGE_CONSTRAINTS);
        this.type = type;
    }

    public static boolean isValidType(String test) {
        return validateSourceAttribute(test, VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && this.type.equals(((Type) other).type)); // state check
    }

    @Override
    public int hashCode() {
        return this.type.hashCode();
    }


}
