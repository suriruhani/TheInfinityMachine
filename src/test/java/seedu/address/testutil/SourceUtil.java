package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditSourceDescriptor;
import seedu.address.model.source.Source;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Source.
 */
public class SourceUtil {

    /**
     * Returns an add command string for adding the {@code source}.
     */
    public static String getAddCommand(Source source) {
        return AddCommand.COMMAND_WORD + " " + getSourceDetails(source);
    }

    /**
     * Returns the part of command string for the given {@code source}'s details.
     */
    public static String getSourceDetails(Source source) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + source.getTitle().title + " ");
        sb.append(PREFIX_TYPE + source.getType().type + " ");
        sb.append(PREFIX_AUTHOR + source.getAuthor().author + " ");
        sb.append(PREFIX_DETAILS + source.getDetail().detail + " ");
        source.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditSourceDescriptor}'s details.
     */
    public static String getEditSourceDescriptorDetails(EditSourceDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.title).append(" "));
        descriptor.getType().ifPresent(type -> sb.append(PREFIX_TYPE).append(type.type).append(" "));
        descriptor.getAuthor().ifPresent(author -> sb.append(PREFIX_AUTHOR).append(author.author).append(" "));
        descriptor.getDetails().ifPresent(detail -> sb.append(PREFIX_DETAILS).append(detail.detail).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
