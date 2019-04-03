package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.DeletedSources;
import seedu.address.model.ReadOnlyDeletedSources;
import seedu.address.model.source.Source;

/**
 * An Immutable Deleted Sources that is serializable to JSON format.
 */
@JsonRootName(value = "deletedsource")
public class JsonSerializableDeletedSources {

    public static final String MESSAGE_DUPLICATE_SOURCE = "Deleted Source list contains duplicate entries.";

    private final List<JsonAdaptedSource> deletedSources = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableDeletedSources} with the given sources.
     */
    @JsonCreator
    public JsonSerializableDeletedSources(@JsonProperty("deletedSources") List<JsonAdaptedSource> sources) {
        this.deletedSources.addAll(sources);
    }

    /**
     * Converts a given {@code ReadOnlySourceManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSourceManager}.
     */
    public JsonSerializableDeletedSources(ReadOnlyDeletedSources source) {
        deletedSources.addAll(source.getDeletedSourceList().stream()
                .map(JsonAdaptedSource::new).collect(Collectors.toList()));
    }

    /**
     * Converts this deleted sources list into the model's {@code DeletedSources} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public DeletedSources toModelType() throws IllegalValueException {
        DeletedSources deletedSourceList = new DeletedSources();
        for (JsonAdaptedSource jsonAdaptedSource : deletedSources) {
            Source source = jsonAdaptedSource.toModelType();
            if (deletedSourceList.hasDeletedSource(source)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SOURCE);
            }
            deletedSourceList.addDeletedSource(source);
        }
        return deletedSourceList;
    }

}
