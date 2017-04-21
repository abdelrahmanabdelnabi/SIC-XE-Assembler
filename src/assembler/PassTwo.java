package src.assembler;

import src.assembler.datastructures.Format;
import src.assembler.datastructures.InstProp;
import src.assembler.datastructures.OpcodeTable;
import src.assembler.utils.Format_2;
import src.assembler.utils.Format_3;
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
        ObjectBuilder format3_4 = new Format_3();

        for (Instruction curInst : instructions) {
            /*
             * If is Instruction
             */
//            if (curInst.getType() == Instruction.InstructionType.Instruction) {
            Format format = OPTAB.get(curInst.getMnemonic()).getFormat();
            if (curInst.getType() == Instruction.InstructionType.Instruction) {
                switch (format) {
                    case FORMAT1:
                        // TODO: Build the object code using the builder
                        int opCode = getOpCode(curInst.getMnemonic());
                        curInst.setObjectCode(ObjectBuilder.buildFormatOne(opCode));
                        break;
                    case FORMAT2:
                        // TODO: build the object code using the builder
                        curInst.setObjectCode(format2.toString());
                        break;
                    case FORMAT3:
                        // TODO: Build the object code using the builder
                        curInst.setObjectCode(format3_4.toString());
                        break;
                    case FORMAT4:
                        // TODO: Build the object code using the builder
                        curInst.setObjectCode(format3_4.toString());
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
