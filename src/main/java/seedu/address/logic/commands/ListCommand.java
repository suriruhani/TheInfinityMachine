package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SOURCES;

import java.util.function.Predicate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.source.Source;


/**
 * Lists all sources in the Source Database to the user, or the top N sources where N may
 * be an optional argument supplied by the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List all the sources in the database to the user, or optionally, the top N sources if"
            + " an argument is supplied.\n"
            + "Parameters: [INDEX] (Optional, must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 20";

    public static final String MESSAGE_LIST_ALL_SUCCESS = "Listed all sources!";

    public static final String MESSAGE_LIST_N_SUCCESS = "Listed top %d sources!";

    public static final String MESSAGE_LIST_X_Y_SUCCESS = "Listed sources from %d to %d!";

    private Index targetIndex = null;
    private Index fromIndex =  null;
    private Index toIndex = null;

    public ListCommand() {}

    //Constructor overloading to account for an optional parameter
    public ListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public ListCommand(Index toIndex, Index fromIndex) {
        this.toIndex = toIndex;
        this.fromIndex = fromIndex;
    }

    /**
     * A method which constructs a predicate to output top n sources
     * Parameters: positive integer n
     */
    private Predicate<Source> makePredicateForTopN(int n) {
        return new Predicate<>() {
            private int count = 0;
            public boolean test(Source source) {
                if (count < n) {
                    count++;
                    return true;
                } else {
                    return false;
                }
            }
        };
    }

    private Predicate<Source> makePredicateForXToY(int x, int y) {
        return new Predicate<>() {
            private int count = 1;
            public boolean test(Source source) {
                if (count >= x && count <= y) {
                    count++;
                    return true;
                } else {
                    count++;
                    return false;
                }
            }
        };
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws IndexOutOfBoundsException {
        requireNonNull(model);
        //shortcut to obtain the entire list of all sources by first displaying an unfiltered list
        model.updateFilteredSourceList(PREDICATE_SHOW_ALL_SOURCES);
        int size = model.getFilteredSourceList().size();
        if (toIndex != null && fromIndex != null) {
            fromIndex = fromIndex.getOneBased() > size ? Index.fromOneBased(size) : fromIndex;
            model.updateFilteredSourceList(makePredicateForXToY(toIndex.getOneBased(), fromIndex.getOneBased()));
            return new CommandResult(String.format(MESSAGE_LIST_X_Y_SUCCESS,
                    toIndex.getOneBased(), fromIndex.getOneBased()));
        } else if (targetIndex != null) { //when LIST is used with an argument (show N)
            //to ensure N is capped at list size
            targetIndex = targetIndex.getOneBased() > size ? Index.fromOneBased(size) : targetIndex;
            model.updateFilteredSourceList(makePredicateForTopN(targetIndex.getOneBased()));
            return new CommandResult(String.format(MESSAGE_LIST_N_SUCCESS, targetIndex.getOneBased()));
        } else { //when LIST is used without an argument (show all)
            return new CommandResult(MESSAGE_LIST_ALL_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && targetIndex.equals(((ListCommand) other).targetIndex)); // state check
    }
}
