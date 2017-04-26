package src.filewriter;

import src.assembler.Instruction;

import java.util.List;

import static src.assembler.Common.extendToLength;
import static src.assembler.datastructures.OpcodeTable.*;

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
        return objectCode.toString();
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
        boolean addressFlag = false;
        // Loop all the instructions
        for (Instruction inst : instructions) {

            // If found multiple data-storage then continue;
            if (addressFlag && !inst.getHasObject()) continue;

            if (addressFlag) {
                addressFlag = false;
                startAddress = inst.getAddress();
            }

            if (!inst.getHasObject()) {
                String mnemonic = inst.getMnemonic();
                // if RESW or RESB found close current T and open new one
                if (mnemonic.equals("RESW") || mnemonic.equals("RESB")) {
                    form_T_Record(T, startAddress);
                    T = new StringBuilder();
                    // set the flag to set the address just after the reserved block
                    addressFlag = true;
                } else {
                    continue;
                }
            }

            if (inst.getObjectCode().length() + T.length() <= 60) {
                T.append(inst.getObjectCode());
            } else {
                form_T_Record(T, startAddress);

                // Create new T record
                T = new StringBuilder(inst.getObjectCode());
                startAddress = inst.getAddress();
            }
        }
        // append remaining T
        form_T_Record(T, startAddress);

        // Create E
        objectCode.append("E");
        objectCode.append(extendToLength(Integer.toHexString(getStartAddress()), 6).toUpperCase());
    }

    private void form_T_Record(StringBuilder T, int startAddress) {
        // Close current T Record
        objectCode.append("T");
        // starting address of the record
        objectCode.append(extendToLength(Integer.toHexString(startAddress), 6).toUpperCase());
        // record length
        objectCode.append(extendToLength(Integer.toHexString(T.length() / 2), 2).toUpperCase());
        // the object code
        objectCode.append(T.toString()).append("\n");
    }
}
