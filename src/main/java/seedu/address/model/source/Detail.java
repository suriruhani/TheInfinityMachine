package seedu.address.model.source;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.source.Source.validateSourceAttribute;

/**
 * Represents a Source's Detail in the infinity machine.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class Detail {

    public static final String MESSAGE_CONSTRAINTS = "Details can take any value, and it should not be blank";

    /*
     * The first character of the detail must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String detail;

    /**
     * Constructs a {@code Detail}.
     *
     * @param detail A valid detail.
     */
    public Detail(String detail) {
        requireNonNull(detail);
        checkArgument(validateSourceAttribute(detail, VALIDATION_REGEX), MESSAGE_CONSTRAINTS);
        this.detail = detail;
    }

    public static boolean isValidDetail(String test) {
        return validateSourceAttribute(test, VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Detail // instanceof handles nulls
                && detail.equals(((Detail) other).detail)); // state check
    }

    @Override
    public int hashCode() {
        return detail.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return detail;
    }

}
