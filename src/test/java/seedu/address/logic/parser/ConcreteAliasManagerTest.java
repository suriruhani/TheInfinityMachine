package seedu.address.logic.parser;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConcreteAliasManagerTest {
    private class CommandValidatorStub implements CommandValidator {

        @Override
        public boolean isValidCommand(String command) {
            if (command.equals(EXISTING_COMMAND_1) || command.equals(EXISTING_COMMAND_2)) {
                return true;
            }

            return false;
        }

        @Override
        public boolean isUnaliasableCommand(String command) {
            if (command.equals(DISALLOWED_COMMAND_1) || command.equals(DISALLOWED_COMMAND_2)) {
                return true;
            }

            return false;
        }

    }

    private static final String ALIAS_1 = "a";
    private static final String ALIAS_2 = "b";
    private static final String EXISTING_COMMAND_1 = "foo";
    private static final String EXISTING_COMMAND_2 = "bar";
    private static final String DISALLOWED_COMMAND_1 = "disallowedone";
    private static final String DISALLOWED_COMMAND_2 = "disallowedtwo";
    private static final String NOVEL_COMMAND_1 = "novel";

    private CommandValidator commandValidator = new CommandValidatorStub();
    private AliasManager aliasManager;

    @Before
    public void setup() {
        // Creates fresh instance without persistence
        // Alias persistence messes up unit test cases
        aliasManager = new ConcreteAliasManager(commandValidator, null);
    }

    @Test(expected = IllegalArgumentException.class)
    // Create an alias using an invalid alias syntax
    public void create_existingCommand_invalidAlias1() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ";");
    }

    @Test(expected = IllegalArgumentException.class)
    // Create an alias using an invalid alias syntax
    public void create_existingCommand_invalidAlias2() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, "a:");
    }

    @Test(expected = IllegalArgumentException.class)
    // Create an alias using an invalid alias syntax
    public void create_existingCommand_invalidAlias3() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, "a:b");
    }

    @Test
    // Create an alias for an existing (valid) command
    public void create_existingCommand_unusedAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);

        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertTrue(aliasManager.getCommand(ALIAS_1).isPresent());
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_1);
    }

    @Test(expected = IllegalArgumentException.class)
    // Create an alias for a non-existing (invalid) command
    public void create_nonExistingCommand_unusedAlias() {
        aliasManager.registerAlias(NOVEL_COMMAND_1, ALIAS_1);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for an existing (valid) command using another command as alias
    public void create_existingCommand_aliasIsExistingCommand() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, EXISTING_COMMAND_2);
    }

    @Test
    // Attempt to create an alias for an existing (valid) command using an existing alias
    public void create_existingCommand_aliasIsExistingAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_1);

        aliasManager.registerAlias(EXISTING_COMMAND_2, ALIAS_1);
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertEquals(aliasManager.getCommand(ALIAS_1).get(), EXISTING_COMMAND_2);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for a disallowed command using a valid alias
    public void create_disallowedCommand_validAlias() {
        aliasManager.registerAlias(DISALLOWED_COMMAND_1, ALIAS_1);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for a disallowed command using another disallowed command
    public void create_disallowedCommand_disallowedCommandAsAlias() {
        aliasManager.registerAlias(DISALLOWED_COMMAND_1, DISALLOWED_COMMAND_2);
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to create an alias for another alias
    public void create_commandIsAlias_unusedAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        aliasManager.registerAlias(ALIAS_1, ALIAS_2);
    }

    @Test
    // Attempt to unregister a registered alias
    public void remove_existingAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);

        aliasManager.unregisterAlias(ALIAS_1);
        Assert.assertFalse(aliasManager.isAlias(ALIAS_1));
        Assert.assertFalse(aliasManager.getCommand(ALIAS_1).isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    // Attempt to unregister an unregistered alias
    public void remove_nonExistingAlias() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        aliasManager.unregisterAlias(ALIAS_2);
        Assert.assertTrue(aliasManager.isAlias(ALIAS_1));
        Assert.assertTrue(aliasManager.getCommand(ALIAS_1).isPresent());
    }

    @Test
    public void clear_emptyAliasManager() {
        aliasManager.clearAliases();
        Assert.assertEquals(aliasManager.getAliasList().size(), 0);
    }

    @Test
    public void clear_nonEmptyAliasManager() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);
        aliasManager.registerAlias(EXISTING_COMMAND_2, ALIAS_2);
        Assert.assertEquals(aliasManager.getAliasList().size(), 2);

        aliasManager.clearAliases();
        Assert.assertEquals(aliasManager.getAliasList().size(), 0);
    }

    @Test
    // Attempt to get alias list from an empty alias manager
    // Should not throw an exception; should not mutate alias manager
    public void list_emptyAliasManager() {
        HashMap<String, String> aliasList = aliasManager.getAliasList();
        Assert.assertFalse(aliasManager.getCommand(ALIAS_1).isPresent());
        Assert.assertNull(aliasList.get(ALIAS_1));

        aliasList.put(ALIAS_1, EXISTING_COMMAND_1);
        Assert.assertFalse(aliasManager.getCommand(ALIAS_1).isPresent());
    }

    @Test
    // Attempt to get alias list from a non-empty alias manager
    // Should not throw an exception; should not mutate alias manager
    public void list_nonEmptyAliasManager() {
        aliasManager.registerAlias(EXISTING_COMMAND_1, ALIAS_1);

        HashMap<String, String> aliasList = aliasManager.getAliasList();
        Assert.assertTrue(aliasManager.getCommand(ALIAS_1).isPresent());
        Assert.assertEquals(aliasList.get(ALIAS_1), EXISTING_COMMAND_1);

        aliasList.remove(ALIAS_1);
        Assert.assertTrue(aliasManager.getCommand(ALIAS_1).isPresent());
    }
}
