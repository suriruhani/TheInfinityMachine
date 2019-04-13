package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditSourceDescriptor;
import seedu.address.model.source.Author;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditSourceDescriptor objects.
 */
public class EditSourceDescriptorBuilder {

    private EditSourceDescriptor descriptor;

    public EditSourceDescriptorBuilder() {
        descriptor = new EditSourceDescriptor();
    }

    public EditSourceDescriptorBuilder(EditSourceDescriptor descriptor) {
        this.descriptor = new EditSourceDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSourceDescriptor} with fields containing {@code source}'s details
     */
    public EditSourceDescriptorBuilder(Source source) {
        descriptor = new EditSourceDescriptor();
        descriptor.setTitle(source.getTitle());
        descriptor.setType(source.getType());
        descriptor.setAuthor(source.getAuthor());
        descriptor.setDetails(source.getDetail());
        descriptor.setTags(source.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditSourceDescriptor} that we are building.
     */
    public EditSourceDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(new Title(title));
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code EditSourceDescriptor} that we are building.
     */
    public EditSourceDescriptorBuilder withType(String type) {
        descriptor.setType(new Type(type));
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code EditSourceDescriptor} that we are building.
     */
    public EditSourceDescriptorBuilder withAuthor(String author) {
        descriptor.setAuthor(new Author(author));
        return this;
    }

    /**
     * Sets the {@code Detail} of the {@code EditSourceDescriptor} that we are building.
     */
    public EditSourceDescriptorBuilder withDetail(String detail) {
        descriptor.setDetails(new Detail(detail));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditSourceDescriptor}
     * that we are building.
     */
    public EditSourceDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditSourceDescriptor build() {
        return descriptor;
    }
}
