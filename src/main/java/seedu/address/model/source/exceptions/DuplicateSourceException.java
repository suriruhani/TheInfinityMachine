package seedu.address.model.source.exceptions;

/**
 * Signals that the operation will result in duplicate Sources (Sources are considered duplicates if they have the same
 * identity).
 */
public class DuplicateSourceException extends RuntimeException {
    public DuplicateSourceException() {
        super("Operation would result in duplicate sources");
    }
}
