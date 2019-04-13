package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.source.Source;
import seedu.address.model.tag.Tag;

/**
 * The Browser Panel of the App.
 */
public class SourcePanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));

    private static final String FXML = "SourcePanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView source;

    public SourcePanel(ObservableValue<Source> selectedSource) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        // Load source page when selected source changes.
        selectedSource.addListener((observable, oldValue, newValue) -> {
            logger.info("SourcePanel triggered on source selection.");
            if (newValue == null) {
                loadDefaultPage();
                return;
            }
            Source sourceDetail = selectedSource.getValue();
            loadSourcePage(generateDetail(sourceDetail));
        });

        loadDefaultPage();
    }

    public void loadSourcePage(String detail) {
        Platform.runLater(() -> source.getEngine().loadContent(detail));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> source.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

    /**
     * Generates the html for Source details.
     */
    public String generateDetail(Source source) {
        String title = source.getTitle().toString();
        String author = source.getAuthor().toString();
        String type = source.getType().toString();
        String detail = source.getDetail().toString();
        String tags = source.getTags().stream().map(Tag::toString).collect(
                Collectors.joining(", "));
        ObservableList<String> biblioFields =
                FXCollections.observableArrayList(source.getBiblioFields().toString().split("\n"));
        biblioFields.remove(0);

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html><html><head></head>");
        htmlBuilder.append("<h1 class=\"source-title\">" + title + "</h1>");
        htmlBuilder.append("<body class=\"source-title\"></br>");
        htmlBuilder.append("Source Author: " + author + "</br>");
        htmlBuilder.append("Source Type: " + type + "</br>");
        htmlBuilder.append("Source Tags: " + tags + "</br></br>");
        htmlBuilder.append("<b>Biblio Fields</b></br>");
        htmlBuilder.append("--------------------------------------</br>");
        for (String biblio: biblioFields) {
            if (biblio.startsWith("[")) {
                htmlBuilder.append(biblio.substring(1, biblio.length() - 1) + "</br>");
            } else {
                htmlBuilder.append(biblio + "</br>");
            }
        }
        htmlBuilder.append("</br><br>" + detail + "</br>");
        htmlBuilder.append("</body></html>");

        return htmlBuilder.toString();
    }
}
