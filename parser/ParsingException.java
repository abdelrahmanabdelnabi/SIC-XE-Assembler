package parser;

/**
 * Created by abdelrahman on 3/22/17.
 */

/**
 * A class representing a parsing error
 * causes of the exception could be illegal instruction format
 */
public class ParsingException extends RuntimeException {
    int lineNumber;

    public ParsingException(String message, int lineNumber) {
        super(message);
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
