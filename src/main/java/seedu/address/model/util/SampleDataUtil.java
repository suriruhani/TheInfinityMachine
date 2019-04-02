package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.DeletedSources;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.SourceManager;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code SourceManager} with sample data.
 */
public class SampleDataUtil {
    public static Source[] getSampleSources() {
        return new Source[] {
            new Source(new Title("FooTitle1"), new Type("FooType1"), new Detail("FooDetail1"),
                getTagSet("footag1")),
            new Source(new Title("FooTitle2"), new Type("FooType2"), new Detail("FooDetail2"),
                getTagSet("footag2", "footag1")),
            new Source(new Title("FooTitle3"), new Type("FooType3"), new Detail("FooDetail3"),
                getTagSet("footag3")),
            new Source(new Title("FooTitle4"), new Type("FooType4"), new Detail("FooDetail4"),
                getTagSet("footag4")),
            new Source(new Title("FooTitle5"), new Type("FooType5"), new Detail("FooDetail5"),
                getTagSet("footag5")),
            new Source(new Title("FooTitle6"), new Type("FooType6"), new Detail("FooDetail6"),
                getTagSet("footag2"))
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
        return new Source[] {
            new Source(new Title("Algorithms 0"), new Type("Math"), new Detail("AlgoDetails12"),
                    getTagSet("ComputerScience12")),
            new Source(new Title("Algorithms 1"), new Type("Math"), new Detail("AlgoDetails12"),
                        getTagSet("ComputerScience12"))
        };
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
