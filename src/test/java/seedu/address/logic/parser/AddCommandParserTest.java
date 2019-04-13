package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AUTHOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DETAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FOO;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_ENGINEERING;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FOO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_NETWORK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_NETWORK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalSources.ENGINEERING;
import static seedu.address.testutil.TypicalSources.NETWORK;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.source.Author;
import seedu.address.model.source.Detail;
import seedu.address.model.source.Source;
import seedu.address.model.source.Title;
import seedu.address.model.source.Type;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.SourceBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Source expectedSource = new SourceBuilder(NETWORK).withTags(VALID_TAG_FOO).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE
                        + TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_FOO,
                new AddCommand(expectedSource));

        // multiple titles - last title accepted
        assertParseSuccess(parser,
                TITLE_DESC_ENGINEERING
                        + TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_FOO,
                new AddCommand(expectedSource));

        // multiple types - last type accepted
        assertParseSuccess(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_ENGINEERING
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_FOO,
                new AddCommand(expectedSource));

        // multiple authors - last author accepted
        assertParseSuccess(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_ENGINEERING
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_FOO,
                new AddCommand(expectedSource));

        // multiple details - last detail accepted
        assertParseSuccess(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_ENGINEERING
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_FOO,
                new AddCommand(expectedSource));

        // multiple tags - all accepted
        Source expectedSourceMultipleTags = new SourceBuilder(NETWORK).withTags(VALID_TAG_FOO, VALID_TAG_BAR).build();
        assertParseSuccess(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_BAR
                        + TAG_DESC_FOO,
                new AddCommand(expectedSourceMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Source expectedSource = new SourceBuilder(ENGINEERING).withTags().build();
        assertParseSuccess(parser,
                TITLE_DESC_ENGINEERING
                        + TYPE_DESC_ENGINEERING
                        + AUTHOR_DESC_ENGINEERING
                        + DETAIL_DESC_ENGINEERING,
                new AddCommand(expectedSource));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser,
                VALID_TITLE_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK,
                expectedMessage);

        // missing type prefix
        assertParseFailure(parser,
                TITLE_DESC_NETWORK
                        + VALID_TYPE_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK,
                expectedMessage);

        // missing author prefix
        assertParseFailure(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + VALID_AUTHOR_NETWORK
                        + DETAIL_DESC_NETWORK,
                expectedMessage);

        // missing detail prefix
        assertParseFailure(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + VALID_DETAIL_NETWORK,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_TITLE_NETWORK
                        + VALID_TYPE_NETWORK
                        + VALID_AUTHOR_NETWORK
                        + VALID_DETAIL_NETWORK,
                expectedMessage);
    }

    @Test
    public void parse_invalidTags_failure() {
        // invalid tag
        assertParseFailure(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + INVALID_TAG_DESC
                        + VALID_TAG_FOO,
                Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid title
        assertParseFailure(parser,
                INVALID_TITLE_DESC
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_BAR
                        + TAG_DESC_FOO,
                Title.MESSAGE_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser,
                TITLE_DESC_NETWORK
                        + INVALID_TYPE_DESC
                        + AUTHOR_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_BAR
                        + TAG_DESC_FOO,
                Type.MESSAGE_CONSTRAINTS);

        // invalid author
        assertParseFailure(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + INVALID_AUTHOR_DESC
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_BAR
                        + TAG_DESC_FOO,
                Author.MESSAGE_CONSTRAINTS);

        // invalid detail
        assertParseFailure(parser,
                TITLE_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + INVALID_DETAIL_DESC
                        + TAG_DESC_BAR
                        + TAG_DESC_FOO,
                Detail.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_TITLE_DESC
                        + TYPE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + INVALID_DETAIL_DESC,
                Title.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY
                        + TITLE_DESC_NETWORK
                        + AUTHOR_DESC_NETWORK
                        + TYPE_DESC_NETWORK
                        + DETAIL_DESC_NETWORK
                        + TAG_DESC_BAR
                        + TAG_DESC_FOO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
