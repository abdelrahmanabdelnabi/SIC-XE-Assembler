package parser;

/**
 * Created by abdelrahman on 3/22/17.
 */

import assembler.Instruction;

import java.util.ArrayList;

/**
 * Responsible for reading an input assembly file
 * possible containing comments or unexpected characters, parses it an creates
 * an arrayList of Instruction in the order they appear in the input file.
 * does not know the details of the assembler or instructions
 */
public class Parser {
    ArrayList<Instruction> parsedInstructions;
    InputReader reader;

    public Parser(InputReader reader) {
        this.reader = reader;
        parsedInstructions = new ArrayList<Instruction>();
    }


    /**
     * Parses the file specified in the path
     * reads it line by line and creates a list of instructions
     * in the same order they appear in the file
     *
     * @throws ParsingException in case the input file contains unexpected text
     */
    public void parse() throws ParsingException {

    }

    /**
     * returns the ArrayList of Instructions created by parse()
     */
    public ArrayList<Instruction> getParsedInstuctions() {
        return parsedInstructions;
    }

}
