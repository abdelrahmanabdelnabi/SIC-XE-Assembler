package src.parser;

/*
 * Created by abdelrahman on 3/22/17.
 */

import src.assembler.Instruction;
import src.assembler.Logger;

import java.io.IOException;
import java.util.ArrayList;

import static src.assembler.datastructures.OpcodeTable.isMnemonic;

/**
 * Responsible for reading an input assembly file
 * possibly containing comments or unexpected characters, parses it an creates
 * an arrayList of Instruction in the order they appear in the input file.
 * does not know the details of the src.assembler or instructions
 */
public class Parser {
    private final ArrayList<Instruction> parsedInstructions;
    private final InputReader reader;

    public Parser(InputReader reader) {
        this.reader = reader;
        parsedInstructions = new ArrayList<>();
    }

    /**
     * Parses the file specified in the path
     * reads it line by line and creates a list of instructions
     * in the same order they appear in the file
     *
     * @throws ParsingException in case the input file contains unexpected text
     */
    /*
     * Made Private
     *
     * @throws ParsingException
     */
    public void parse() throws ParsingException {
        Logger.Log("Start File Parsing");
        String newLine;
        int lineNumber = 0;
        try {
            while ((newLine = reader.getLine()) != null) {
                lineNumber++;
                // Replace all whitespaces/tabs/spaces with a single space
                newLine = newLine.trim().replaceAll("^ +| +$|( )+|\t+", " ");
                // check if comment line , continue
                if (newLine.length() == 0 || newLine.charAt(0) == '.') continue;

                // else split line to tokens
                String tokens[] = newLine.split(" ");
                // classify line

                /*
                 * Format 1,2,3
                 */
                if (tokens.length == 3 && isMnemonic(tokens[1])) {
                    parseInstruction(tokens[0], tokens[1], tokens[2], lineNumber);
                } else if (tokens.length == 2 && isMnemonic(tokens[0])) {
                    parseInstruction("", tokens[0], tokens[1], lineNumber);
                } else if (tokens.length == 2 && isMnemonic(tokens[1])) {
                    parseInstruction(tokens[0], tokens[1], "", lineNumber);
                } else if (tokens.length == 1 && isMnemonic(tokens[0])) {
                    parseInstruction("", tokens[0], "", lineNumber);
                }

                /*
                 * Format 4
                 */
                else if (tokens.length == 3 && tokens[1].charAt(0) == '+' && isMnemonic(tokens[1].substring(1))) {
                    parseInstruction(tokens[0], tokens[1], tokens[2], lineNumber);
                } else if (tokens.length == 2 && tokens[0].charAt(0) == '+' && isMnemonic(tokens[0].substring(1))) {
                    parseInstruction("", tokens[0], tokens[1], lineNumber);
                } else if (tokens.length == 2 && tokens[1].charAt(0) == '+' && isMnemonic(tokens[1].substring(1))) {
                    parseInstruction(tokens[0], tokens[1], "", lineNumber);
                }

                /*
                 * Error !
                 */
                else {
                    // TODO: print a more descriptive error message (ex: unknown mnemonic /
                    // TODO: unexpected token ...
                    // TODO : THIS IS DESCRIPTIVE ENOUGH FOR A PARSER
                    Logger.LogError("Line " + lineNumber + " ( " + newLine + " )" + " is Not a " +
                            "valid SIC(/XE) instruction !");
                    throw new ParsingException("Unrecognized line format " + "( " + newLine + " )",
                            lineNumber);
                }
            }
            Logger.Log("Parsing Completed Successfully");
            LexicalAnalyzer analyzer = new LexicalAnalyzer(parsedInstructions);
            analyzer.inspectCode();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.Log("Parsing Failed");
        }
    }

    /**
     * Parse single instruction
     */
    private void parseInstruction(String label, String mnemonic, String operand, int lineNumber) {
        parsedInstructions.add(new Instruction(label, mnemonic, operand, lineNumber));
    }


    /**
     * Calls Parse then
     * returns the ArrayList of Instructions created by parse()
     */
    public ArrayList<Instruction> getParsedInstuctions() {
        return parsedInstructions;
    }

}
