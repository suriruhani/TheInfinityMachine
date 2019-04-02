package seedu.address.model.source;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Source}'s {@code Title}, {@code Type}, {@code Source} and {@code Detail}
 * matches the keywords given. Performs a logical and by checking if all those keywords
 * are present in the output source.
 */
public class SourceContainsKeywordsPredicate implements Predicate<Source> {
    private final List<String> keywords;

    public SourceContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Source source) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(source.getTitle().title, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((SourceContainsKeywordsPredicate) other).keywords)); // state check
    }

}
