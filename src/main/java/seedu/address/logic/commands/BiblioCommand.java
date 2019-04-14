package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.source.BiblioFields;
import seedu.address.model.source.Source;

/**
 * Generates a bibliography from a source at the specified index of appropriate style.
 */

public class BiblioCommand extends Command {
    public static final String COMMAND_WORD = "biblio";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a bibliography in appropriate style\n"
            + "Parameters: \n"
            + "INDEX (must be a positive integer)\n"
            + "FORMAT (must be a non-empty string)\n"
            + "Example: " + COMMAND_WORD + " 1 APA";

    public static final String MESSAGE_SUCCESS = "Bibliography generated:\n";
    public static final String MESSAGE_UNPOPULATED_FIELDS = "\n\nWarning: Incomplete bibliography\n"
            + "The following fields are recommended for this source type but are not populated:\n";
    public static final String MESSAGE_UNSUPPORTED_TYPE = "This source type is currently not supported\n"
            + "Source type must be one of the following:\n"
            + "Book\n"
            + "Journal Article\n"
            + "Website\n";
    public static final String MESSAGE_UNSUPPORTED_FORMAT = "Format must be one of the following:\n"
            + "APA\n"
            + "MLA\n";

    private final Index targetIndex;
    private final String format;

    public BiblioCommand(Index targetIndex, String format) {
        this.targetIndex = targetIndex;
        this.format = format;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Source> lastShownList = model.getFilteredSourceList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
        }

        Source targetSource = lastShownList.get(targetIndex.getZeroBased());
        String biblioEntry;

