package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSource;

import org.junit.Test;

import guitests.guihandles.SourceCardHandle;
import seedu.address.model.source.Source;
import seedu.address.testutil.SourceBuilder;

public class SourceCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Source sourceWithNoTags = new SourceBuilder().withTags(new String[0]).build();
        SourceCard sourceCard = new SourceCard(sourceWithNoTags, 1);
        uiPartRule.setUiPart(sourceCard);
        assertCardDisplay(sourceCard, sourceWithNoTags, 1);

        // with tags
        Source sourceWithTags = new SourceBuilder().build();
        sourceCard = new SourceCard(sourceWithTags, 2);
        uiPartRule.setUiPart(sourceCard);
        assertCardDisplay(sourceCard, sourceWithTags, 2);

        // pinned source no tags
        Source pinnedSourceNoTags = new SourceBuilder().withTags(new String[0]).build();
        pinnedSourceNoTags.setPinnedState(true);
        sourceCard = new SourceCard(pinnedSourceNoTags, 1);
        uiPartRule.setUiPart(sourceCard);
        assertCardDisplay(sourceCard, pinnedSourceNoTags, 1);

        // pinned source with tags
        Source pinnedSourceWithTags = new SourceBuilder().build();
        pinnedSourceWithTags.setPinnedState(true);
        sourceCard = new SourceCard(pinnedSourceWithTags, 1);
        uiPartRule.setUiPart(sourceCard);
        assertCardDisplay(sourceCard, pinnedSourceWithTags, 1);
    }

    @Test
    public void equals() {
        Source source = new SourceBuilder().build();
        SourceCard sourceCard = new SourceCard(source, 0);

        // same source, same index -> returns true
        SourceCard copy = new SourceCard(source, 0);
        assertTrue(sourceCard.equals(copy));

        // same object -> returns true
        assertTrue(sourceCard.equals(sourceCard));

        // null -> returns false
        assertFalse(sourceCard.equals(null));

        // different types -> returns false
        assertFalse(sourceCard.equals(0));

        // different source, same index -> returns false
        Source differentSource = new SourceBuilder().withTitle("differentName").build();
        assertFalse(sourceCard.equals(new SourceCard(differentSource, 0)));

        // same source, different index -> returns false
        assertFalse(sourceCard.equals(new SourceCard(source, 1)));
    }

    /**
     * Asserts that {@code sourceCard} displays the details of {@code expectedSource} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(SourceCard sourceCard, Source expectedSource, int expectedId) {
        guiRobot.pauseForHuman();

        SourceCardHandle sourceCardHandle = new SourceCardHandle(sourceCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", sourceCardHandle.getId());

        // verify source details are displayed correctly
        assertCardDisplaysSource(expectedSource, sourceCardHandle);
    }
}
