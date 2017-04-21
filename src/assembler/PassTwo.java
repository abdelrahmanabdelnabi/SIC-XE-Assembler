package src.assembler;

import src.assembler.datastructures.Format;
import src.assembler.datastructures.InstProp;
import src.assembler.datastructures.OpcodeTable;
import src.assembler.datastructures.RegisterTable;
import src.assembler.utils.Format_2;
import src.assembler.utils.Format_3;
import src.assembler.utils.Format_4;
import src.assembler.utils.ObjectBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static src.assembler.datastructures.OpcodeTable.getOpCode;

/**
 * Created by ahmed on 4/21/17.
 */
public class PassTwo {
    private final HashMap<String, SymbolProperties> symbolTable;
    private List<Instruction> instructions;
    private Set<String> directives = OpcodeTable.getAssemblerDirectivesSet();
    private Map<String, InstProp> OPTAB = OpcodeTable.getOpcodeTable();

    PassTwo(List<Instruction> instructions, HashMap<String, SymbolProperties> symbolTable) {
        this.instructions = instructions;
        this.symbolTable = symbolTable;
    }

    public void execute() {
        // TODO: format 3, 4 & assembler directives
        ObjectBuilder format2 = new Format_2();
        ObjectBuilder format_3 = new Format_3();
        ObjectBuilder format_4 = new Format_4();

        for (Instruction curInst : instructions) {
            /*
             * If is Instruction
             */
            if (curInst.getType() == Instruction.InstructionType.Instruction) {
                Format format = OPTAB.get(curInst.getMnemonic()).getFormat();
                switch (format) {
                    case FORMAT1:
                        // TODO: Build the object code using the builder
                        int opCode = getOpCode(curInst.getMnemonic());
                        curInst.setObjectCode(ObjectBuilder.buildFormatOne(opCode));
                        break;
                    case FORMAT2:
                        // TODO: build the object code using the builder
                        String operand1 = curInst.getOperand().split(",")[0];
                        format2.setOperand(RegisterTable.getRegisterNumber(operand1));
                        String operand2 = curInst.getOperand().split(",")[1];
                        if (curInst.getObjectCode().contains("SHIFT")) {
                            format2.setSecondOperand(Integer.parseInt(operand2));
                        } else {
                            format2.setSecondOperand(RegisterTable.getRegisterNumber(operand2));
                        }
                        curInst.setObjectCode(format2.toString());
                        break;
                    case FORMAT3:
                        // TODO: Build the object code using the builder
                        curInst.setObjectCode(format_3.toString());
                        break;
                    case FORMAT4:
                        // TODO: Build the object code using the builder
                        curInst.setObjectCode(format_3.toString());
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

    public List<Instruction> getOutputInstructions() {
        return instructions;
    }

}
