package seedu.address.testutil;

import seedu.address.model.SourceManager;
import seedu.address.model.source.Source;

/**
 * A utility class to help with building SourceManager objects.
 * Example usage: <br>
 *     {@code SourceManager ab = new SourceManagerBuilder().withSource("Foo", "Bar").build();}
 */
public class SourceManagerBuilder {

    private SourceManager sourceManager;

    public SourceManagerBuilder() {
        sourceManager = new SourceManager();
    }

    public SourceManagerBuilder(SourceManager sourceManager) {
        this.sourceManager = sourceManager;
    }

    /**
     * Adds a new {@code Source} to the {@code SourceManager} that we are building.
     */
    public SourceManagerBuilder withSource(Source source) {
        sourceManager.addSource(source);
        return this;
    }

    public SourceManager build() {
        return sourceManager;
    }
}