        switch (format) {
        case "APA":
            switch (targetSource.getType().type) {
            case "Book":
                biblioEntry = apaBook(targetSource);
                return new CommandResult(MESSAGE_SUCCESS + biblioEntry);
            case "Website":
                biblioEntry = apaWebsite(targetSource);
                return new CommandResult(MESSAGE_SUCCESS + biblioEntry);
            case "Journal Article":
                biblioEntry = apaJournal(targetSource);
                return new CommandResult(MESSAGE_SUCCESS + biblioEntry);
            default:
                return new CommandResult(MESSAGE_UNSUPPORTED_TYPE);
            }
        case "MLA":
            switch (targetSource.getType().type) {
            case "Book":
                biblioEntry = mlaBook(targetSource);
                return new CommandResult(MESSAGE_SUCCESS + biblioEntry);
            case "Website":
                biblioEntry = mlaWebsite(targetSource);
                return new CommandResult(MESSAGE_SUCCESS + biblioEntry);
            case "Journal Article":
                biblioEntry = mlaJournal(targetSource);
                return new CommandResult(MESSAGE_SUCCESS + biblioEntry);
            default:
                return new CommandResult(MESSAGE_UNSUPPORTED_TYPE);
            }
        default:
            throw new CommandException(MESSAGE_UNSUPPORTED_FORMAT);
        }
    }

    /**
     * Generates a bibliography entry for a book in APA style.
     */
    private String apaBook(Source targetSource) {

        String biblioEntry;
        String suggestedFields = "";
        BiblioFields biblioFields = targetSource.getBiblioFields();
        String targetTitle = targetSource.getTitle().title;
        String targetAuthor = targetSource.getAuthor().author;

        biblioEntry = String.format("%s. ", targetAuthor);

        if (biblioFields.getYear() != "") {
            biblioEntry += String.format("(%s). ", biblioFields.getYear());
        } else {
            biblioEntry += "(n.d.). ";
            suggestedFields = suggestedFields + "Year; ";
        }

        biblioEntry += String.format("<i>%s</i>. ", targetTitle);

        if (biblioFields.getCity() != "") {
            biblioEntry += String.format("%s: ", biblioFields.getCity());
        } else {
            biblioEntry += "<Placeholder City>: ";
            suggestedFields = suggestedFields + "City; ";
        }

        if (biblioFields.getPublisher() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getPublisher());
        } else {
            biblioEntry += "<Placeholder Publisher>. ";
            suggestedFields = suggestedFields + "Publisher; ";
        }

        if (suggestedFields.equals("")) {
            return biblioEntry;
        } else {
            return biblioEntry + MESSAGE_UNPOPULATED_FIELDS + suggestedFields;
        }
    }

    /**
     * Generates a bibliography entry for a Journal Article in APA style.
     */
    private String apaJournal(Source targetSource) {

        String biblioEntry;
        String suggestedFields = "";
        BiblioFields biblioFields = targetSource.getBiblioFields();
        String targetTitle = targetSource.getTitle().title;
        String targetAuthor = targetSource.getAuthor().author;

        biblioEntry = String.format("%s. ", targetAuthor);

        if (biblioFields.getYear() != "") {
            biblioEntry += String.format("(%s). ", biblioFields.getYear());
        } else {
            biblioEntry += "(n.d.). ";
            suggestedFields = suggestedFields + "Year; ";
        }

        biblioEntry += String.format("%s. ", targetTitle);

        if (biblioFields.getJournal() != "") {
            biblioEntry += String.format("<i>%s</i>, ", biblioFields.getJournal());
        } else {
            biblioEntry += "<i><Placeholder Journal></i>, ";
            suggestedFields = suggestedFields + "Journal; ";
        }

        if (biblioFields.getPages() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getPublisher());
        } else {
            biblioEntry += "<Placeholder Pages>. ";
            suggestedFields = suggestedFields + "Pages; ";
        }

        if (suggestedFields.equals("")) {
            return biblioEntry;
        } else {
            return biblioEntry + MESSAGE_UNPOPULATED_FIELDS + suggestedFields;
        }
    }

    /**
     * Generates a bibliography entry for a Website in APA style.
     */
    private String apaWebsite(Source targetSource) {

        String biblioEntry;
        String suggestedFields = "";
        BiblioFields biblioFields = targetSource.getBiblioFields();
        String targetTitle = targetSource.getTitle().title;
        String targetAuthor = targetSource.getAuthor().author;

        biblioEntry = String.format("%s. ", targetAuthor);

        if (biblioFields.getMonth() != "") {
            if (biblioFields.getDay() != "") {
                biblioEntry += String.format("(%s %s, ", biblioFields.getDay(), biblioFields.getMonth());
            } else {
                biblioEntry += String.format("(%s, ", biblioFields.getMonth());
                suggestedFields = suggestedFields + "Day; ";
            }
        } else {
            biblioEntry += "(";
            if (biblioFields.getDay() == "") {
                suggestedFields = suggestedFields + "Day; ";
            }
            suggestedFields = suggestedFields + "Month; ";
        }

        if (biblioFields.getYear() != "") {
            biblioEntry += String.format("%s). ", biblioFields.getYear());
        } else {
            biblioEntry += "n.d.). ";
            suggestedFields = suggestedFields + "Year; ";
        }

        biblioEntry += String.format("<i>%s</i>. ", targetTitle);

        if (biblioFields.getWebsite() != "") {
            biblioEntry += String.format("Retrieved from %s: ", biblioFields.getWebsite());
        } else {
            biblioEntry += "Retrieved from <Placeholder Website>: ";
            suggestedFields = suggestedFields + "Website; ";
        }

        if (biblioFields.getUrl() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getUrl());
        } else {
            biblioEntry += "<Placeholder URL>. ";
            suggestedFields = suggestedFields + "URL; ";
        }

        if (suggestedFields.equals("")) {
            return biblioEntry;
        } else {
            return biblioEntry + MESSAGE_UNPOPULATED_FIELDS + suggestedFields;
        }
    }


    /**
     * Generates a bibliography entry for a Book in MLA style.
     */
    private String mlaBook(Source targetSource) {

        String biblioEntry;
        String suggestedFields = "";
        BiblioFields biblioFields = targetSource.getBiblioFields();
        String targetTitle = targetSource.getTitle().title;
        String targetAuthor = targetSource.getAuthor().author;

        biblioEntry = String.format("%s. ", targetAuthor);

        biblioEntry += String.format("<i>%s</i>. ", targetTitle);

        if (biblioFields.getCity() != "") {
            biblioEntry += String.format("%s: ", biblioFields.getCity());
        } else {
            biblioEntry += "<Placeholder City>: ";
            suggestedFields = suggestedFields + "City; ";
        }

        if (biblioFields.getPublisher() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getPublisher());
        } else {
            biblioEntry += "<Placeholder Publisher>. ";
            suggestedFields = suggestedFields + "Publisher; ";
        }

        if (biblioFields.getYear() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getYear());
        } else {
            biblioEntry += "(n.d.). ";
            suggestedFields = suggestedFields + "Year; ";
        }

        if (biblioFields.getMedium() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getMedium());
        } else {
            biblioEntry += "<Placeholder Medium>. ";
            suggestedFields = suggestedFields + "Medium; ";
        }

        if (suggestedFields.equals("")) {
            return biblioEntry;
        } else {
            return biblioEntry + MESSAGE_UNPOPULATED_FIELDS + suggestedFields;
        }
    }

    /**
     * Generates a bibliography entry for a Journal Article in MLA style.
     */
    private String mlaJournal(Source targetSource) {

        String biblioEntry;
        String suggestedFields = "";
        BiblioFields biblioFields = targetSource.getBiblioFields();
        String targetTitle = targetSource.getTitle().title;
        String targetAuthor = targetSource.getAuthor().author;

        biblioEntry = String.format("%s. ", targetAuthor);

        biblioEntry += String.format("\"%s\". ", targetTitle);

        if (biblioFields.getJournal() != "") {
            biblioEntry += String.format("<i>%s</i> ", biblioFields.getJournal());
        } else {
            biblioEntry += "<i><Placeholder Journal></i> ";
            suggestedFields = suggestedFields + "Journal; ";
        }

        if (biblioFields.getYear() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getYear());
        } else {
            biblioEntry += "(n.d.). ";
            suggestedFields = suggestedFields + "Year; ";
        }

        if (biblioFields.getPages() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getPublisher());
        } else {
            biblioEntry += "<Placeholder Pages>. ";
            suggestedFields = suggestedFields + "Pages; ";
        }

        if (biblioFields.getMedium() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getMedium());
        } else {
            biblioEntry += "<Placeholder Medium>. ";
            suggestedFields = suggestedFields + "Medium; ";
        }

        if (suggestedFields.equals("")) {
            return biblioEntry;
        } else {
            return biblioEntry + MESSAGE_UNPOPULATED_FIELDS + suggestedFields;
        }
    }

    /**
     * Generates a bibliography entry for a Journal Article in MLA style.
     */
    private String mlaWebsite(Source targetSource) {

        String biblioEntry;
        String suggestedFields = "";
        BiblioFields biblioFields = targetSource.getBiblioFields();
        String targetTitle = targetSource.getTitle().title;
        String targetAuthor = targetSource.getAuthor().author;

        biblioEntry = String.format("%s. ", targetAuthor);

        biblioEntry += String.format("<i>%s</i>. ", targetTitle);

        if (biblioFields.getMonth() != "") {
            if (biblioFields.getDay() != "") {
                biblioEntry += String.format("%s %s, ", biblioFields.getDay(), biblioFields.getMonth());
            } else {
                biblioEntry += String.format("%s, ", biblioFields.getMonth());
                suggestedFields = suggestedFields + "Day; ";
            }
        } else {
            if (biblioFields.getDay() == "") {
                suggestedFields = suggestedFields + "Day; ";
            }
            suggestedFields = suggestedFields + "Month; ";
        }

        if (biblioFields.getYear() != "") {
            biblioEntry += String.format("%s). ", biblioFields.getYear());
        } else {
            biblioEntry += "n.d. ";
            suggestedFields = suggestedFields + "Year; ";
        }

        if (biblioFields.getMedium() != "") {
            biblioEntry += String.format("%s. ", biblioFields.getMedium());
        } else {
            biblioEntry += "<Placeholder Medium>. ";
            suggestedFields = suggestedFields + "Medium; ";
        }

        if (biblioFields.getUrl() != "") {
            biblioEntry += String.format("<%s>. ", biblioFields.getUrl());
        } else {
            biblioEntry += "<<Placeholder URL>>. ";
            suggestedFields = suggestedFields + "URL; ";
        }

        if (suggestedFields.equals("")) {
            return biblioEntry;
        } else {
            return biblioEntry + MESSAGE_UNPOPULATED_FIELDS + suggestedFields;
        }
    }
}
