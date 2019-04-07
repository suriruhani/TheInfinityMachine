package seedu.address.model.source;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Tests that a {@code Source}'s {@code Title}, {@code Type} {@code Detail}, and {@code Tag}
 * contain the keywords given. Performs a logical AND by checking if all those keywords
 * are present in the output source.
 */
public class SourceContainsKeywordsPredicate implements Predicate<Source> {
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
            return false;
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
     * Evaluates true for sources that have at least one tag that contains the tags (ie. a substring)
     * entered by the user as an argument
     * @param tagKeywords entered by user
     * @param source to be tested
     * @return true if present, else false
     */
    private boolean matchTagKeywords(List<String> tagKeywords, Source source) {
        boolean result = true;
        for (String tag : tagKeywords) {
            result = result && source.getTags().stream()
                    .anyMatch(keyword -> (keyword.tagName.trim().toLowerCase()).contains(tag.trim().toLowerCase()));
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
        boolean result = true;
        for (String detail : detailsKeywords) {
            result = result && (source.getDetail().detail.toLowerCase().contains(detail.toLowerCase()));
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
        boolean result = true;
        for (String type : typeKeywords) {
            result = result && (source.getType().type.toLowerCase().contains(type.toLowerCase()));
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
        boolean result = true;
        for (String title : titleKeywords) {
            result = result && (source.getTitle().title.toLowerCase().contains(title.toLowerCase()));
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SourceContainsKeywordsPredicate) other).keywords)); // state check
    }
}
