package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DeletedSources;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.SourceManager;
import seedu.address.model.source.*;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code SourceManager} with sample data.
 */
public class SampleDataUtil {
    public static Source[] getSampleSources() {
        return new Source[] {
            new Source(new Title("Algorithm Research"), new Author("Fredrick Poznan"), new Type("article"),
                    new Detail("A research about researchers on algorithms."), getTagSet("research")),
            new Source(new Title("Sensor Research"), new Author("Patrick Wyvern"), new Type("article"),
                    new Detail("A research about sensors."), getTagSet("sensors", "research")),
            new Source(new Title("Game Development 101"), new Author("Johann Stahl"), new Type("tutorial"),
                    new Detail("How to develop games for today's market."), getTagSet("game", "introduction")),
            new Source(new Title("Artificial Intelligence Research"), new Author("Smith James"), new Type("article"),
                    new Detail("A research about artificial intelligence."), getTagSet("research")),
            new Source(new Title("Virtual Reality Research"), new Author("Ben Stathem"), new Type("article"),
                    new Detail("A research about virtual reality."), getTagSet("research")),
            new Source(new Title("Augmented Reality Research"), new Author("John Snow"), new Type("article"),
                    new Detail("A research about augmented reality."), getTagSet("research"))
        };
    }

    public static ReadOnlySourceManager getSampleSourceManager() {
        SourceManager sampleSm = new SourceManager();
        for (Source sampleSource : getSampleSources()) {
            sampleSm.addSource(sampleSource);
        }
        return sampleSm;
    }

    public static Source[] getSampleDeletedSources() {
        return new Source[] { };
    }

    public static ReadOnlyDeletedSources getSampleDeletedSourcesList() {
        DeletedSources deletedSources = new DeletedSources();
        for (Source sampleDeletedSource : getSampleDeletedSources()) {
            deletedSources.addDeletedSource(sampleDeletedSource);
        }
        return deletedSources;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
