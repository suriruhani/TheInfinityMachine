package seedu.address.model.util;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.model.source.Source;
import seedu.address.testutil.TypicalSources;


/**
 * Contains unit test for SampleDataUtil.
 */

public class SampleDataUtilTest {

    @Test
    public void sampleDataEqualsTestData_success() {
        Source[] sampleData = SampleDataUtil.getSampleSources();
        Source[] testData = TypicalSources.getTypicalSources().toArray(new Source[0]);
        for (Source s : testData) {
            System.out.println(s);
        }
        assert (Arrays.equals(sampleData, testData));
    }
}
