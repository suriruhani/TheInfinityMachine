package seedu.address.sourcemodel;

/**
 * Represents a Tag for the source.
 */
public class SourceItemTag {
    private String tag;

    public SourceItemTag(String tag) {
        this.tag = tag;
    }

    public String getTagString() {
        return tag;
    }

    public void setTagString(String newTag) {
        tag = newTag;
    }
}
