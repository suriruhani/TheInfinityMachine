package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalSources.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.source.Source;

public class SourcePanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Source> selectedSource = new SimpleObjectProperty<>();
    private SourcePanel sourcePanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> sourcePanel = new SourcePanel(selectedSource));
        uiPartRule.setUiPart(sourcePanel);

        browserPanelHandle = new BrowserPanelHandle(sourcePanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(SourcePanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a source
        guiRobot.interact(() -> selectedSource.set(ALICE));
        URL expectedPersonUrl = new URL(SourcePanel.SEARCH_PAGE_URL + ALICE.getTitle().title.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
