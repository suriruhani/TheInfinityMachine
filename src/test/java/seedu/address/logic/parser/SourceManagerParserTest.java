package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ENGINEERING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CountCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.source.Source;
import seedu.address.model.source.SourceContainsKeywordsPredicate;
import seedu.address.testutil.EditSourceDescriptorBuilder;
import seedu.address.testutil.SourceBuilder;
import seedu.address.testutil.SourceUtil;

public class SourceManagerParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SourceManagerParser parser;

    @Before
    public void setup() {
        parser = new SourceManagerParser(); // Reset between tests
    }

    @Test
    public void parseCommand_add() throws Exception {
        Source source = new SourceBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(SourceUtil.getAddCommand(source));
        assertEquals(new AddCommand(source), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_count() throws Exception {
        assertTrue(parser.parseCommand(CountCommand.COMMAND_WORD) instanceof CountCommand);
        assertTrue(parser.parseCommand(CountCommand.COMMAND_WORD + " 3") instanceof CountCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_SOURCE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Source source = new SourceBuilder().build();
        EditCommand.EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder(source).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_SOURCE.getOneBased() + " " + SourceUtil.getEditSourceDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_SOURCE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        String input = TITLE_DESC_ENGINEERING;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(input,
                PREFIX_TITLE, PREFIX_TYPE, PREFIX_DETAILS, PREFIX_TAG);
        SearchCommand command = (SearchCommand) parser.parseCommand(
                SearchCommand.COMMAND_WORD + TITLE_DESC_ENGINEERING);
        assertEquals(new SearchCommand(new SourceContainsKeywordsPredicate(argMultimap)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_SOURCE), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    // Meta-commands (pertaining to AliasManager)

    @Test
    public void parseMetaCommand_addAlias_validArguments() throws Exception {
        parser.parseCommand("alias count c");
        assertTrue(parser.parseCommand("c") instanceof CountCommand);
    }

    @Test
    public void parseMetaCommand_addAlias_invalidArguments1() throws Exception {
        thrown.expect(ParseException.class);
        parser.parseCommand("alias count");
    }

    @Test
    public void parseMetaCommand_addAlias_invalidArguments2() throws Exception {
        thrown.expect(ParseException.class);
        parser.parseCommand("alias count c c");
    }

    @Test
    public void parseMetaCommand_removeAlias_validArgumentsExistingAlias() throws Exception {
        thrown.expect(ParseException.class);

        parser.parseCommand("alias count c");
        assertTrue(parser.parseCommand("c") instanceof CountCommand);

        parser.parseCommand("alias-rm c");
        parser.parseCommand("c"); // Should throw ParseException
    }

    @Test
    public void parseMetaCommand_removeAlias_validArgumentsNonExistingAlias() throws Exception {
        thrown.expect(ParseException.class);

        parser.parseCommand("alias count c");
        parser.parseCommand("alias-rm foo"); // "foo" is not an alias
    }

    @Test
    public void parseMetaCommand_removeAlias_invalidArguments() throws Exception {
        thrown.expect(ParseException.class);
        parser.parseCommand("alias count c");
        parser.parseCommand("alias-rm"); // Alias not specified
    }

    @Test
    public void parseMetaCommand_clearAlias_validArguments() throws Exception {
        parser.parseCommand("alias-clear"); // Should not throw
    }

    @Test
    public void parseMetaCommand_clearAlias_invalidArguments() throws Exception {
        thrown.expect(ParseException.class);
        parser.parseCommand("alias-clear foo");
    }
}
