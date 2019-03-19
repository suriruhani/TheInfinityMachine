package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.SourceCardHandle;
import guitests.guihandles.SourceListPanelHandle;
import seedu.address.model.source.Source;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(SourceCardHandle expectedCard, SourceCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getDetail(), actualCard.getDetail());
        assertEquals(expectedCard.getType(), actualCard.getType());
        assertEquals(expectedCard.getTitle(), actualCard.getTitle());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedSource}.
     */
    public static void assertCardDisplaysSource(Source expectedSource, SourceCardHandle actualCard) {
        assertEquals(expectedSource.getTitle().title, actualCard.getTitle());
        assertEquals(expectedSource.getType().type, actualCard.getType());
        assertEquals(expectedSource.getDetail().detail, actualCard.getDetail());
        assertEquals(expectedSource.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code sourceListPanelHandle} displays the details of {@code sources} correctly and
     * in the correct order.
     */
    public static void assertListMatching(SourceListPanelHandle sourceListPanelHandle, Source... sources) {
        for (int i = 0; i < sources.length; i++) {
            sourceListPanelHandle.navigateToCard(i);
            assertCardDisplaysSource(sources[i], sourceListPanelHandle.getSourceCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code sourceListPanelHandle} displays the details of {@code sources} correctly and
     * in the correct order.
     */
    public static void assertListMatching(SourceListPanelHandle sourceListPanelHandle, List<Source> sources) {
        assertListMatching(sourceListPanelHandle, sources.toArray(new Source[0]));
    }

    /**
     * Asserts the size of the list in {@code sourceListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(SourceListPanelHandle sourceListPanelHandle, int size) {
        int numberOfSources = sourceListPanelHandle.getListSize();
        assertEquals(size, numberOfSources);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
