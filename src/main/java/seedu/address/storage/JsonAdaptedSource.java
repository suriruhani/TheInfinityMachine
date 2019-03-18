package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Source}.
 */
class JsonAdaptedSource {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Source's %s field is missing!";

    private final String title;
    private final String type;
    private final String source;
    private final String detail;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedSource} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedSource(@JsonProperty("title") String title, @JsonProperty("type") String type,
            @JsonProperty("source") String source, @JsonProperty("detail") String detail,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = title;
        this.type = type;
        this.source = source;
        this.detail = detail;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Source} into this class for Jackson use.
     */
    public JsonAdaptedSource(Source source) {
        title = source.getTitle().name;
        type = source.getType().value;
        source = source.getSource().value;
        detail = source.getDetail().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted source object into the model's {@code Source} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted source.
     */
    public Source toModelType() throws IllegalValueException {
        final List<Tag> sourceTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            sourceTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        if (source == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Source.class.getSimpleName()));
        }
        if (!Source.isValidSource(source)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Source modelSource = new Source(source);

        if (detail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Detail.class.getSimpleName()));
        }
        if (!Detail.isValidDetail(detail)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Detail modelDetail = new Detail(detail);

        final Set<Tag> modelTags = new HashSet<>(sourceTags);
        return new Source(modelTitle, modelType, modelSource, modelDetail, modelTags);
    }

}
