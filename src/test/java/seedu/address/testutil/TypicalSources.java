package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.SourceManager;
import seedu.address.model.source.Source;

/**
 * A utility class containing a list of {@code Source} objects to be used in tests.
 */
public class TypicalSources {

    public static final Source ALICE = new SourceBuilder().withTitle("Alice Pauline")
            .withType("alice_type")
            .withDetail("alice_detail")
            .withTags("friends").build();
    public static final Source BENSON = new SourceBuilder().withTitle("Benson Meier")
            .withType("benson_type")
            .withDetail("benson_detail")
            .withTags("owesMoney", "friends").build();
    public static final Source CARL = new SourceBuilder().withTitle("Carl Kurz")
            .withType("carl_type")
            .withDetail("carl_detail").build();
    public static final Source DANIEL = new SourceBuilder().withTitle("Daniel Meier")
            .withType("daniel_type")
            .withDetail("daniel_detail")
            .withTags("friends").build();
    public static final Source ELLE = new SourceBuilder().withTitle("Elle Meyer")
            .withType("elle_type")
            .withDetail("elle_detail").build();
    public static final Source FIONA = new SourceBuilder().withTitle("Fiona Kunz")
            .withType("fiona_type")
            .withDetail("fiona_detail").build();
    public static final Source GEORGE = new SourceBuilder().withTitle("George Best")
            .withType("george_type")
            .withDetail("george_detail").build();

    // Manually added
    public static final Source HOON = new SourceBuilder().withTitle("Hoon Meier")
            .withType("hoon_type")
            .withDetail("hoon_detail").build();
    public static final Source IDA = new SourceBuilder().withTitle("Ida Mueller")
            .withType("ida_type")
            .withDetail("ida_detail").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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

    public static List<Source> getTypicalSources() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
