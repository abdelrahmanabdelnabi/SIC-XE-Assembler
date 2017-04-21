package src.filewriter;

import src.assembler.Instruction;

import java.util.List;

import static src.assembler.datastructures.OpcodeTable.*;

/**
 * Created by ahmed on 4/19/17.
 */
public class FormObjectString {
    private List<Instruction> instructions;
    private StringBuilder objectCode;

    public FormObjectString(List<Instruction> instructions) {
        this.instructions = instructions;
        objectCode = new StringBuilder();
        formHTE();
    }

    public String toString() {
        return objectCode.toString().toUpperCase();
    }

    private void formHTE() {

        // H RECORD
        objectCode.append("H");
        objectCode.append(getProgramName()).append(" ");
        objectCode.append(stretch(Integer.toHexString(getStartAddress()), 6));
        objectCode.append(stretch(Integer.toHexString(getProgramLength()), 6));
        objectCode.append("\n");
        // END OF H RECORD


        // T RECORD
        StringBuilder T = new StringBuilder();
        int startAddress = instructions.get(0).getAddress();
        for (Instruction inst : instructions) {
            if (!inst.getHasObject()) continue;

            if (inst.getObjectCode().length() + T.length() <= 60) {
                T.append(inst.getObjectCode());
            } else {
                // Close current T Record
                objectCode.append("T");
                // starting address of the record
                objectCode.append(stretch(Integer.toHexString(startAddress), 6));
                // record length
                objectCode.append(stretch(Integer.toHexString(T.length() / 2), 2));
                // the object code
                objectCode.append(T.toString()).append("\n");

                // Create to T record
                T = new StringBuilder(inst.getObjectCode());
                startAddress = inst.getAddress();
            }
        }
        // append remaining T
        objectCode.append("T");
        objectCode.append(stretch(Integer.toHexString(startAddress), 6));
        objectCode.append(stretch(Integer.toHexString(T.length() / 2), 2));
        objectCode.append(T.toString()).append("\n");

        // Create E
        objectCode.append("E");
        objectCode.append(stretch(Integer.toHexString(getStartAddress()), 6));
    }

    private String stretch(String s, int len) {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < len - s.length(); i++) ss.append('0');
        ss.append(s);
        return ss.toString();
    }
}
