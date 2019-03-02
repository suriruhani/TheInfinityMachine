package seedu.address.sourcemodel;

import java.util.ArrayList;

public class Source {
    /** Title of the source as detailed by the user, can be anything **/
    private String sourceTitle;

    /** Type of the source, defined by the user but should follow some convention like "Book" or "Website" **/
    private String sourceType;

    /** Details of the source as detailed by the user, summary of source materials, can be anything **/
    private String sourceDetails;

    /** List of tags for this source, while user defined, should follow some conventions to avoid duplicates **/
    private ArrayList<String> sourceTags;

    public Source (String sourceTitle, String sourceType, String sourceDetails, ArrayList<String> sourceTags) {
        this.sourceTitle = sourceTitle;
        this.sourceType = sourceType;
        this.sourceDetails = sourceDetails;
        this.sourceTags = sourceTags;
    }

    /** Public get functions for all the details of the source, to be used for retrieving information **/
    public String getSourceTitle() {
        return this.sourceTitle;
    }
    public String getSourceType() {
        return this.sourceType;
    }
    public String getSourceDetails() {
        return this.sourceDetails;
    }
    public ArrayList<String> getSourceTags() {
        return this.sourceTags;
    }

    /** Public set functions for all the details of the source, to be used for editing information **/
    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    public void setSourceDetails(String sourceDetails) {
        this.sourceDetails = sourceDetails;
    }
    public void setSourceTags(ArrayList<String> sourceTags) {
        this.sourceTags = sourceTags;
    }
}
