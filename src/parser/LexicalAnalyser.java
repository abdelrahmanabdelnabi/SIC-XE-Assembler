package src.parser;

/**
 * Created by ahmed on 4/28/17.
 */

import src.assembler.Instruction;
import src.assembler.InstructionPart;
import src.assembler.Logger;
import src.assembler.datastructures.OperandType;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static src.assembler.Common.buildErrorString;
import static src.assembler.Common.getOperandWithoutModeFlags;
import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.datastructures.OperandType.*;

/**
 * This Class is responsible for code syntax analysis
 * Detects lexical errors in the input code
 * Takes an ArrayList of Instructions, analyze it, then sets the Error Flag at
 * Logger Class, Using Logger.LogError(msg);
 */
public class LexicalAnalyser {
    private ArrayList<Instruction> srcCode;
    private String errorStr;

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
        String label = inst.getLabel();
        String mnemonic = inst.getMnemonic();
        String operand = inst.getOperand();

        // if invalid mnemonic
        if (!isMnemonic(mnemonic)) {
            errorStr = buildErrorString(inst.getLineNumber(),
                    InstructionPart.MNEMONIC,
                    "Undefined Mnemonic");
            Logger.LogError(errorStr);
            return;
        }

        // Validate 1st Operand
        OperandType expected1stOperand = getFirstOperandType(mnemonic);
        OperandType actual1stOperand = getOperandType(getOperandWithoutModeFlags(operand));
        if (expected1stOperand != actual1stOperand) {
            errorStr = buildErrorString(inst.getLineNumber(), InstructionPart.LABEL, "Invalid 1st Operand");
            Logger.LogError(errorStr);
        }

        // Validate 2nd operand if it exists
        OperandType expected2ndOperand = getSecondOperandType(mnemonic);
        if (expected2ndOperand != NONE) {
            OperandType actual2ndOperand = getOperandType(operand.split(",")[1]);
            if (expected2ndOperand != actual2ndOperand) {
                errorStr = buildErrorString(inst.getLineNumber(), InstructionPart.LABEL, "Invalid 2nd Operand");
                Logger.LogError(errorStr);
            }
        }
    }


    private OperandType getOperandType(String operand) {
        // if there's none
        if (operand.length() == 0)
            return NONE;
        // if is numerical
        if (Pattern.matches("0x[0-9]+|[0-9]+", getOperandWithoutModeFlags(operand)))
            return VALUE;


        // if is label
        if (Pattern.matches("[a-zA-Z][a-zA-Z0-9]+", getOperandWithoutModeFlags(operand)))
            return VALUE;

        // if is data
        if (Pattern.matches("X'[A-Z0-9]+'|C'[a-zA-Z0-9]+'", getOperandWithoutModeFlags(operand)))
            return VALUE;

        // if is register
        if (Pattern.matches("A|X|L|B|S|T|F|PC|SW", operand))
            return REGISTER;


        return INVALID;
    }
}
