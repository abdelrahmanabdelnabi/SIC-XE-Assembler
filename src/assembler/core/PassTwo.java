package src.assembler.core;

import src.assembler.*;
import src.assembler.datastructures.Format;
import src.assembler.datastructures.InstProp;
import src.assembler.utils.Format_2;
import src.assembler.utils.Format_3;
import src.assembler.utils.Format_4;
import src.assembler.utils.ObjectBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static src.assembler.Instruction.InstructionType.Directive;
import static src.assembler.Instruction.InstructionType.Instruction;
import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.datastructures.OperandType.REGISTER;
import static src.assembler.datastructures.OperandType.VALUE;
import static src.assembler.datastructures.RegisterTable.getRegisterNumber;

/**
 * Created by ahmed on 4/21/17.
 */
public class PassTwo {
    private final HashMap<String, SymbolProperties> symbolTable;
    private List<src.assembler.Instruction> instructions;
    private Set<String> directives = getAssemblerDirectivesSet();
    private Map<String, InstProp> OPTAB = getOpcodeTable();

    private boolean isBaseSet = false;
    private int baseAddress = 0;

    PassTwo(List<Instruction> instructions, HashMap<String, SymbolProperties> symbolTable) {
        this.instructions = instructions;
        this.symbolTable = symbolTable;
    }

    public void execute() throws AssemblerException {
        // TODO: format 3, 4 & assembler directives


        for (Instruction inst : instructions) {
            ObjectBuilder format2 = new Format_2();
            ObjectBuilder format3 = new Format_3();
            ObjectBuilder format4 = new Format_4();
            /*
             * If is Instruction
             */
            if (inst.getType() == Instruction) {
                String mnemonic = inst.getMnemonic();
                // FORMAT 4
                if (mnemonic.startsWith("+")) {
                    checkIndexed(inst, format4);
                    checkIndirectImmediate(inst, format4);
                    handleFormat4(inst, format4);
                    inst.setObjectCode(format4.toString());
                } else {
                    Format format = OPTAB.get(inst.getMnemonic()).getFormat();
                    switch (format) {
                        case FORMAT1:
                            int opCode = getOpCode(inst.getMnemonic());
                            inst.setObjectCode(ObjectBuilder.buildFormatOne(opCode));
                            break;
                        case FORMAT2:
                            handleFormat2(inst, format2);
                            inst.setObjectCode(format2.toString());
                            break;
                        case FORMAT3:
                            String obj = handleFormat3(inst, format3);
                            inst.setObjectCode(obj);
                            break;
                    }
                }
            }
            /*
             * if is assembler directive
             */
            else if (inst.getType() == Directive) {
                // TODO : Handle directives (also set baseFlag and base address appropriately)
                String directive = inst.getMnemonic();
                switch (directive) {
                    case "BASE":
                        // check if label is defined
                        String operand = inst.getOperand();
                        if(!symbolTable.containsKey(operand)) {
                            // TODO: throw error
                        } else {
                            isBaseSet = true;
                            baseAddress = symbolTable.get(operand).getAddress();
                        }

                        break;
                    case "NOBASE":
                        isBaseSet = false;
                        baseAddress = 0;
                        break;
                }
            }
        }
    }

    private void handleFormat2(Instruction inst, ObjectBuilder format_2) {
        String mnemonic = inst.getMnemonic();

        format_2.setOpCode(getOpCode(mnemonic));

        // 1st Operand
        if (getFirstOperandType(mnemonic) == REGISTER) {
            format_2.setOperand(getRegisterNumber(inst.getOperand().split(",")[0]));
        } else if (getFirstOperandType(mnemonic) == VALUE) {
            format_2.setOperand(Integer.parseInt(inst.getOperand().split(",")[0]));
        }

        // 2nd Operand
        if (getSecondOperandType(mnemonic) == REGISTER) {
            format_2.setSecondOperand(getRegisterNumber(inst.getOperand().split(",")[1]));
        } else if (getSecondOperandType(mnemonic) == VALUE) {
            format_2.setSecondOperand(Integer.parseInt(inst.getOperand().split(",")[1]) - 1);
        } else {
            format_2.setSecondOperand(0);
        }

    }

