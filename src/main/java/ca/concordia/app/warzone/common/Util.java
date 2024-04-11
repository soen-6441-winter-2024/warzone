package ca.concordia.app.warzone.common;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Utility class containing reusable methods along the application, these methods shouldn't implement game logic, but
 * pure utilities to make code easier to read and promote reuse among different class of components.
 */
public class Util {

    /**
     * Validates is a paragraph is formatted correctly
     *
     * @param p_iterator Iterator of the lines
     * @param p_title Title of the paragraph
     * @param p_lineValidator Predicate method to validate each line
     * @return true if valid, else false.
     */
    public static boolean validateParagraph(Iterator<String> p_iterator, String p_title, Predicate<String> p_lineValidator) {

        String line = getFirstNonEmptyLine(p_iterator);

        if (p_iterator.hasNext() && line.equals("[" + p_title +"]")) {
            line = p_iterator.next();
        } else {
            return false;
        }

        while (!line.trim().isEmpty()){

            if (!p_lineValidator.test(line)) {
                return false;
            }

            if (p_iterator.hasNext()) {
                line = p_iterator.next();
            } else {
                break;
            }
        }

        return true;
    }

    /**
     * Get the first non-empty line from the lines of the file
     *
     * @param p_iterator Iterator referencing the lines of the file
     * @return Non-empty line
     */
    private static String getFirstNonEmptyLine(Iterator<String> p_iterator) {

        String line = "";

        while (p_iterator.hasNext() && line.trim().isEmpty()) {
            line = p_iterator.next();
        }

        return line;
    }
}
