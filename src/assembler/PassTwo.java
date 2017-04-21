package src.assembler;

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
    private List<Instruction> instructions;
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
        ObjectBuilder format2 = new Format_2();
        ObjectBuilder format3 = new Format_3();
        ObjectBuilder format4 = new Format_4();

        for (Instruction inst : instructions) {
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
                            // TODO: Build the object code using the builder
                            checkIndexed(inst, format3);
                            checkIndirectImmediate(inst, format3);
                            inst.setObjectCode(format3.toString());
                            break;
                    }
                }
            }
            /*
             * if is assembler directive
             */
            else if (inst.getType() == Directive) {
                // TODO : Handle directives
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
        int PC = inst.getAddress() + 3;

        int opCode = getOpCode(inst.getMnemonic());

        String operand = inst.getOperand();

        int displacement = 0;

        // Case 1: operand is only letters
        boolean direct = operand.matches("[a-zA-Z]+");
        boolean indirect = operand.matches("@[a-zA-Z]+");
        boolean numbersOnly = operand.matches("[0-9]+") || operand.matches("@[0-9]+");

        if (direct || indirect || numbersOnly) {
            if(numbersOnly){

                displacement = Integer.parseInt(operand.replace('@', '0'));

            } else if (!symbolTable.containsKey(operand)) {
                String error = PassOne.buildErrorString(inst.getLineNumber(), InstructionPart
                        .OPERAND, ErrorStrings.UNDEFINED_LABEL);

                Logger.LogError(error + "\n");
                throw new AssemblerException(error);
            } else {
                int labelAddress = symbolTable.get(operand).getAddress();

                if (isFitPCRelative(labelAddress - PC)) {
                    displacement = labelAddress - PC;
                    format3.setPCRelative();

                } else if (isFitBaseRelative(labelAddress - baseAddress)) {
                    displacement = labelAddress - baseAddress;

                    format3.setBaseRelative();
                    format3.setOperand(displacement);

                } else {
                    // error: displacement doesn't fit in instruction
                    String error = PassOne.buildErrorString(inst.getLineNumber(), InstructionPart
                            .OPERAND, ErrorStrings.DISP_OUT_OF_RANGE);

                    Logger.LogError(error + "\n");

                    throw new AssemblerException(error);
                }
            }

            format3.setIndirect();

            if(direct)
                format3.setImmediate();

            format3.setOpCode(opCode);

            format3.setOperand(displacement);

            return format3.toString();
        }

        // TODO: handle immediate and indexed modes

        return null;

    }

    private void handleFormat4(Instruction inst, ObjectBuilder format4) {
        format4.setOpCode(getOpCode(inst.getMnemonic().substring(1)));
        int TA = getOperandTargetAddress(inst);
        format4.setOperand(TA);
    }

    private void checkIndexed(Instruction inst, ObjectBuilder objectBuilder) {
        if (inst.getOperand().contains(",X")) objectBuilder.setIndexed();
    }

    private void checkIndirectImmediate(Instruction inst, ObjectBuilder objectBuilder) {
        String operand = inst.getOperand();
        if (operand.startsWith("@")) objectBuilder.setIndirect();
        else if (operand.startsWith("#")) objectBuilder.setImmediate();
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

    private boolean isFitPCRelative(int displacment) {
        return displacment >= -2048 && displacment <= 2047;
    }

    private boolean isFitBaseRelative(int displacement) {
        return displacement >= 0 && displacement <= 4095;
    }


    public List<Instruction> getOutputInstructions() {
        return instructions;
    }

}
