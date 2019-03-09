package seedu.address.sourcemodel;

/**
 * Represents the Details for the source.
 */
public class SourceItemDetails {
    private String details;

    public SourceItemDetails(String details) {
        this.details = details;
    }

    public String getDetailsString() {
        return details;
    }

    public void setDetailsString(String newDetails) {
        details = newDetails;
    }
}
