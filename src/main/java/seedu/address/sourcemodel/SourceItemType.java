package seedu.address.sourcemodel;

/**
 * Represents the Type of the source.
 */
public class SourceItemType {
    private String type;

    public SourceItemType(String type) {
        this.type = type;
    }

    public String getTypeString() {
        return type;
    }

    public void setTypeString(String newType) {
        type = newType;
    }
}
