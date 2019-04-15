//@@author DarrenDragonLee
package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CustomOrderCommand;
import seedu.address.logic.commands.PinCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UnpinCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.source.Author;
import seedu.address.model.source.BiblioFields;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;

public class CommandHashTest {
    @Test
    public void check_hashcodeAddCommand_success() {
        HashSet<AddCommand> set = new HashSet<AddCommand>();

        AddCommand one = new AddCommand(new Source(
                new Title("Title"),
                new Author("Author"),
                new Type("Type"),
                new Detail("Detail"),
                new HashSet<Tag>(),
                new BiblioFields()));
        AddCommand two = new AddCommand(new Source(
                new Title("Title"),
                new Author("Author"),
                new Type("Type"),
                new Detail("Detail"),
                new HashSet<Tag>(),
                new BiblioFields()));

        set.add(one);
        set.add(two);

        int size = set.size();

        assertEquals(size, 1);
    }


    @Test
    public void check_hashcodeSelectCommand_success() {
        HashSet<SelectCommand> set = new HashSet<SelectCommand>();

        try {
            SelectCommand one = new SelectCommand(ParserUtil.parseIndex("2"));
            SelectCommand two = new SelectCommand(ParserUtil.parseIndex("2"));

            set.add(one);
            set.add(two);

            int size = set.size();

            assertEquals(size, 1);
        } catch (ParseException pe) {
            assertEquals(0, 1);
        }
    }

    @Test
    public void check_hashcodePinCommand_success() {
        HashSet<PinCommand> set = new HashSet<PinCommand>();

        PinCommand one = new PinCommand(2);
        PinCommand two = new PinCommand(2);

        set.add(one);
        set.add(two);

        int size = set.size();

        assertEquals(size, 1);
    }

    @Test
    public void check_hashcodeUnpinCommand_success() {
        HashSet<UnpinCommand> set = new HashSet<UnpinCommand>();

        UnpinCommand one = new UnpinCommand(2);
        UnpinCommand two = new UnpinCommand(2);

        set.add(one);
        set.add(two);

        int size = set.size();

        assertEquals(size, 1);
    }

    @Test
    public void check_hashcodeCustomOrderCommand_success() {
        HashSet<CustomOrderCommand> set = new HashSet<CustomOrderCommand>();

        CustomOrderCommand one = new CustomOrderCommand(2, 5);
        CustomOrderCommand two = new CustomOrderCommand(2, 5);

        set.add(one);
        set.add(two);

        int size = set.size();

        assertEquals(size, 1);
    }
}
