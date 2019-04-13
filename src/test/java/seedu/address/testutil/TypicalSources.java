package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_NETWORK;
import static seedu.address.logic.parser.CliSyntax.*;

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
            .withType("article")
            .withAuthor("Fredrick Poznan")
            .withDetail("A research about researchers on algorithms.")
            .withTags("research").build();
    public static final Source SENSOR_RESEARCH = new SourceBuilder()
            .withTitle("Sensor Research")
            .withType("article")
            .withAuthor("John Markson")
            .withDetail("A research about sensors.")
            .withTags("sensor", "research").build();
    public static final Source SMART_COMPUTERS = new SourceBuilder()
            .withTitle("Smart Computers 101")
            .withType("computer tutorial")
            .withAuthor("David Kim")
            .withDetail("How to build smart computers").build();
    public static final Source VR_RESEARCH = new SourceBuilder()
            .withTitle("Virtual Reality Research")
            .withType("article")
            .withAuthor("Johann Johnson")
            .withDetail("A research about virtual reality.")
            .withTags("research").build();
    public static final Source AR_RESEARCH = new SourceBuilder()
            .withTitle("Augmented Reality Research")
            .withType("article")
            .withAuthor("Joseph Jackson")
            .withDetail("A research about augmented reality.").build();
    public static final Source GAME_DEVELOPMENT = new SourceBuilder()
            .withTitle("Game Development")
            .withType("tutorial")
            .withAuthor("Selena Macro")
            .withDetail("How to develop games for today's market.").build();
    public static final Source AI_RESEARCH = new SourceBuilder()
            .withTitle("Artificial Intelligence Research")
            .withType("article")
            .withAuthor("Joana Micro")
            .withDetail("A research about artificial intelligence.").build();
    public static final Source RESEARCH_METHOD = new SourceBuilder()
            .withTitle("The Correct Way to Research")
            .withType("tutorial")
            .withAuthor("Lee Shin Hyung")
            .withDetail("The best way to conduct research.").build();

    // Manually added
    public static final Source STRUCTURE = new SourceBuilder()
            .withTitle("Structure Experiment")
            .withType("experiment")
            .withAuthor("Park Yi San")
            .withDetail("Experiments about structures.").build();
    public static final Source PROGRAMMING = new SourceBuilder()
            .withTitle("PROGRAMMING METHODOLOGY")
            .withType("methodology")
            .withAuthor("Zhong Xing")
            .withDetail("All about programming methodology.").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Source ENGINEERING = new SourceBuilder()
            .withTitle(VALID_TITLE_ENGINEERING)
            .withType(VALID_TYPE_ENGINEERING)
            .withAuthor(VALID_AUTHOR_ENGINEERING)
            .withDetail(VALID_DETAIL_ENGINEERING)
            .withTags(VALID_TAG_FOO).build();
    public static final Source NETWORK = new SourceBuilder()
            .withTitle(VALID_TITLE_NETWORK)
            .withType(VALID_TYPE_NETWORK)
            .withAuthor(VALID_AUTHOR_NETWORK)
            .withDetail(VALID_DETAIL_NETWORK)
            .withTags(VALID_TAG_BAR, VALID_TAG_FOO).build();

    public static final String KEYWORD_MATCHING_EXPERIMENT = "Experiment"; // A keyword that matches EXPERIMENT

    public static final String KEYWORD_MATCHING_RESEARCH = "Research";
    public static final String TITLE_PREFIX_RESEARCH = " " + PREFIX_TITLE + KEYWORD_MATCHING_RESEARCH;
    public static final String TYPE_PREFIX_RESEARCH = " " + PREFIX_TYPE + KEYWORD_MATCHING_RESEARCH;;
    public static final String DETAIL_PREFIX_RESEARCH = " " + PREFIX_DETAILS + KEYWORD_MATCHING_RESEARCH;;
    public static final String TAG_PREFIX_RESEARCH = " " + PREFIX_TAG + KEYWORD_MATCHING_RESEARCH;

    public static final String KEYWORD_MATCHING_SMARTCOMP = "Smart Computers";
    public static final String TITLE_PREFIX_SMARTCOMP =" " + PREFIX_TITLE + KEYWORD_MATCHING_SMARTCOMP;

    public static final String KEYWORD_MATCHING_AI = "Artifical Intelligence";
    public static final String TITLE_PREFIX_AI =" " + PREFIX_TITLE + KEYWORD_MATCHING_AI;

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
