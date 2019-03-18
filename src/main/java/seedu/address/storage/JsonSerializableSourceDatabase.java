package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.source.Source;

/**
 * An Immutable Source Database that is serializable to JSON format.
 */
@JsonRootName(value = "sourcedatabase")
class JsonSerializableSourceDatabase {

    public static final String MESSAGE_DUPLICATE_PERSON = "Source list contains duplicate entires.";

    private final List<JsonAdaptedSource> sources = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSourceDatabase} with the given persons.
     */
    @JsonCreator
    public JsonSerializableSourceDatabase(@JsonProperty("sources") List<JsonAdaptedSource> sources) {
        this.sources.addAll(sources);
    }

    /**
     * Converts a given {@code ReadOnlySourceDatabase} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSourceDatabase}.
     */
    public JsonSerializableSourceDatabase(ReadOnlySourceDatabase source) {
        sources.addAll(source.getSourceList().stream().map(JsonAdaptedSource::new).collect(Collectors.toList()));
    }

    /**
     * Converts this source database into the model's {@code SourceDatabase} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public SourceDatabase toModelType() throws IllegalValueException {
        SourceDatabase sourceDB = new SourceDatabase();
        for (JsonAdaptedSource jsonAdaptedSource : persons) {
            Source source = jsonAdaptedSource.toModelType();
            if (sourceDB.hasSource(source)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            sourceDB.addSource(source);
        }
        return sourceDB;
    }

}
