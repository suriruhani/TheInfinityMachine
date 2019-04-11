package seedu.address.ui;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalSources.ALGORITHM_RESEARCH;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SourcePanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.source.Source;
import seedu.address.testutil.SourceBuilder;

public class SourcePanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Source> selectedSource = new SimpleObjectProperty<>();
    private SourcePanel sourcePanel;
    private SourcePanelHandle sourcePanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> sourcePanel = new SourcePanel(selectedSource));
        uiPartRule.setUiPart(sourcePanel);

        sourcePanelHandle = new SourcePanelHandle(sourcePanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(SourcePanel.DEFAULT_PAGE, sourcePanelHandle.getLoadedUrl());

        // associated web page of a source
        guiRobot.interact(() -> selectedSource.set(ALGORITHM_RESEARCH));
        Source sampleSource = new SourceBuilder().build();
        String html = sourcePanel.generateDetail(sampleSource);
        sourcePanel.loadSourcePage(html);
        do {
            Thread.sleep(500);
        } while(!sourcePanelHandle.isLoaded());
        String loadedContent = sourcePanelHandle.getLoadedSource();
        assertTrue(!loadedContent.isEmpty());
    }
}
