package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class SourceCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        SourceCard sourceCard = new SourceCard(personWithNoTags, 1);
        uiPartRule.setUiPart(sourceCard);
        assertCardDisplay(sourceCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        sourceCard = new SourceCard(personWithTags, 2);
        uiPartRule.setUiPart(sourceCard);
        assertCardDisplay(sourceCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        SourceCard sourceCard = new SourceCard(person, 0);

        // same person, same index -> returns true
        SourceCard copy = new SourceCard(person, 0);
        assertTrue(sourceCard.equals(copy));

        // same object -> returns true
        assertTrue(sourceCard.equals(sourceCard));

        // null -> returns false
        assertFalse(sourceCard.equals(null));

        // different types -> returns false
        assertFalse(sourceCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(sourceCard.equals(new SourceCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(sourceCard.equals(new SourceCard(person, 1)));
    }

    /**
     * Asserts that {@code sourceCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(SourceCard sourceCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(sourceCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
