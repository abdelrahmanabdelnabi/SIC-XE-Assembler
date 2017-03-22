package parser;

import java.io.*;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class InputReader {
    BufferedReader reader;

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
    }

    public String getLine() throws IOException {
        return reader.readLine();
    }


    public static enum InputType {
        File,
        String;
    }
}
