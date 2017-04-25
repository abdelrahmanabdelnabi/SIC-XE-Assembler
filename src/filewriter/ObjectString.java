package src.filewriter;

import src.assembler.Instruction;

import java.util.List;

import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/19/17.
 */
public class ObjectString implements StringGenerator {
    private List<Instruction> instructions;
    private StringBuilder objectCode;

    public ObjectString(List<Instruction> instructions) {
        this.instructions = instructions;
        objectCode = new StringBuilder();
    }

    public String toString() {
        formHTE();
        return objectCode.toString().toUpperCase();
    }

    private void formHTE() {

        // H RECORD
        objectCode.append("H");
        objectCode.append(getProgramName()).append(" ");
        objectCode.append(extendToLength(Integer.toHexString(getStartAddress()), 6));
        objectCode.append(extendToLength(Integer.toHexString(getProgramLength()), 6));
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
                objectCode.append(extendToLength(Integer.toHexString(startAddress), 6));
                // record length
                objectCode.append(extendToLength(Integer.toHexString(T.length() / 2), 2));
                // the object code
                objectCode.append(T.toString()).append("\n");

                // Create to T record
                T = new StringBuilder(inst.getObjectCode());
                startAddress = inst.getAddress();
            }
        }
        // append remaining T
        objectCode.append("T");
        objectCode.append(extendToLength(Integer.toHexString(startAddress), 6));
        objectCode.append(extendToLength(Integer.toHexString(T.length() / 2), 2));
        objectCode.append(T.toString()).append("\n");

        // Create E
        objectCode.append("E");
        objectCode.append(extendToLength(Integer.toHexString(getStartAddress()), 6));
    }
}
