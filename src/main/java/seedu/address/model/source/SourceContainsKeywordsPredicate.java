package seedu.address.model.source;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.Tag;

import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Tests that a {@code Source}'s {@code Title}, {@code Type}, {@code Source} and {@code Detail}
 * matches the keywords given. Performs a logical AND by checking if all those keywords
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

        String titleKeywords = keywords.getValue(PREFIX_TITLE).get();
        String typeKeywords = keywords.getValue(PREFIX_TYPE).get();
        String detailsKeywords = keywords.getValue(PREFIX_DETAILS).get();
        List<String> tagKeywords = keywords.getAllValues(PREFIX_TAG);

        if (titleKeywords.equals("") && tagKeywords.isEmpty() && typeKeywords.equals("")
                && detailsKeywords.equals("")) {
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
        
        if (!tagKeywords.isEmpty()) {
            result = result && matchTagKeywords(tagKeywords, source);
        }

        return result;

        // return keywords.stream()
        // .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(source.getTitle().title, keyword));
    }

    private boolean matchTagKeywords(List<String> tagKeywords, Source source) {


        return true;
    }

    private boolean matchDetailKeywords(String detailsKeywords, Source source) {
        return true;
    }

    private boolean matchTypeKeywords(String typeKeywords, Source source) {
        return true;
    }

    private boolean matchTitleKeywords(String titleKeywords, Source source) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SourceContainsKeywordsPredicate) other).keywords)); // state check
    }

}
