package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.source.SourceContainsKeywordsPredicate;

/**
 * Finds and lists all sources in Source Database which have any of their title, type, detail, source, tag(s)
 * containing the argument keywords. Accounts for minor typing errors when entire field value is entered.
 * Keyword matching is case insensitive, substrings are matched.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all sources whose title, type, details, "
            + "source and tags contains any of the specified keywords (case-insensitive, substring) and displays "
            + "them as a list with index numbers. Accounts for minor typing errors when entire field value is entered."
            + "Parameters: "
            + PREFIX_TITLE + "SOURCE_TITLE "
            + PREFIX_TYPE + "SOURCE_TYPE "
            + PREFIX_DETAILS + "SOURCE_DETAILS "
            + "[" + PREFIX_TAG + "SOURCE_TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Wikipedia Algorithms "
            + PREFIX_TYPE + "Website "
            + PREFIX_DETAILS + "Basic definitions of algorithms "
            + PREFIX_TAG + "Algorithms "
            + PREFIX_TAG + "Introduction";

    private final SourceContainsKeywordsPredicate predicate;

    public SearchCommand(SourceContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredSourceList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_SOURCES_LISTED_OVERVIEW, model.getFilteredSourceList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && predicate.equals(((SearchCommand) other).predicate)); // state check
    }

    @Override
    public int hashCode() {
        return predicate.hashCode();
    }
}
