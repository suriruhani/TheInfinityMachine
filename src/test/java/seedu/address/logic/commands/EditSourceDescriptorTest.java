package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_NETWORK;

import org.junit.Test;

import seedu.address.testutil.EditSourceDescriptorBuilder;

public class EditSourceDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditSourceDescriptor descriptorWithSameValues =
                new EditCommand.EditSourceDescriptor(DESC_ENGINEERING);
        assertTrue(DESC_ENGINEERING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_ENGINEERING.equals(DESC_ENGINEERING));

        // null -> returns false
        assertFalse(DESC_ENGINEERING.equals(null));

        // different types -> returns false
        assertFalse(DESC_ENGINEERING.equals(5));

        // different values -> returns false
        assertFalse(DESC_ENGINEERING.equals(DESC_NETWORK));

        // different title -> returns false
        EditCommand.EditSourceDescriptor editedEngineering =
                new EditSourceDescriptorBuilder(DESC_ENGINEERING).withTitle(VALID_TITLE_NETWORK).build();
        assertFalse(DESC_ENGINEERING.equals(editedEngineering));

        // different type -> returns false
        editedEngineering = new EditSourceDescriptorBuilder(DESC_ENGINEERING).withType(VALID_TYPE_NETWORK).build();
        assertFalse(DESC_ENGINEERING.equals(editedEngineering));

        // different detail -> returns false
        editedEngineering = new EditSourceDescriptorBuilder(DESC_ENGINEERING).withDetail(VALID_DETAIL_NETWORK).build();
        assertFalse(DESC_ENGINEERING.equals(editedEngineering));

        // different tags -> returns false
        editedEngineering = new EditSourceDescriptorBuilder(DESC_ENGINEERING).withTags(VALID_TAG_BAR).build();
        assertFalse(DESC_ENGINEERING.equals(editedEngineering));
    }
}
