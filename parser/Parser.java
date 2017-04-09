package parser;

/**
 * Created by abdelrahman on 3/22/17.
 */

import assembler.Instruction;
import assembler.Logger;
import assembler.datastructures.OpcodeTable;

import java.io.IOException;
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
        try {
            Logger.Log("Parsing File");
            String newLine;
            int lineNumber = 0;
            while ((newLine = reader.getLine()) != null) {
                lineNumber++;
                // Replace all whitespaces/tabs/spaces with a single space
                newLine = newLine.replaceAll("^ +| +$|( )+", "$1");
                // check if comment line , continue
                if (newLine.charAt(0) == '.') continue;

                // else split line to tokens
                String tokens[] = newLine.split(" ");
                // classify line
                if (tokens.length == 3 && (OpcodeTable.isOpcode(tokens[1]) || OpcodeTable.isDirective(tokens[1]))) {
                    parseInstruction(tokens[0], tokens[1], tokens[2], lineNumber);
                } else if (tokens.length == 2 && (OpcodeTable.isOpcode(tokens[0]) || OpcodeTable.isDirective(tokens[0]))) {
                    parseInstruction("", tokens[0], tokens[1], lineNumber);
                } else if (tokens.length == 2 && (OpcodeTable.isOpcode(tokens[1]) || OpcodeTable.isDirective(tokens[1]))) {
                    parseInstruction(tokens[0], tokens[1], "", lineNumber);
                } else if (tokens.length == 1 && (OpcodeTable.isOpcode(tokens[0]) || OpcodeTable.isDirective(tokens[0]))) {
                    parseInstruction("", tokens[0], "", lineNumber);
                } else {
                    throw new ParsingException("Unrecognized line format", lineNumber);
                }

                Logger.Log("Parsing Completed Successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse single instruction
     */
    private void parseInstruction(String label, String mnemonic, String operand, int lineNumber) {
        parsedInstructions.add(new Instruction(label, mnemonic, operand, lineNumber));
    }

    /**
     * returns the ArrayList of Instructions created by parse()
     */
    public ArrayList<Instruction> getParsedInstuctions() {
        return parsedInstructions;
    }

}
