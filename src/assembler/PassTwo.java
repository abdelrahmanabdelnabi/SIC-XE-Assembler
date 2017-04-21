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

    PassTwo(List<Instruction> instructions, HashMap<String, SymbolProperties> symbolTable) {
        this.instructions = instructions;
        this.symbolTable = symbolTable;
    }

    public void execute() {
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

    private void handleFormat3(Instruction inst, ObjectBuilder format3) {
        format3.setOpCode(getOpCode(inst.getMnemonic()));

        // TARGET ADDRESS AND DISPLACEMENT
        int TA = getOperandTargetAddress(inst), DISPLACEMENT;
/*
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 * TODO CRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
 */
        // Check Base Vs Pc relative
        DISPLACEMENT = TA - inst.getAddress();
        if (DISPLACEMENT <= 2047) {
            format3.setBaseRelative();
            format3.setOperand(DISPLACEMENT);
        } else {
            format3.setBaseRelative();
            // TODO calculate base address IMPORTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            // TODO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            // TODO AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAT
//            DISPLACEMENT = TA - ;
            format3.setOperand(DISPLACEMENT);
        }
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

    public List<Instruction> getOutputInstructions() {
        return instructions;
    }

}
