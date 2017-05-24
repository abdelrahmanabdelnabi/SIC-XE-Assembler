package src.parser;

/*
 * Created by ahmed on 4/28/17.
 */

import src.assembler.datastructures.Instruction;
import src.assembler.datastructures.OperandType;
import src.misc.Logger;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static src.assembler.datastructures.InstructionPart.MNEMONIC;
import static src.assembler.datastructures.InstructionPart.OPERAND;
import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.datastructures.OperandType.*;
import static src.assembler.datastructures.OperandType.VALUE.*;
import static src.misc.Common.buildErrorString;
import static src.misc.Common.getRawOperand;

/**
 * This Class is responsible for code syntax analysis
 * Detects lexical errors in the input code
 * Takes an ArrayList of Instructions, analyze it, then sets the Error Flag at
 * Logger Class, Using Logger.LogError(msg);
 */
public class LexicalAnalyzer {
    private final ArrayList<Instruction> srcCode;

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
        String errorStr;

        // if is valid opCode
        if (isOpcode(mnemonic)) {
            // Validate 1st Operand
            OperandType expected1stOperand = getFirstOperandType(mnemonic);
            OperandType actual1stOperand = getOperandType(inst);
            if (expected1stOperand != actual1stOperand) {
                errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid 1st Operand");
                Logger.LogError(errorStr);
            }
            //
            inst.setOperandType(actual1stOperand);

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
            inst.setInstructionType(Instruction.InstructionType.Directive);
            inst.setOperandType(VALUE);
            switch (mnemonic) {
                case "RESW":
                    if (!Pattern.matches("[0-9]+", operand)) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid RESW Operand");
                        Logger.LogError(errorStr);
                    }
                    inst.setValueType(NUM);
                    break;
                case "RESB":
                    if (!Pattern.matches("[0-9]+", operand)) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid RESB Operand");
                        Logger.LogError(errorStr);
                    }
                    inst.setValueType(NUM);
                    break;
                case "WORD":
                    if (!Pattern.matches("0x[0-9A-F]{1,6}|X'[0-9]{1,6}'|C'[a-zA-Z0-9]{1,3}'", operand)) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid WORD Operand");
                        Logger.LogError(errorStr);
                    }
                    inst.setValueType(DATA);
                    break;
                case "BYTE"://
                    if (!Pattern.matches("X'[0-9A-F]+'|C'[a-zA-Z0-9]+'", operand)) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid BYTE Operand");
                        Logger.LogError(errorStr);
                    }
                    inst.setValueType(NUM);
                    break;

                case "START":
                    if (!Pattern.matches("(0x)?[0-9]+", operand)) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid BYTE Operand");
                        Logger.LogError(errorStr);
                    }
                    inst.setValueType(NUM);
                    break;

                case "END":
                    break;

                case "EXTREF":
                case "EXTDEF":
                    if (!Pattern.matches("(([A-Za-z][a-z0-9A-Z]+|[A-Za-z])(,)?)+", operand)
                            && !operand.endsWith(",")) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid EXTREF/EXTDEF Operand");
                        Logger.LogError(errorStr);
                    }
                    inst.setOperandType(VALUE);
                    inst.setValueType(LABEL);
                    break;

                // TODO Implement org,equ,csect
                case "ORG":
                    if (ORG(inst)) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid ORG Operand");
                        Logger.LogError(errorStr);
                    }
                    break;

                case "EQU":
                    if (EQU(inst) == null) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid EQU Operand");
                        Logger.LogError(errorStr);
                    }
                    break;

                case "CSECT":
                    if (operand.length() != 0) {
                        errorStr = buildErrorString(inst.getLineNumber(), OPERAND, "CSECT has no Operand");
                        Logger.LogError(errorStr);
                    }
                    inst.setOperandType(NONE);
                    break;

                default:
                    Logger.Log(inst.getLineNumber() + "  -> Unhandled Directive: " + mnemonic);
            }
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

        Logger.Log(buildErrorString(inst.getLineNumber(), OPERAND, "Invalid Operand"));
        return INVALID;
    }

    private VALUE EQU(Instruction inst) {
        String operand = inst.getOperand();
        inst.setOperandType(VALUE);

        // loc
        if (Pattern.matches("[*]", operand)) {
            inst.setValueType(LOCCTR);
            return LOCCTR;
        }

        // num
        if (Pattern.matches("(0x)?[0-9]+", operand)) {
            inst.setValueType(NUM);
            return NUM;
        }

        // single label
        if (Pattern.matches("[A-Za-z][A-Za-z0-9]*", operand)) {
            inst.setValueType(LABEL);
            return LABEL;
        }

        //
        if (Pattern.matches("X'[A-F0-9]+'|C'[a-zA-Z0-9]+'", operand)) {
            inst.setValueType(DATA);
            return DATA;
        }

        // expression
        if (Pattern.matches("(([a-zA-Z][a-zA-Z0-9]*([+]|[-]|[*]|[/]|))|(([0-9]+)([+]|[-]|[*]|[/]|)))+", operand)
                && !operand.endsWith("+") && !operand.endsWith("-")) {
            inst.setValueType(EXPRESSION);
            return EXPRESSION;
        }

        return null;
    }

    private boolean ORG(Instruction inst) {
        String operand = inst.getOperand();

        if (Pattern.matches("[0-9]+", operand)) {
            inst.setOperandType(VALUE);
            inst.setValueType(NUM);
            return false;
        }

        if (Pattern.matches("[A-Za-z][A-Za-z0-9]*", operand)) {
            inst.setOperandType(VALUE);
            inst.setValueType(LABEL);
            return false;
        }

        if (operand.length() == 0) {
            inst.setOperandType(NONE);
            return false;
        }

        return true;
    }
}
