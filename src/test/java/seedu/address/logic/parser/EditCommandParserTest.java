package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FOO;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_NETWORK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_SOURCE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditSourceDescriptor;
import seedu.address.model.source.Author;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditSourceDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_ENGINEERING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_ENGINEERING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_ENGINEERING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 j/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTags_failure() {
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC, Title.MESSAGE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC, Type.MESSAGE_CONSTRAINTS); // invalid type
        assertParseFailure(parser, "1" + INVALID_AUTHOR_DESC, Author.MESSAGE_CONSTRAINTS); // invalid author
        assertParseFailure(parser, "1" + INVALID_DETAIL_DESC, Detail.MESSAGE_CONSTRAINTS); // invalid detail

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FOO + TAG_DESC_BAR + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FOO + TAG_EMPTY + TAG_DESC_BAR, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FOO + TAG_DESC_BAR, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_TYPE_DESC + VALID_DETAIL_ENGINEERING,
                Title.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_SOURCE;
        String userInput = targetIndex.getOneBased() + TAG_DESC_BAR
                + TYPE_DESC_ENGINEERING + DETAIL_DESC_ENGINEERING + TITLE_DESC_ENGINEERING + TAG_DESC_FOO;

        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder().withTitle(VALID_TITLE_ENGINEERING)
                .withType(VALID_TYPE_ENGINEERING).withDetail(VALID_DETAIL_ENGINEERING)
                .withTags(VALID_TAG_BAR, VALID_TAG_FOO).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_SOURCE;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_ENGINEERING;

        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder()
                .withType(VALID_TYPE_ENGINEERING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_SOURCE;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_ENGINEERING;
        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder().withTitle(VALID_TITLE_ENGINEERING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // type
        userInput = targetIndex.getOneBased() + TYPE_DESC_ENGINEERING;
        descriptor = new EditSourceDescriptorBuilder().withType(VALID_TYPE_ENGINEERING).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // detail
        userInput = targetIndex.getOneBased() + DETAIL_DESC_ENGINEERING;
        descriptor = new EditSourceDescriptorBuilder().withDetail(VALID_DETAIL_ENGINEERING).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FOO;
        descriptor = new EditSourceDescriptorBuilder().withTags(VALID_TAG_FOO).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_SOURCE;
        String userInput = targetIndex.getOneBased() + DETAIL_DESC_ENGINEERING + TYPE_DESC_ENGINEERING
                + TAG_DESC_FOO + DETAIL_DESC_ENGINEERING + TYPE_DESC_ENGINEERING + TAG_DESC_FOO
                + DETAIL_DESC_NETWORK + TYPE_DESC_NETWORK + TAG_DESC_BAR;

        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder()
                .withType(VALID_TYPE_NETWORK).withDetail(VALID_DETAIL_NETWORK).withTags(VALID_TAG_FOO, VALID_TAG_BAR)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_SOURCE;
        String userInput = targetIndex.getOneBased() + INVALID_TYPE_DESC + TYPE_DESC_NETWORK;
        EditSourceDescriptor descriptor =
                new EditSourceDescriptorBuilder().withType(VALID_TYPE_NETWORK).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + TYPE_DESC_NETWORK + DETAIL_DESC_NETWORK;
        descriptor = new EditSourceDescriptorBuilder().withType(VALID_TYPE_NETWORK)
                .withDetail(VALID_DETAIL_NETWORK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_SOURCE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditSourceDescriptor descriptor = new EditSourceDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
