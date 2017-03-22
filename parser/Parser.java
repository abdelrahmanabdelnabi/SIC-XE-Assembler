package parser;

/**
 * Created by abdelrahman on 3/22/17.
 */

import java.io.IOException;

/**
 * Responsible for reading an input assembly file
 * possible containing errors, parses it an returns
 * an arrayList of <code>Instruction<code/> in the order they
 * appear in the input file
 */
public class Parser {
    private String filePath;

    public Parser(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Opens the file specified by the <code>filePath<code/>
     */
    public void openFile() throws IOException {

    }

}
