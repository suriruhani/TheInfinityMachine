package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DeletedSources;
import seedu.address.model.SourceManager;
import seedu.address.model.source.Source;

/**
 * A utility class containing a list of {@code Source} objects to be used in tests.
 */
public class TypicalSources {

    public static final Source ALGORITHM_RESEARCH = new SourceBuilder()
            .withTitle("Algorithm Researchers")
            .withType("Article")
            .withAuthor("Fredrick Poznan")
            .withDetail("A research about researchers on algorithms.")
            .withTags("research").build();
    public static final Source SENSOR_RESEARCH = new SourceBuilder()
            .withTitle("Sensor Research")
            .withType("Website")
            .withAuthor("John Markson")
            .withDetail("A research about sensors.")
            .withTags("sensor", "research").build();
    public static final Source SMART_COMPUTERS = new SourceBuilder()
            .withTitle("Smart Computers 101")
            .withType("Website")
            .withAuthor("David Kim")
            .withDetail("How to build smart computers")
            .withTags("tutorial")
            .withBiblioFields().build();
    public static final Source VR_RESEARCH = new SourceBuilder()
            .withTitle("Virtual Reality Research")
            .withType("Journal Article")
            .withAuthor("Johann Johnson")
            .withDetail("A research about virtual reality.")
            .withTags("research").build();
    public static final Source AR_RESEARCH = new SourceBuilder()
            .withTitle("Augmented Reality Research")
            .withType("Journal Article")
            .withAuthor("Joseph Jackson")
            .withDetail("A research about augmented reality.")
            .withTags("research")
            .withBiblioFields().build();
    public static final Source GAME_DEVELOPMENT = new SourceBuilder()
            .withTitle("Game Development")
            .withType("Book")
            .withAuthor("Selena Macro")
            .withTags("tutorial")
            .withDetail("How to develop games for today's market.").build();
    public static final Source AI_RESEARCH = new SourceBuilder()
            .withTitle("Artificial Intelligence Research")
            .withType("Book")
            .withAuthor("Joana Micro")
            .withDetail("A research about artificial intelligence.")
            .withTags("research")
            .withBiblioFields().build();
    public static final Source RESEARCH_METHOD = new SourceBuilder()
            .withTitle("The Correct Way to Research")
            .withType("Journal Article")
            .withAuthor("Lee Shin Hyung")
            .withTags("tutorial")
            .withDetail("The best way to conduct research.").build();

    private TypicalSources() {} // prevents instantiation

    /**
     * Returns a {@code SourceManager} with all the typical sources.
     */
    public static SourceManager getTypicalSourceManager() {
        SourceManager sm = new SourceManager();
        for (Source source : getTypicalSources()) {
            sm.addSource(source);
        }
        return sm;
    }

    /**
     * Returns a {@code DeletedSources} with all the typical deleted sources.
     */
    public static DeletedSources getTypicalDeletedSources() {
        DeletedSources ds = new DeletedSources();
        for (Source source : getTypicalDeletedSourcesList()) {
            ds.addDeletedSource(source);
        }
        return ds;
    }

    public static List<Source> getTypicalSources() {
        return new ArrayList<>(Arrays.asList(
                ALGORITHM_RESEARCH,
                SENSOR_RESEARCH,
                SMART_COMPUTERS,
                VR_RESEARCH,
                AR_RESEARCH,
                GAME_DEVELOPMENT,
                AI_RESEARCH));
    }

    public static List<Source> getTypicalDeletedSourcesList() {
        return new ArrayList<>(Arrays.asList(RESEARCH_METHOD));
    }
}
