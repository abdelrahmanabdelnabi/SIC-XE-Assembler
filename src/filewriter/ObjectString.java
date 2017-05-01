package src.filewriter;

import src.assembler.Instruction;
import src.assembler.datastructures.LiteralProp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.assembler.Common.extendToLength;
import static src.assembler.datastructures.Format.FORMAT4;
import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.datastructures.OperandType.VALUE.NUM;

/**
 * Created by ahmed on 4/19/17.
 */
public class ObjectString implements StringGenerator {
    private List<Instruction> instructions;
    private HashMap<String, LiteralProp> literalsTable;
    private StringBuilder objectCode;
    private StringBuilder Mrecords;

    public ObjectString(List<Instruction> instructions, HashMap<String, LiteralProp> literalsTable) {
        this.instructions = instructions;
        this.literalsTable = literalsTable;
        objectCode = new StringBuilder();
        Mrecords = new StringBuilder();
    }

    public String toString() {
        form_H();
        form_T();
        form_E();
        return objectCode.toString();
    }


    private void form_H() {
        // H RECORD
        objectCode.append("H");
        objectCode.append(String.format("%-6s", getProgramName()));
        objectCode.append(extendToLength(Integer.toHexString(getStartAddress()).toUpperCase(), 6));
        objectCode.append(extendToLength(Integer.toHexString(getProgramLength()).toUpperCase(), 6));
        objectCode.append("\n");
        // END OF H RECORD
    }

    private void form_T() {
        // T RECORD
        int expectedAddress = 0;
        StringBuilder T = new StringBuilder();
        int startAddress = instructions.get(0).getAddress();
        boolean addressFlag = false;
        // Loop all the instructions
        for (int i = 0; i < instructions.size(); i++) {

            Instruction inst = instructions.get(i);
            expectedAddress = inst.getAddress();

            // If found multiple data-storage then continue;
            if (addressFlag && !inst.getHasObject() && !inst.getMnemonic().equals("END")) continue;

            if (addressFlag) {
                addressFlag = false;
                startAddress = inst.getAddress();
            }

            // Literals Insertion
            if (inst.getMnemonic().equals("LTORG")) {
                boolean found = true;
                while (found) {
                    found = false;
                    for (Map.Entry<String, LiteralProp> literal : literalsTable.entrySet()) {
                        if (literal.getValue().getAddress() == expectedAddress) {
                            if (T.length() + literal.getValue().getObjectCode().length() <= 60) {
                                T.append(literal.getValue().getObjectCode());
                            } else {
                                makeSingle_T(T, startAddress);
                                // Create new T record
                                T = new StringBuilder(literal.getValue().getObjectCode());
                                startAddress = literal.getValue().getAddress();
                            }
                            found = true;
                            expectedAddress = expectedAddress + literal.getValue().getLength();
//                        literalsTable.remove(literal.getKey());
                        }
                    }
                }
            }

            if (!inst.getHasObject()) {
                String mnemonic = inst.getMnemonic();
                // if RESW or RESB found close current T and open new one
                if (mnemonic.equals("RESW") || mnemonic.equals("RESB")) {
                    makeSingle_T(T, startAddress);
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
                makeSingle_T(T, startAddress);

                // Create new T record
                T = new StringBuilder(inst.getObjectCode());
                startAddress = inst.getAddress();
            }

            if (inst.getFormat() == FORMAT4 && inst.getValueType() != NUM)
                form_M(inst);
        }

        // append remaining literals
        boolean found = true;
        while (found) {
            found = false;
            for (Map.Entry<String, LiteralProp> literal : literalsTable.entrySet()) {
                if (literal.getValue().getAddress() == expectedAddress) {
                    if (T.length() + literal.getValue().getObjectCode().length() <= 60) {
                        T.append(literal.getValue().getObjectCode());
                    } else {
                        makeSingle_T(T, startAddress);
                        // Create new T record
                        T = new StringBuilder(literal.getValue().getObjectCode());
                        startAddress = literal.getValue().getAddress();
                    }
                    found = true;
                    expectedAddress = expectedAddress + literal.getValue().getLength();
//                        literalsTable.remove(literal.getKey());
                }
            }
        }

        // append remaining T
        makeSingle_T(T, startAddress);
        objectCode.append(Mrecords.toString());

    }


    private void makeSingle_T(StringBuilder T, int startAddress) {
        // Close current T Record
        objectCode.append("T");
        // starting address of the record
        objectCode.append(extendToLength(Integer.toHexString(startAddress), 6).toUpperCase());
        // record length
        objectCode.append(extendToLength(Integer.toHexString(T.length() / 2), 2).toUpperCase());
        // the object code
        objectCode.append(T.toString()).append("\n");
    }

    private void form_M(Instruction inst) {
        Mrecords.append("M");
        Mrecords.append(extendToLength(Integer.toHexString(inst.getAddress() + 1), 6).toUpperCase());
        Mrecords.append("05\n");
    }

    private void form_E() {
        // Create E
        objectCode.append("E");
        objectCode.append(extendToLength(Integer.toHexString(getStartAddress()), 6).toUpperCase());
    }
}
