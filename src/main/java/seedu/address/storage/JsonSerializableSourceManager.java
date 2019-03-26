package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySourceManager;
import seedu.address.model.SourceManager;
import seedu.address.model.source.Source;

/**
 * An Immutable Source Manager that is serializable to JSON format.
 */
@JsonRootName(value = "sourcemanager")
class JsonSerializableSourceManager {

    public static final String MESSAGE_DUPLICATE_SOURCE = "Source list contains duplicate entires.";

    private final List<JsonAdaptedSource> sources = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSourceManager} with the given sources.
     */
    @JsonCreator
    public JsonSerializableSourceManager(@JsonProperty("sources") List<JsonAdaptedSource> sources) {
        this.sources.addAll(sources);
    }

    /**
     * Converts a given {@code ReadOnlySourceManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSourceManager}.
     */
    public JsonSerializableSourceManager(ReadOnlySourceManager source) {
        sources.addAll(source.getSourceList().stream().map(JsonAdaptedSource::new).collect(Collectors.toList()));
    }

    /**
     * Converts this source manager into the model's {@code SourceManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SourceManager toModelType() throws IllegalValueException {
        SourceManager sourceManager = new SourceManager();
        for (JsonAdaptedSource jsonAdaptedSource : sources) {
            Source source = jsonAdaptedSource.toModelType();
            if (sourceManager.hasSource(source)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SOURCE);
            }
            sourceManager.addSource(source);
        }
        return sourceManager;
    }

}
