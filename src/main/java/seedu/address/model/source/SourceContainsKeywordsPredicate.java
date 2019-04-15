package seedu.address.model.source;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Tests that a {@code Source}'s {@code Title}, {@code Type} {@code Detail}, and {@code Tag}
 * contain the keywords given. Performs a logical AND by checking if all those keywords
 * are present in the output source. Substring matching enable. Minor typos accounted for.
 */
public class SourceContainsKeywordsPredicate implements Predicate<Source> {
    public static final int LEVENSHTIEN_DISTANCE_CONSTANT = 5;
    private final ArgumentMultimap keywords;

    public SourceContainsKeywordsPredicate(ArgumentMultimap keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Source source) {
        boolean result = true;

        List<String> titleKeywords = keywords.getAllValues(PREFIX_TITLE);
        List<String> typeKeywords = keywords.getAllValues(PREFIX_TYPE);
        List<String> detailsKeywords = keywords.getAllValues(PREFIX_DETAILS);
        List<String> tagKeywords = keywords.getAllValues(PREFIX_TAG);

        if ((titleKeywords.isEmpty() || checkAllEmpty(titleKeywords))
                && (typeKeywords.isEmpty() || checkAllEmpty(typeKeywords))
                && (detailsKeywords.isEmpty() || checkAllEmpty(detailsKeywords))
                && (tagKeywords.isEmpty() || checkAllEmpty(tagKeywords))) {
            return true;
            //show all when tags empty
        }

        if (!titleKeywords.isEmpty() && !checkAllEmpty(titleKeywords)) {
            result = result && matchTitleKeywords(titleKeywords, source);
        }

        if (!typeKeywords.isEmpty() && !checkAllEmpty(typeKeywords)) {
            result = result && matchTypeKeywords(typeKeywords, source);
        }

        if (!detailsKeywords.isEmpty() && !checkAllEmpty(detailsKeywords)) {
            result = result && matchDetailKeywords(detailsKeywords, source);
        }

        if (!tagKeywords.isEmpty() && !checkAllEmpty(tagKeywords)) {
            result = result && matchTagKeywords(tagKeywords, source);
        }

        return result;
    }

    /**
     * Evaluates true if all of the entries in a list of strings is empty
     *
     */
    public boolean checkAllEmpty(List<String> keywords) {
        boolean result = true;
        for (String s : keywords) {
            result = result && s.equals("");
        }
        return result;
    }

    /**
     * Evaluates the number of swaps needed to transform one string to the other [case insensitive]
     * Inspired from https://www.baeldung.com/java-levenshtein-distance[Baeldung].
     * @param a a string to be transformed from
     * @param b a string to be transformed to
     * @return int number of swaps needed for the transformation
     */
    public static int levenshtienDist(String a, String b) {
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    /**
     * Evaluates true if the source field value is similar enough as per the Levenshtien Distance logic,
     * with the threshold as LEVENSHTIEN_DISTANCE_CONSTANT
     * @param sourceField the field value of the source being tested [lower case]
     * @param userField the argument passed by the user [lower case]
     * @return true if words are similar enough, else false
     */
    public static boolean checkLevenshtienSimilarity(String sourceField, String userField) {
        return levenshtienDist(sourceField, userField) < LEVENSHTIEN_DISTANCE_CONSTANT;
    }

    /**
     * Evaluates true for sources that have at least one tag that contains the tags (ie. a substring)
     * entered by the user as an argument
     * @param tagKeywords entered by user
     * @param source to be tested
     * @return true if present, else false
     */
    private boolean matchTagKeywords(List<String> tagKeywords, Source source) {
        boolean result = true;
        for (String tag : tagKeywords) {
            String userTag = tag.trim().toLowerCase();
            result = result && source.getTags().stream()
                    .anyMatch(keyword -> (keyword.tagName.trim().toLowerCase()).contains(userTag)
                    || checkLevenshtienSimilarity(keyword.tagName.trim().toLowerCase(), userTag));
        }
        return result;
    }

    /**
     * Evaluates true for sources that have details that contains the details entered by the user as an argument
     * @param detailsKeywords entered by the user
     * @param source to be tested
     * @return true if matches, else false
     */
    private boolean matchDetailKeywords(List<String> detailsKeywords, Source source) {
        String sourceDetail = source.getDetail().detail.toLowerCase();
        String userDetail;
        boolean result = true;
        for (String detail : detailsKeywords) {
            userDetail = detail.toLowerCase();
            result = result && (sourceDetail.contains(userDetail)
                    || checkLevenshtienSimilarity(sourceDetail, userDetail));
        }
        return result;
    }

    /**
     * Evaluates true for sources that have types that contains the types entered by the user as an argument
     * @param typeKeywords entered by the user
     * @param source to be tested
     * @return true if matches, else false
     */
    private boolean matchTypeKeywords(List<String> typeKeywords, Source source) {
        String sourceType = source.getType().type.toLowerCase();
        String userType;
        boolean result = true;
        for (String type : typeKeywords) {
            userType = type.toLowerCase();
            result = result && (sourceType.contains(userType)
                            || checkLevenshtienSimilarity(sourceType, userType));
        }
        return result;
    }

    /**
     * Evaluates true for sources that have title that contains the title entered by the user as an argument
     * @param titleKeywords entered by the user
     * @param source to be tested
     * @return true if matches, else false
     */
    private boolean matchTitleKeywords(List<String> titleKeywords, Source source) {
        String sourceTitle = source.getTitle().title.toLowerCase();
        String userTitle;
        boolean result = true;
        for (String title : titleKeywords) {
            userTitle = title.toLowerCase();
            result = result && ((sourceTitle.contains(userTitle))
                            || checkLevenshtienSimilarity(sourceTitle, userTitle));
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SourceContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public int hashCode() {
        return keywords.hashCode();
    }
}
