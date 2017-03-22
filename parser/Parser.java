package parser;

/**
 * Created by abdelrahman on 3/22/17.
 */

import assembler.Instruction;
import sun.rmi.log.LogInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Responsible for reading an input assembly file
 * possible containing errors, parses it an returns
 * an arrayList of <code>Instruction<code/> in the order they
 * appear in the input file
 */
public class Parser {
    private String filePath;

    ArrayList<Instruction> parsedInstructions;
    BufferedReader br = new BufferedReader(new StringReader(filePath));

    public Parser(String filePath) {
        this.filePath = filePath;
        parsedInstructions = new ArrayList<Instruction>();
    }

    /**
     * Opens the file specified by the <code>filePath<code/>
     */
    public void openFile() throws IOException {

    }


    /**
     * Parses the file specified in the path
     * reads it line by line and creates a list of instructions
     * in the same order they appear in the file
     *
     * @throws ParsingException in case the input file is illegal
     */
    public void parse() {

    }

    /**
     * returns the ArrayList of Instructions created by parse()
     */
    public ArrayList<Instruction> getParsedInstuctions() {
        return parsedInstructions;
    }

}
