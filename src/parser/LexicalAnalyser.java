package src.parser;

/**
 * Created by ahmed on 4/28/17.
 */

import src.assembler.Instruction;

import java.util.ArrayList;

/**
 * This Class is responsible for code syntax analysis
 * Detects lexical errors in the input code
 * Takes an ArrayList of Instructions, analyze it, then sets the Error Flag at
 * Logger Class, Using Logger.LogError(msg);
 */
public class LexicalAnalyser {
    private ArrayList<Instruction> srcCode;

    LexicalAnalyser(ArrayList<Instruction> srcCode) {
        this.srcCode = srcCode;
    }

    /**
     * For each instruction validate its syntax
     */
    public void inspectCode() {
        for (Instruction inst : srcCode) {
            validateSyntax(inst);
        }
    }

    private void validateSyntax(Instruction inst) {

    }
}
