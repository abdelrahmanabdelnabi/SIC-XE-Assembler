package src.filewriter;

import src.assembler.Instruction;

import java.util.List;

import static src.assembler.Common.extendToLength;

/**
 * Created by ahmed on 4/19/17.
 */
public class ListingString implements StringGenerator {
    private final List<Instruction> instructions;
    private final StringBuilder data;

    public ListingString(List<Instruction> instructions) {
        this.instructions = instructions;
        data = new StringBuilder();
    }

    public String toString() {
        formString();
        return data.toString();
    }

    private void formString() {
        // 90
        data.append(getSeparator());
        // 4 line - 10 loc cnt - 30 label - 10 mnemonic - 20 operand-10 object
        data.append(String.format("%-3s| %-10s| %-20s| %-10s| %-20s| %-10s\n", "Line", "LOC_CTR",
                "LABEL", "MNEMONIC", "OPERAND", "Object Code"));
        data.append(getSeparator());

        for (Instruction instruction : instructions) {
            data.append(formInstructionString(instruction));
        }
        data.append(getSeparator());

    }

    private String formInstructionString(Instruction inst) {
        return String.format(" %-3s| %-10s| %-20s| %-10s| %-20s| %-10s\n", Integer.toString(inst.getLineNumber()),
                extendToLength(Integer.toHexString(inst.getAddress()).toUpperCase(), 6),
                inst.getLabel(),
                inst.getMnemonic(),
                inst.getOperand(),
                inst.getObjectCode());
    }

    private String getSeparator() {
        return "======================================================================================\n";
    }
}
