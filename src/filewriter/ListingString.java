package src.filewriter;

import src.assembler.Instruction;

import java.util.List;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/19/17.
 */
public class ListingString implements StringGenerator {
    private List<Instruction> instructions;
    private StringBuilder data;

    public ListingString(List<Instruction> instructions) {
        this.instructions = instructions;
        data = new StringBuilder();
        formString();
    }

    public String toString() {
        return data.toString();
    }

    private void formString() {
        // 90
        data.append(getSeperator());
        // 4 line - 10 loc cnt - 30 label - 10 mnemonic - 20 operand-10 object
        data.append(String.format("%-3s| %-10s| %-20s| %-10s| %-20s| %-10s\n", "Line", "LOC_CTR",
                "LABEL", "MNEMONIC", "OPERAND", "Object Code"));
        data.append(getSeperator());

        for (Instruction instruction : instructions) {
            data.append(formInstructionString(instruction));
        }
        data.append(getSeperator());

    }

    private String formInstructionString(Instruction inst) {
        return String.format(" %-3s| %-10s| %-20s| %-10s| %-20s| %-10s\n", Integer.toString(inst.getLineNumber()),
                extendToLength(Integer.toHexString(inst.getAddress()), 6),
                inst.getLabel(),
                inst.getMnemonic(),
                inst.getOperand(),
                inst.getObjectCode());
    }

    private String getSeperator() {
        return "======================================================================================\n";
    }
}
