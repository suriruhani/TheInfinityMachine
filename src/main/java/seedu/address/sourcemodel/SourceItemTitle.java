package seedu.address.sourcemodel;

/**
 * Represents a Title for the source.
 */
public class SourceItemTitle {
    private String title;

    public SourceItemTitle(String title) {
        this.title = title;
    }

    public String getTitleString() {
        return title;
    }

    public void setTitleString(String newTitle) {
        title = newTitle;
    }
}
