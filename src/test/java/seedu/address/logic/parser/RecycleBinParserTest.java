package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.CountCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EmptyBinCommand;
import seedu.address.logic.commands.ExitBinCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.RecycleBinCommand;
import seedu.address.logic.commands.RestoreCommand;
import seedu.address.logic.commands.SelectCommand;

public class RecycleBinParserTest extends SourceManagerParser {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private RecycleBinParser parser;

    @Before
    public void setup() {
        parser = new RecycleBinParser(); // Reset between tests
    }

    @Test
    public void parseCommand_recycleBin() throws Exception {
        assertTrue(parser.parseCommand(RecycleBinCommand.COMMAND_WORD) instanceof RecycleBinCommand);
    }

    @Test
    public void parseCommand_restore() throws Exception {
        RestoreCommand command = (RestoreCommand) parser.parseCommand(
                RestoreCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased());
        assertEquals(new RestoreCommand(INDEX_FIRST_SOURCE), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_SOURCE), command);
    }

    @Test
    public void parseCommand_emptyBin() throws Exception {
        assertTrue(parser.parseCommand(EmptyBinCommand.COMMAND_WORD) instanceof EmptyBinCommand);
    }

    @Test
    public void parseCommand_exitBin() throws Exception {
        assertTrue(parser.parseCommand(ExitBinCommand.COMMAND_WORD) instanceof ExitBinCommand);
    }

    @Test
    public void parseCommand_count() throws Exception {
        assertTrue(parser.parseCommand(CountCommand.COMMAND_WORD) instanceof CountCommand);
        assertTrue(parser.parseCommand(CountCommand.COMMAND_WORD + " 3") instanceof CountCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_SOURCE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_SOURCE), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

}