    private String handleFormat3(Instruction inst, ObjectBuilder format3) {
        // prepare needed input
        int PC = inst.getAddress() + 3;
        int opCode = getOpCode(inst.getMnemonic());
        String operand = inst.getOperand();


        // Checks if an operand is Valid.. does not account for literals
        // note also that this does NOT allow spaces in the operand
        boolean validFormat =
                operand.matches("([#@]?([a-zA-Z]+|-?([0-9]+|(0x)?-?[0-9A-F]+)))|(([a-zA-Z]+|-?([0-9]+|(0x)?-?[0-9A-F]+))(,X)?)");

        if (!validFormat) {
            String error = PassOne.buildErrorString(inst.getLineNumber(), InstructionPart
                    .OPERAND, ErrorStrings.INVALID_OPERAND_FORMAT);
            Logger.LogError(error);
            throw new AssemblerException(error);
        }

        // prepare needed flags
        boolean indirect = operand.startsWith("@");
        boolean indexed = operand.endsWith(",X");
        boolean immediate = operand.startsWith("#");
        boolean simple = !(indirect || indexed || immediate);

        String rawOperand = getRawOperand(operand);

        boolean isDecimal = rawOperand.matches("-?[0-9]+");
        boolean isHexaDecimal = rawOperand.matches("0x-?[0-9A-F]+");

        int displacement = 0;

        if (!(isDecimal || isHexaDecimal)) {
            if (!symbolTable.containsKey(rawOperand)) {
                String error = PassOne.buildErrorString(inst.getLineNumber(), InstructionPart
                        .OPERAND, ErrorStrings.UNDEFINED_LABEL);

                Logger.LogError(error);
                throw new AssemblerException(inst.toString() + " " + error);
            }
        } else { // if number
            // check if it fits in the displacement of a fromat 3 instruction
            int value;
            if(isDecimal) {
                value = Integer.parseInt(rawOperand);
            }
            else // hexadecimal
            {
                value = Integer.parseInt(rawOperand.replace("0x", ""), 16);
            }

            if(!isFitPCRelative(value)) {
                String error = PassOne.buildErrorString(inst.getLineNumber(), InstructionPart
                        .OPERAND, ErrorStrings.DISP_OUT_OF_RANGE);

                Logger.LogError(error);
                throw new AssemblerException(error);
            }
            displacement = value;
        }

        // set displacement if not a number
        if(!(isDecimal || isHexaDecimal)) {

            // check range of operand for base and pc relative
            int labelAddress = symbolTable.get(rawOperand).getAddress();
            if(isFitPCRelative(labelAddress - PC)) {
                displacement = labelAddress - PC;
            } else if(isBaseSet && isFitConstant(labelAddress - baseAddress)) {
                displacement = labelAddress - baseAddress;
            } else {
                // error
                // operand address can not fit into a format 3 instruction
                String error = PassOne.buildErrorString(inst.getLineNumber(), InstructionPart
                        .OPERAND, ErrorStrings.DISP_OUT_OF_RANGE);

                Logger.LogError(error);
                throw new AssemblerException(error);
            }
        }

        format3.setOperand(displacement);
        format3.setOpCode(opCode);

        // according to the last page in the reference operand can either be one of simple
        // or indirect or indexed or immediate

        // set flags
        if (simple) {
            format3.setIndirect(true);
            format3.setImmediate(true);
        } else if (immediate) {
            format3.setIndirect(false);
            format3.setImmediate(true);
        } else if (indexed) {
            format3.setIndirect(true);
            format3.setImmediate(true);
            format3.setIndexed(true);
        } else { // indirect
            format3.setIndirect(true);
            format3.setImmediate(false);
        }

        return format3.toString();
    }


    private String getRawOperand(String operand) {
        return operand.replace("#", "")
                .replace("@", "").replace(",X", "");
    }

    private void handleFormat4(Instruction inst, ObjectBuilder format4) {
        format4.setOpCode(getOpCode(inst.getMnemonic().substring(1)));
        int TA = getOperandTargetAddress(inst);
        format4.setOperand(TA);
    }

    private void checkIndexed(Instruction inst, ObjectBuilder objectBuilder) {
        if (inst.getOperand().contains(",X")) objectBuilder.setIndexed(true);
    }

    private void checkIndirectImmediate(Instruction inst, ObjectBuilder objectBuilder) {
        String operand = inst.getOperand();
        if (operand.startsWith("@")) objectBuilder.setIndirect(true);
        else if (operand.startsWith("#")) objectBuilder.setImmediate(true);
    }

    private int getOperandTargetAddress(Instruction instruction) {
        String operand = instruction.getOperand();
        int TA = 0;
        operand = operand.replace(",X", "");
        operand = operand.replace("@", "");
        operand = operand.replace("#", "");
        // check is value or symbol
        if (Pattern.matches("[0-9]+", operand)) {
            TA = Integer.parseInt(operand);
        } else if (symbolTable.containsKey(operand)) {
            TA = symbolTable.get(operand).getAddress();
        } else {
            // TODO Error while parsing
        }
        return TA;
    }

    private boolean isFitPCRelative(int displacement) {
        return displacement >= -2048 && displacement <= 2047;
    }

    /* returns true if the number is between 0 and 4095 inclusive */
    private boolean isFitConstant(int number) {
        return number >= 0 && number <= 4095;
    }

    public List<Instruction> getOutputInstructions() {
        return instructions;
    }

}
