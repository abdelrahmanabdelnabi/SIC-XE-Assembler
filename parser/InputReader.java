package parser;

import java.io.*;

/**
 * Created by abdelrahman on 3/22/17.
 */

/**
 * Reads the input assembly instructions from an input stream
 * the input stream is either a file or a string
 */
public class InputReader {
    BufferedReader reader;
    InputType inputType;

    /**
     * Creates a buffered input reader that either reads from a file or from a string
     *
     * @param type the source of the input, either file or string
     * @param input the file name in case the input type is file, or
     *              the input itself in case the input type is string.
     */
    public InputReader(InputType type, String input) {
        if (type == InputType.File) {
            try {
                reader = new BufferedReader(new FileReader(input));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (type == InputType.String) {
            reader = new BufferedReader(new StringReader(input));
        }
        inputType = type;
    }

    /**
     * returns the next line in the input
     */
    public String getLine() throws IOException {
        return reader.readLine();
    }

    public void setInputString(String input) {
        if(inputType == InputType.String)
            reader = new BufferedReader(new StringReader(input));
    }

    public static enum InputType {
        File, String;
    }
}
