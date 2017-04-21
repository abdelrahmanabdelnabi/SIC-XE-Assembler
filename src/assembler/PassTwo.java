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

        for (Instruction curInst : instructions) {
            /*
             * If is Instruction
             */
            if (curInst.getType() == Instruction.InstructionType.Instruction) {
                Format format = OPTAB.get(curInst.getMnemonic()).getFormat();
                switch (format) {
                    case FORMAT1:
                        int opCode = getOpCode(curInst.getMnemonic());
                        curInst.setObjectCode(ObjectBuilder.buildFormatOne(opCode));
                        break;
                    case FORMAT2:
                        handleFormat2(curInst, format2);
                        curInst.setObjectCode(format2.toString());
                        break;
                    case FORMAT3:
                        // TODO: Build the object code using the builder
                        curInst.setObjectCode(format3.toString());
                        break;
                    case FORMAT4:
                        // TODO: Build the object code using the builder
                        curInst.setObjectCode(format3.toString());
                    default:
                        break;
                }
            }
            /*
             * if is assembler directive
             */
            else if (curInst.getType() == Instruction.InstructionType.Directive) {
                // TODO : Handle directives
            }
        }
    }

    private void handleFormat2(Instruction inst, ObjectBuilder format_2) {
        String mnemonic = inst.getMnemonic();

        format_2.setOpCode(getOpCode(mnemonic));

        if (getFirstOperandType(mnemonic) == REGISTER) {
            format_2.setOperand(getRegisterNumber(inst.getOperand().split(",")[0]));
        } else if (getFirstOperandType(mnemonic) == VALUE) {
            format_2.setOperand(Integer.parseInt(inst.getOperand().split(",")[0]));
        }

        if (getSecondOperandType(mnemonic) == REGISTER) {
            format_2.setSecondOperand(getRegisterNumber(inst.getOperand().split(",")[1]));
        } else if (getSecondOperandType(mnemonic) == VALUE) {
            format_2.setSecondOperand(Integer.parseInt(inst.getOperand().split(",")[1]) - 1);
        } else {
            format_2.setSecondOperand(0);
        }

    }

    private void handleFormat3() {

    }

    private void handleFormat4() {

    }

    public List<Instruction> getOutputInstructions() {
        return instructions;
    }

}
