package seedu.address.model.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;

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

        String titleKeywords = keywords.getValue(PREFIX_TITLE).orElse("");
        String typeKeywords = keywords.getValue(PREFIX_TYPE).orElse("");
        String detailsKeywords = keywords.getValue(PREFIX_DETAILS).orElse("");
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
            List<String> listTitleKeywords = new ArrayList<>();
            for (String tag : tagKeywords) {
                listTitleKeywords.add(tag.trim());
            }
            result = result && matchTagKeywords(tagKeywords, source);
        }

        return result;
    }

    private boolean matchTagKeywords(List<String> tagKeywords, Source source) {
        boolean result = true;
        for (String tag : tagKeywords) {
            result = result && source.getTags().stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tag.trim(), keyword.tagName));
        }
        return result;
    }

    private boolean matchDetailKeywords(String detailsKeywords, Source source) {
        List<String> listTitleKeywords = Arrays.asList(detailsKeywords.trim().split("\\s+"));
        return listTitleKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(source.getDetail().detail, keyword));
    }

    private boolean matchTypeKeywords(String typeKeywords, Source source) {
        List<String> listTitleKeywords = Arrays.asList(typeKeywords.trim().split("\\s+"));
        return listTitleKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(source.getType().type, keyword));
    }

    private boolean matchTitleKeywords(String titleKeywords, Source source) {
        List<String> listTitleKeywords = Arrays.asList(titleKeywords.trim().split("\\s+"));
        return listTitleKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(source.getTitle().title, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SourceContainsKeywordsPredicate) other).keywords)); // state check
    }

}
