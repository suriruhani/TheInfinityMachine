package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SOURCE;
import static seedu.address.testutil.TypicalSources.getTypicalSources;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSource;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.SourceCardHandle;
import guitests.guihandles.SourceListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.source.Author;
import seedu.address.model.source.BiblioFields;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;

public class SourceListPanelTest extends GuiUnitTest {
    private static final ObservableList<Source> TYPICAL_SOURCES =
            FXCollections.observableList(getTypicalSources());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Source> selectedSource = new SimpleObjectProperty<>();
    private SourceListPanelHandle sourceListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_SOURCES);

        for (int i = 0; i < TYPICAL_SOURCES.size(); i++) {
            sourceListPanelHandle.navigateToCard(TYPICAL_SOURCES.get(i));
            Source expectedSource = TYPICAL_SOURCES.get(i);
            SourceCardHandle actualCard = sourceListPanelHandle.getSourceCardHandle(i);

            assertCardDisplaysSource(expectedSource, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedSourceChanged_selectionChanges() {
        initUi(TYPICAL_SOURCES);
        Source secondSource = TYPICAL_SOURCES.get(INDEX_SECOND_SOURCE.getZeroBased());
        guiRobot.interact(() -> selectedSource.set(secondSource));
        guiRobot.pauseForHuman();

        SourceCardHandle expectedSource = sourceListPanelHandle.getSourceCardHandle(INDEX_SECOND_SOURCE.getZeroBased());
        SourceCardHandle selectedSource = sourceListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedSource, selectedSource);
    }

    /**
     * Verifies that creating and deleting large number of sources in {@code SourceListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Source> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of source cards exceeded time limit");
    }

    /**
     * Returns a list of sources containing {@code sourceCount} sources that is used to populate the
     * {@code SourceListPanel}.
     */
    private ObservableList<Source> createBackingList(int sourceCount) {
        ObservableList<Source> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < sourceCount; i++) {
            Title title = new Title(i + "a");
            Type type = new Type("000");
            Author author = new Author("Author");
            Detail detail = new Detail("a@aa");
            Source source = new Source(title, author, type, detail, Collections.emptySet(), new BiblioFields());
            backingList.add(source);
        }
        return backingList;
    }

    /**
     * Initializes {@code sourceListPanelHandle} with a {@code SourceListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code SourceListPanel}.
     */
    private void initUi(ObservableList<Source> backingList) {
        SourceListPanel sourceListPanel =
                new SourceListPanel(backingList, selectedSource, selectedSource::set);
        uiPartRule.setUiPart(sourceListPanel);

        sourceListPanelHandle = new SourceListPanelHandle(getChildNode(sourceListPanel.getRoot(),
                SourceListPanelHandle.SOURCE_LIST_VIEW_ID));
    }
}
