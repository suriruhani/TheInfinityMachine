package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.SourceManager;
import seedu.address.model.source.Source;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code SourceManager} with sample data.
 */
public class SampleDataUtil {
    public static Source[] getSampleSources() {
        List<Source> sampleSources = TypicalSources.getTypicalSources();
        return sampleSources.toArray(new Source[0]);
    }

    public static ReadOnlySourceManager getSampleSourceManager() {
        SourceManager sampleSm = new SourceManager();
        for (Source sampleSource : getSampleSources()) {
            sampleSm.addSource(sampleSource);
        }
        return sampleSm;
    }

    public static ReadOnlyDeletedSources getSampleDeletedSourcesList() {
        return TypicalSources.getTypicalDeletedSources();
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
