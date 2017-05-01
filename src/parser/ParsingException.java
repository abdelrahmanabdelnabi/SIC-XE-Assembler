package src.parser;

/*
  Created by abdelrahman on 3/22/17.
 */

/**
 * A class representing a parsing error
 * causes of the exception could be illegal instruction format
 */
public class ParsingException extends RuntimeException {
    private final int lineNumber;

    public ParsingException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }

// --Commented out by Inspection START (5/1/17 3:49 AM):
//    public int getLineNumber() {
//        return lineNumber;
//    }
// --Commented out by Inspection STOP (5/1/17 3:49 AM)
}
