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
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

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
            .withBiblioFields().build();
    public static final Source GAME_DEVELOPMENT = new SourceBuilder()
            .withTitle("Game Development")
            .withType("Book")
            .withAuthor("Selena Macro")
            .withDetail("How to develop games for today's market.").build();
    public static final Source AI_RESEARCH = new SourceBuilder()
            .withTitle("Artificial Intelligence Research")
            .withType("Book")
            .withAuthor("Joana Micro")
            .withDetail("A research about artificial intelligence.")
            .withBiblioFields().build();
    public static final Source RESEARCH_METHOD = new SourceBuilder()
            .withTitle("The Correct Way to Research")
            .withType("Journal Article")
            .withAuthor("Lee Shin Hyung")
            .withDetail("The best way to conduct research.").build();

    // Manually added
    public static final Source STRUCTURE = new SourceBuilder()
            .withTitle("Structure Experiment")
            .withType("Journal Article")
            .withAuthor("Park Yi San")
            .withDetail("Experiments about structures.").build();
    public static final Source PROGRAMMING = new SourceBuilder()
            .withTitle("PROGRAMMING METHODOLOGY")
            .withType("Website")
            .withAuthor("Zhong Xing")
            .withDetail("All about programming methodology.").build();

    // Manually added - Source's details found in {@code CommandTestUtil}
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

    // Additional 13
    // Some references to history, games, and movies were made in the following sources for entertainment purposes only
    public static final Source SC_GUIDE = new seedu.address.model.util.SourceBuilder()
            .withTitle("From Bronze to Grandmaster")
            .withType("Book")
            .withAuthor("Mun Seong-won")
            .withDetail("A professional guide to getting better at Starcraft 2.")
            .withTags("game")
            .withBiblioFields()
            .build();
    public static final Source REPUBLIC = new seedu.address.model.util.SourceBuilder()
            .withTitle("Republic")
            .withType("Book")
            .withAuthor("Plato")
            .withDetail("Translation of the iconic text by the renown philosopher.")
            .withTags("philosophy", "history")
            .build();
    public static final Source DRAGONS = new seedu.address.model.util.SourceBuilder()
            .withTitle("Entering Dragons")
            .withType("Journal")
            .withAuthor("Alexander Eisenhower")
            .withDetail("A guide to the legendary creatures which once inhabited our planet.")
            .withTags("natural history")
            .build();
    public static final Source DIMENSIONS = new seedu.address.model.util.SourceBuilder()
            .withTitle("What Lurks in the Void")
            .withType("Journal Article")
            .withAuthor("Julius Coldair")
            .withDetail("A peek into the world beyond our own and the creatures that might dwell there.")
            .build();
    public static final Source DIMENSIONS_TWO = new seedu.address.model.util.SourceBuilder()
            .withTitle("Into the Void Dimensional Horrors")
            .withType("Journal Article")
            .withAuthor("Julius Coldair")
            .withDetail("A malevolent intelligence, ancient beyond our concept of time, that occasionally passes "
                    + "into our world. A brief summary of what we know about these monstrous beings.")
            .build();
    public static final Source DIMENSIONS_THREE = new seedu.address.model.util.SourceBuilder()
            .withTitle("Into the Void Spectral Wraiths")
            .withType("Journal Article")
            .withAuthor("Julius Coldair")
            .withDetail("A spectral entity shining with the ominous light of a pale star that has been seen to roam "
                    + "in the vast reaches of space. A brief summary of what we know about these strange creatures.")
            .build();
    public static final Source BRIDGE_ENGINEERING = new seedu.address.model.util.SourceBuilder()
            .withTitle("Introduction to Structural Engineering")
            .withType("Website")
            .withAuthor("Joseph Yeetus")
            .withDetail("A brief introduction into the engineering of bridges and how they have changed over history.")
            .withTags("engineering")
            .withBiblioFields()
            .build();
    public static final Source SPACE_DEFENCE = new seedu.address.model.util.SourceBuilder()
            .withTitle("The Earth Defence Initiative")
            .withType("Book")
            .withAuthor("David Levinson")
            .withDetail("Since the invasion in 1996 that brought humanity to its knees, the Earth has had vast "
                    + "improvements in its planetary defences. A brief overview of how our planet is defended.")
            .withTags("planetary defences", "space warfare")
            .build();
    public static final Source BRITISH_HISTORY = new seedu.address.model.util.SourceBuilder()
            .withTitle("The History of the British Empire")
            .withType("Book")
            .withAuthor("Mark Huey")
            .withDetail("A timeline of the events leading to the formation of the United Kingdom.")
            .build();
    public static final Source NAVAL_HISTORY = new seedu.address.model.util.SourceBuilder()
            .withTitle("Naval History Aircraft Carriers")
            .withType("Book")
            .withAuthor("Jason Nimitz")
            .withDetail("A overview of the massive capital ships that reshaped the world's navies.")
            .withTags("warships")
            .build();
    public static final Source NAVAL_HISTORY_TWO = new seedu.address.model.util.SourceBuilder()
            .withTitle("Naval History Battleships")
            .withType("Book")
            .withAuthor("Jason Nimitz")
            .withDetail("A overview of the big-gunned capital ships that were symbols of power in the world's navies.")
            .withTags("warships")
            .build();
    public static final Source NAVAL_HISTORY_THREE = new seedu.address.model.util.SourceBuilder()
            .withTitle("Naval History Cruiser")
            .withType("Book")
            .withAuthor("Jason Nimitz")
            .withDetail("A overview of the nimble escort vessels that formed the backbone of many navies.")
            .withTags("warships")
            .build();
    public static final Source COMPUTER_ORGANISATION = new seedu.address.model.util.SourceBuilder()
            .withTitle("Introductory Computer Organisation")
            .withType("Book")
            .withAuthor("Lee Ming Yuan")
            .withDetail("A look to the working principle behind the world's most powerful computational devices.")
            .withTags("computer science", "algorithms")
            .build();

    public static final String KEYWORD_MATCHING_EXPERIMENT = "Experiment"; // A keyword that matches EXPERIMENT

    public static final String KEYWORD_MATCHING_RESEARCH = "Research";
    public static final String TITLE_PREFIX_RESEARCH = " " + PREFIX_TITLE + KEYWORD_MATCHING_RESEARCH;
    public static final String TAG_PREFIX_RESEARCH = " " + PREFIX_TAG + KEYWORD_MATCHING_RESEARCH;

    public static final String KEYWORD_MATCHING_SMARTCOMP = "Smart Computers";
    public static final String TITLE_PREFIX_SMARTCOMP = " " + PREFIX_TITLE + KEYWORD_MATCHING_SMARTCOMP;

    public static final String KEYWORD_MATCHING_AI = "Artifical Intelligence";
    public static final String TITLE_PREFIX_AI = " " + PREFIX_TITLE + KEYWORD_MATCHING_AI;

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
                // Original 7
                ALGORITHM_RESEARCH,
                SENSOR_RESEARCH,
                SMART_COMPUTERS,
                VR_RESEARCH,
                AR_RESEARCH,
                GAME_DEVELOPMENT,
                AI_RESEARCH,
                // Additional 13
                SC_GUIDE,
                REPUBLIC,
                DRAGONS,
                DIMENSIONS,
                DIMENSIONS_TWO,
                DIMENSIONS_THREE,
                BRIDGE_ENGINEERING,
                SPACE_DEFENCE,
                BRITISH_HISTORY,
                NAVAL_HISTORY,
                NAVAL_HISTORY_TWO,
                NAVAL_HISTORY_THREE,
                COMPUTER_ORGANISATION));
    }

    public static List<Source> getTypicalDeletedSourcesList() {
        return new ArrayList<>(Arrays.asList(
                RESEARCH_METHOD));
    }

    public static Source getNonDefaultSource() {
        return ENGINEERING;
    }
}
