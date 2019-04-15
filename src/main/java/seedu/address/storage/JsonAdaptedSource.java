package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.source.Author;
import seedu.address.model.source.BiblioFields;
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
    private final String author;
    private final String detail;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String[] fieldBodies;

    /**
     * Constructs a {@code JsonAdaptedSource} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedSource(
            @JsonProperty("title") String title,
            @JsonProperty("type") String type,
            @JsonProperty("author") String author,
            @JsonProperty("detail") String detail,
            @JsonProperty("fieldBodies") String[] fieldBodies,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.title = title;
        this.type = type;
        this.author = author;
        this.detail = detail;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.fieldBodies = fieldBodies;
    }

    /**
     * Converts a given {@code Source} into this class for Jackson use.
     */
    public JsonAdaptedSource(Source source) {
        title = source.getTitle().title;
        type = source.getType().type;
        author = source.getAuthor().author;
        detail = source.getDetail().detail;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        fieldBodies = source.getBiblioFields().getFieldBodies();
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
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelTitle = new Title(title);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }
        if (!Author.isValidAuthor(author)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Author modelAuthor = new Author(author);

        if (detail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Detail.class.getSimpleName()));
        }
        if (!Detail.isValidDetail(detail)) {
            throw new IllegalValueException(Detail.MESSAGE_CONSTRAINTS);
        }
        final Detail modelDetail = new Detail(detail);

        final Set<Tag> modelTags = new HashSet<>(sourceTags);

        if (fieldBodies == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BiblioFields.class.getSimpleName()));
        }
        final BiblioFields modelBiblioFields = new BiblioFields();
        for (int i = 0; i < BiblioFields.ACCEPTED_FIELD_HEADERS.length; i++) {
            modelBiblioFields.replaceField(BiblioFields.ACCEPTED_FIELD_HEADERS[i], fieldBodies[i]);
        }
        return new Source(modelTitle, modelAuthor, modelType, modelDetail, modelTags, modelBiblioFields);
    }

}
