package seedu.address.logic.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.jupiter.api.TestTemplate;

public class AliasManagerTest {
    private class CommandValidatorStub implements CommandValidator {
        @Override
        public boolean isValidCommand(String command) {
            if (command.equals(EXISTING_COMMAND_1) || command.equals(EXISTING_COMMAND_2)) {
                return true;
            }

            return false;
        }
    }

    private final String ALIAS_1 = "a";
    private final String ALIAS_2 = "b";
    private final String EXISTING_COMMAND_1 = "foo";
    private final String EXISTING_COMMAND_2 = "bar";
    private final String NOVEL_COMMAND_1 = "novel";

    private CommandValidator commandValidator = new CommandValidatorStub();
    private AliasManager aliasManager = new AliasManager(commandValidator);

    @Before
    public void setup() {
        aliasManager = new AliasManager(commandValidator); // Creates fresh instance
    }

    @Test
    // Create an alias for an existing (valid) command
    public void create_existingCommand_unusedAlias() {
        Assert.assertFalse(aliasManager.isAlias(ALIAS_1));
        Assert.assertFalse(aliasManager.getCommand(ALIAS_1).isPresent());

        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);

        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertTrue(aliasManager.getCommand(ALIAS_1).isPresent());
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_1);
    }

    @Test
    // Create an alias for a non-existing (invalid) command
    public void create_nonExistingCommand_unusedAlias() {
        Assert.assertFalse(aliasManager.isAlias(ALIAS_1));
        Assert.assertFalse(aliasManager.getCommand(ALIAS_1).isPresent());

        aliasManager.registerAlias(NOVEL_COMMAND_1, ALIAS_1);

        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertTrue(aliasManager.getCommand(ALIAS_1).isPresent());
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), NOVEL_COMMAND_1);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for an existing (valid) command using another command as alias
    public void create_existingCommand_aliasIsExistingCommand() {
        Assert.assertFalse(aliasManager.isAlias(EXISTING_COMMAND_2));
        Assert.assertFalse(aliasManager.getCommand(EXISTING_COMMAND_2).isPresent());

        aliasManager.registerAlias(EXISTING_COMMAND_1, EXISTING_COMMAND_2);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for the alias (add) meta-command
    public void create_MetaCommandAdd_unusedAlias() {
        aliasManager.registerAlias(AliasManager.COMMAND_WORD_ADD, ALIAS_1);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for the alias-rm meta-command
    public void create_MetaCommandRemove_unusedAlias() {
        aliasManager.registerAlias(AliasManager.COMMAND_WORD_REMOVE, ALIAS_1);
    }

    @Test
    // Attempt to create an alias for an existing (valid) command using an existing alias
    public void create_existingCommand_aliasIsExistingAlias() {
        Assert.assertFalse(aliasManager.isAlias(ALIAS_1));

        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_1);

        aliasManager.registerAlias(EXISTING_COMMAND_2, ALIAS_1);
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_2);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for another alias
    public void create_CommandIsAlias_unusedAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        aliasManager.registerAlias(ALIAS_1, ALIAS_2);
    }

    @Test
    public void remove_existingAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_1);

        aliasManager.unregisterAlias(ALIAS_1);
        Assert.assertFalse(aliasManager.isAlias(ALIAS_1));
        Assert.assertFalse(aliasManager.getCommand(ALIAS_1).isPresent());
    }

    @Test
    public void remove_nonExistingAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_1);

        aliasManager.unregisterAlias(ALIAS_2); // Should not throw
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertTrue(aliasManager.getCommand(ALIAS_1).isPresent());
        Assert.assertFalse(aliasManager.isAlias(ALIAS_2));
        Assert.assertFalse(aliasManager.getCommand(ALIAS_2).isPresent());
    }
}
