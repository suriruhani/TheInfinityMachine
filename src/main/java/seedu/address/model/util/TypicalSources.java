package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.DeletedSources;
import seedu.address.model.source.Source;

/**
 * A utility class containing a list of {@code Source} objects to be used in tests.
 */
public class TypicalSources {

    // Original 7
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

    // Original populating typical source list
    public static final Source RESEARCH_METHOD = new SourceBuilder()
            .withTitle("The Correct Way to Research")
            .withType("Journal Article")
            .withAuthor("Lee Shin Hyung")
            .withDetail("The best way to conduct research.").build();

    // Additional 13
    // Some references to history, games, and movies were made in the following sources for entertainment purposes only
    public static final Source SC_GUIDE = new SourceBuilder()
            .withTitle("From Bronze to Grandmaster")
            .withType("Book")
            .withAuthor("Mun Seong-won")
            .withDetail("A professional guide to getting better at Starcraft 2.")
            .withTags("game")
            .withBiblioFields()
            .build();
    public static final Source REPUBLIC = new SourceBuilder()
            .withTitle("Republic")
            .withType("Book")
            .withAuthor("Plato")
            .withDetail("Translation of the iconic text by the renown philosopher.")
            .withTags("philosophy", "history")
            .build();
    public static final Source DRAGONS = new SourceBuilder()
            .withTitle("Entering Dragons")
            .withType("Journal")
            .withAuthor("Alexander Eisenhower")
            .withDetail("A guide to the legendary creatures which once inhabited our planet.")
            .withTags("natural history")
            .build();
    public static final Source DIMENSIONS = new SourceBuilder()
            .withTitle("What Lurks in the Void")
            .withType("Journal Article")
            .withAuthor("Julius Coldair")
            .withDetail("A peek into the world beyond our own and the creatures that might dwell there.")
            .build();
    public static final Source DIMENSIONS_TWO = new SourceBuilder()
            .withTitle("Into the Void Dimensional Horrors")
            .withType("Journal Article")
            .withAuthor("Julius Coldair")
            .withDetail("A malevolent intelligence, ancient beyond our concept of time, that occasionally passes "
                    + "into our world. A brief summary of what we know about these monstrous beings.")
            .build();
    public static final Source DIMENSIONS_THREE = new SourceBuilder()
            .withTitle("Into the Void Spectral Wraiths")
            .withType("Journal Article")
            .withAuthor("Julius Coldair")
            .withDetail("A spectral entity shining with the ominous light of a pale star that has been seen to roam "
                    + "in the vast reaches of space. A brief summary of what we know about these strange creatures.")
            .build();
    public static final Source BRIDGE_ENGINEERING = new SourceBuilder()
            .withTitle("Introduction to Structural Engineering")
            .withType("Website")
            .withAuthor("Joseph Yeetus")
            .withDetail("A brief introduction into the engineering of bridges and how they have changed over history.")
            .withTags("engineering")
            .withBiblioFields()
            .build();
    public static final Source SPACE_DEFENCE = new SourceBuilder()
            .withTitle("The Earth Defence Initiative")
            .withType("Book")
            .withAuthor("David Levinson")
            .withDetail("Since the invasion in 1996 that brought humanity to its knees, the Earth has had vast "
                    + "improvements in its planetary defences. A brief overview of how our planet is defended.")
            .withTags("planetary defences", "space warfare")
            .build();
    public static final Source BRITISH_HISTORY = new SourceBuilder()
            .withTitle("The History of the British Empire")
            .withType("Book")
            .withAuthor("Mark Huey")
            .withDetail("A timeline of the events leading to the formation of the United Kingdom.")
            .build();
    public static final Source NAVAL_HISTORY = new SourceBuilder()
            .withTitle("Naval History Aircraft Carriers")
            .withType("Book")
            .withAuthor("Jason Nimitz")
            .withDetail("A overview of the massive capital ships that reshaped the world's navies.")
            .withTags("warships")
            .build();
    public static final Source NAVAL_HISTORY_TWO = new SourceBuilder()
            .withTitle("Naval History Battleships")
            .withType("Book")
            .withAuthor("Jason Nimitz")
            .withDetail("A overview of the big-gunned capital ships that were symbols of power in the world's navies.")
            .withTags("warships")
            .build();
    public static final Source NAVAL_HISTORY_THREE = new SourceBuilder()
            .withTitle("Naval History Cruiser")
            .withType("Book")
            .withAuthor("Jason Nimitz")
            .withDetail("A overview of the nimble escort vessels that formed the backbone of many navies.")
            .withTags("warships")
            .build();
    public static final Source COMPUTER_ORGANISATION = new SourceBuilder()
            .withTitle("Introductory Computer Organisation")
            .withType("Book")
            .withAuthor("Lee Ming Yuan")
            .withDetail("A look to the working principle behind the world's most powerful computational devices.")
            .withTags("computer science", "algorithms")
            .build();

    private TypicalSources() {} // prevents instantiation

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

    /**
     * Returns a list of all typical sources (limited to 7).
     */
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
        return new ArrayList<>(Arrays.asList(RESEARCH_METHOD));
    }

    /**
     * Returns a list of complete list of typical sources (all 20 sources included).
     */
    public static List<Source> getCompleteSourceList() {
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
}
