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

        String titleKeywords = keywords.getValue(PREFIX_TITLE).orElse("");
        String typeKeywords = keywords.getValue(PREFIX_TYPE).orElse("");
        String detailsKeywords = keywords.getValue(PREFIX_DETAILS).orElse("");
        List<String> tagKeywords = keywords.getAllValues(PREFIX_TAG);

        if (titleKeywords.equals("") && (tagKeywords.isEmpty() || checkAllEmpty(tagKeywords))
                && typeKeywords.equals("") && detailsKeywords.equals("")) {
            return false;
        }

        if (!titleKeywords.equals("")) {
            result = result && matchTitleKeywords(titleKeywords, source);
        }

        if (!typeKeywords.equals("")) {
            result = result && matchTypeKeywords(typeKeywords, source);
        }

        if (!detailsKeywords.equals("")) {
            result = result && matchDetailKeywords(detailsKeywords, source);
        }

        if (!tagKeywords.isEmpty() && !checkAllEmpty(tagKeywords)) {
            List<String> listTitleKeywords = new ArrayList<>();
            for (String tag : tagKeywords) {
                listTitleKeywords.add(tag.trim());
            }
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
    private boolean matchDetailKeywords(String detailsKeywords, Source source) {
        List<String> listDetailKeywords = Arrays.asList(detailsKeywords.trim().split("\\s+"));
        return listDetailKeywords.stream()
                .anyMatch(keyword -> (source.getDetail().detail.toLowerCase().contains(keyword.toLowerCase())));
    }

    /**
     * Evaluates true for sources that have types that contains the types entered by the user as an argument
     * @param typeKeywords entered by the user
     * @param source to be tested
     * @return true if matches, else false
     */
    private boolean matchTypeKeywords(String typeKeywords, Source source) {
        List<String> listTypeKeywords = Arrays.asList(typeKeywords.trim().split("\\s+"));
        return listTypeKeywords.stream()
                .anyMatch(keyword -> (source.getType().type.toLowerCase()).contains(keyword.toLowerCase()));
    }

    /**
     * Evaluates true for sources that have title that contains the title entered by the user as an argument
     * @param titleKeywords entered by the user
     * @param source to be tested
     * @return true if matches, else false
     */
    private boolean matchTitleKeywords(String titleKeywords, Source source) {
        List<String> listTitleKeywords = Arrays.asList(titleKeywords.trim().split("\\s+"));
        return listTitleKeywords.stream()
                .anyMatch(keyword -> (source.getTitle().title.toLowerCase()).contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SourceContainsKeywordsPredicate) other).keywords)); // state check
    }
}
