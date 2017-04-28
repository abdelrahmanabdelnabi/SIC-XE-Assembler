package src.parser;

/*
 * Created by ahmed on 4/28/17.
 */

import src.assembler.Instruction;
import src.assembler.Logger;
import src.assembler.datastructures.OperandType;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static src.assembler.Common.buildErrorString;
import static src.assembler.Common.getRawOperand;
import static src.assembler.InstructionPart.MNEMONIC;
import static src.assembler.InstructionPart.OPERAND;
import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.datastructures.OperandType.*;
import static src.assembler.datastructures.OperandType.VALUE.*;

/**
 * This Class is responsible for code syntax analysis
 * Detects lexical errors in the input code
 * Takes an ArrayList of Instructions, analyze it, then sets the Error Flag at
 * Logger Class, Using Logger.LogError(msg);
 */
public class LexicalAnalyzer {
    private ArrayList<Instruction> srcCode;
    private String errorStr;

    public LexicalAnalyzer(ArrayList<Instruction> srcCode) {
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
        String mnemonic = inst.getMnemonic().replace("+", "");
        String operand = inst.getOperand();

        // if is valid opCode
        if (isOpcode(mnemonic)) {
            // Validate 1st Operand
            OperandType expected1stOperand = getFirstOperandType(mnemonic);
            OperandType actual1stOperand = getOperandType(inst);
            if (expected1stOperand != actual1stOperand) {
                errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid 1st Operand");
                Logger.LogError(errorStr);
            }

            // Validate 2nd operand if it exists
            OperandType expected2ndOperand = getSecondOperandType(mnemonic);
            if (expected2ndOperand != NONE) {
                OperandType actual2ndOperand = null;

                if (Pattern.matches("A|X|L|B|S|T|F|PC|SW", operand.split(",")[1]))
                    actual2ndOperand = REGISTER;

                if (Pattern.matches("[0-9]|[0-9][0-6]", operand.split(",")[1]))
                    actual2ndOperand = VALUE;

                if (expected2ndOperand != actual2ndOperand) {
                    errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid 2nd Operand");
                    Logger.LogError(errorStr);
                }
            }
        } else if (isDirective(mnemonic)) {

        } else {
            // if invalid mnemonic
            errorStr = buildErrorString(inst.getLineNumber(), MNEMONIC, "Undefined Mnemonic");
            Logger.LogError(errorStr);
        }
    }


    private OperandType getOperandType(Instruction inst) {
        String rawOperand = getRawOperand(inst.getOperand()).split(",")[0];

        // if there's none
        if (rawOperand.length() == 0)
            return NONE;

        // if is numerical
        if (Pattern.matches("0x[0-9A-F]+|[0-9]+", rawOperand)) {
            inst.setOperandType(VALUE);
            inst.setValueType(NUM);
            return VALUE;
        }


        // if is label
        if (Pattern.matches("[a-z]|[a-zA-Z][a-zA-Z0-9]+", rawOperand)) {
            inst.setOperandType(VALUE);
            inst.setValueType(LABEL);
            return VALUE;
        }

        // if is data
        if (Pattern.matches("X'[A-F0-9]+'|C'[a-zA-Z0-9]+'", rawOperand)) {
            inst.setOperandType(VALUE);
            inst.setValueType(DATA);
            return VALUE;
        }

        // if is register
        if (Pattern.matches("A|X|L|B|S|T|F|PC|SW", rawOperand))
            return REGISTER;

        return INVALID;
    }
}