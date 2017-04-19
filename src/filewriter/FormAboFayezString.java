package src.filewriter;

import src.assembler.Instruction;

import java.util.ArrayList;

/**
 * Created by ahmed on 4/19/17.
 */
public class FormAboFayezString {
    private ArrayList<Instruction> instructions;
    private StringBuilder data;

    public FormAboFayezString(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
        data = new StringBuilder();
        formString();
    }

    public String toString() {
        return data.toString();
    }

    private void formString() {
        // 90
        data.append("================================================================================================\n");
        // 4 line - 10 loc cnt - 30 label - 10 mnemonic - 20 operand-10 object
        data.append(String.format("%-3s| %-10s| %-30s| %-10s| %-20s| %-10s\n", "Line", "LOC_CTR",
                "LABEL", "MNEMONIC", "OPERAND", "Object Code"));
        data.append("================================================================================================\n");

        for (Instruction instruction : instructions) {
            data.append(formInstructionString(instruction));
        }
        data.append("================================================================================================\n");

    }

    private String formInstructionString(Instruction inst) {
        return String.format("%-3s | %-10s| %-30s| %-10s| %-20s| %-10s\n", Integer.toString(inst.getLineNumber()),
                stretch(Integer.toHexString(inst.getAddress()), 6),
                inst.getLabel(),
                inst.getMnemonic(),
                inst.getOperand(),
                inst.getObjectCode());
    }

    private String stretch(String s, int len) {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < len - s.length(); i++) ss.append('0');
        ss.append(s);
        return ss.toString();
    }
}
