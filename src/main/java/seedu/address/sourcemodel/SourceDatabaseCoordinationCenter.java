package seedu.address.sourcemodel;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the model of a source database coordination center.
 */
public class SourceDatabaseCoordinationCenter {
    // Currently the same as database input format, but this may change in the future
    private static final Pattern ADD_INPUT_FORMAT = Pattern.compile(
            "(?<sourceTitle>[^/]+)"
            + " y/(?<sourceType>[^/]+)"
            + " d/(?<sourceDetails>[^/]+)"
            + "(?<sourceTags>(?: t/[^/]+)*)");

    private SourceDatabase sourceDatabase;

    /**
     * Constructor for the database coordinator, will automatically load any existing entries in the database file
     * The database will be empty if there is nothing in the existing file to load
     */
    public SourceDatabaseCoordinationCenter() {
        this.sourceDatabase = new SourceDatabase();
        System.out.println("The current database is of size " + sourceDatabase.getDatabaseSize() + ".");
    }

    /**
     * Takes in a user input and returns an boolean to see if the loop should end.
     * Carries out user operations for the database.
     *
     * @param order The user input.
     * @return Boolean to show if the loop should end.
     */
    public boolean executeOrders(String order) {
        String [] orderSplit = order.split(" ", 2);

        // There will always be a "first" element, so no array out of bounds exception can happen here
        String orderCommand = orderSplit[0];

        switch(orderCommand) {
        case "add":
            // Check size of array (in case user enters "add" and nothing else)
            if (orderSplit.length < 2) {
                System.out.println("Invalid command parameters.\n"
                        + "add TITLE y/TYPE d/DETAILS [t/TAG]");
                break;
            }

            String orderParameters = orderSplit[1];
            final Matcher matcher = ADD_INPUT_FORMAT.matcher(orderParameters);

            if (!matcher.matches()) {
                System.out.println("Invalid command parameters.\n"
                        + "add TITLE y/TYPE d/DETAILS [t/TAG]");
                break;
            }

            // Loads and stores basic information into Strings for use later (can be refactored if necessary)
            String inputTitle = matcher.group("sourceTitle");
            String inputType = matcher.group("sourceType");
            String inputDetails = matcher.group("sourceDetails");

            // sourceTags contains a line of tags separated by "/t", need to split them up and store into an ArrayList
            String inputTags = matcher.group("sourceTags");
            final String[] inputTagArray = inputTags.replaceFirst(" t/", "").split(" t/");

            // Cycles through the generated tag array and creates an ArrayList of tags
            ArrayList<String> sourceTagArray = new ArrayList<String>();
            for (int j = 0; j < inputTagArray.length; j++) {
                sourceTagArray.add(inputTagArray[j]);
            }

            // New Source object generated from the database file
            Source inputSource = new Source(inputTitle, inputType, inputDetails, sourceTagArray);

            // Add the new source to the ArrayList of sources
            sourceDatabase.addNewSource(inputSource);

            System.out.println("New source has been added.");
            break;

        case "delete":
            // Check size of array (in case user enters "delete" and nothing else)
            if (orderSplit.length < 2) {
                System.out.println("Invalid command parameters.\n"
                        + "delete INDEX");
                break;
            }

            int index = -1;
            try {
                index = Integer.parseInt(orderSplit[1]);
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid command parameters.\n"
                        + "delete INDEX");
                break;
            }

            // ArrayLists start from index 0, so a user entering 1 means deleting the first, or 0 index, item
            int actualIndex = index - 1;
            if (actualIndex < sourceDatabase.getDatabaseSize() && actualIndex > 0) {
                sourceDatabase.deleteSourceAtIndex(actualIndex);
                System.out.println("Source has been deleted.\n");
            } else {
                System.out.println("There is no such source index.\n");
            }
            break;

        case "list":
            for (int i = 0; i < sourceDatabase.getDatabaseSize(); i++) {
                Source source = sourceDatabase.getSourceAtIndex(i);

                System.out.println("=====================================\n"
                        + "Source: " + (i + 1) + "\n"
                        + source.getSourceTitle() + "\n"
                        + source.getSourceType() + "\n"
                        + source.getSourceDetails());

                ArrayList<String> sourceTags = source.getSourceTags();
                System.out.print("Tags: ");
                for (int j = 0; j < sourceTags.size(); j++) {
                    System.out.print(sourceTags.get(j) + " ");
                }
                System.out.println("\n=====================================\n");
            }
            break;

        case "exit":
            sourceDatabase.saveDatabase();
            return false;

        default:
            System.out.println("Invalid command. Refer to the following list for proper commands."
                    + "Instructions for commands:\n"
                    + "add TITLE y/TYPE d/DETAILS [t/TAG]\n"
                    + "delete INDEX\n"
                    + "list\n"
                    + "exit\n"
                    + "Standing by for orders...\n");
            break;
        }
        return true;
    }
}
