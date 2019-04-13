package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedSource.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalSources.SENSOR_RESEARCH;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.testutil.Assert;

public class JsonAdaptedSourceTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_TYPE = "+651234";
    private static final String INVALID_DETAIL = " example"; // Leading white space is illegal
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TITLE = SENSOR_RESEARCH.getTitle().toString();
    private static final String VALID_TYPE = SENSOR_RESEARCH.getType().toString();
    private static final String VALID_AUTHOR = SENSOR_RESEARCH.getAuthor().toString();
    private static final String VALID_DETAIL = SENSOR_RESEARCH.getDetail().toString();
    private static final String[] VALID_FIELDBODIES = SENSOR_RESEARCH.getBiblioFields().getFieldBodies();
    private static final List<JsonAdaptedTag> VALID_TAGS = SENSOR_RESEARCH.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validSourceDetails_returnsSource() throws Exception {
        JsonAdaptedSource source = new JsonAdaptedSource(SENSOR_RESEARCH);
        assertEquals(SENSOR_RESEARCH, source.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedSource source =
                new JsonAdaptedSource(INVALID_TITLE, VALID_TYPE, VALID_AUTHOR, VALID_DETAIL,
                        VALID_FIELDBODIES, VALID_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, source::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedSource source = new JsonAdaptedSource(null, VALID_TYPE, VALID_AUTHOR, VALID_DETAIL,
                VALID_FIELDBODIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, source::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedSource source =
                new JsonAdaptedSource(VALID_TITLE, INVALID_TYPE, VALID_AUTHOR, VALID_DETAIL,
                        VALID_FIELDBODIES, VALID_TAGS);
        String expectedMessage = Type.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, source::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedSource source = new JsonAdaptedSource(VALID_TITLE, null, VALID_AUTHOR, VALID_DETAIL,
                VALID_FIELDBODIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, source::toModelType);
    }

    @Test
    public void toModelType_invalidDetail_throwsIllegalValueException() {
        JsonAdaptedSource source =
                new JsonAdaptedSource(VALID_TITLE, VALID_TYPE, VALID_AUTHOR, INVALID_DETAIL,
                        VALID_FIELDBODIES, VALID_TAGS);
        String expectedMessage = Detail.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, source::toModelType);
    }

    @Test
    public void toModelType_nullDetail_throwsIllegalValueException() {
        JsonAdaptedSource source = new JsonAdaptedSource(VALID_TITLE, VALID_TYPE, VALID_AUTHOR, null,
                VALID_FIELDBODIES, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Detail.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, source::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedSource source =
                new JsonAdaptedSource(VALID_TITLE, VALID_TYPE, VALID_AUTHOR, VALID_DETAIL,
                        VALID_FIELDBODIES, invalidTags);
        Assert.assertThrows(IllegalValueException.class, source::toModelType);
    }
}
