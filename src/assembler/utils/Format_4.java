package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/21/17.
 */
public class Format_4 extends Format_3 {

    @Override
    public String toString() {
        // Clear the String
        objectCode = "";
        // Concat. OpCode
        objectCode += parseOpCodeBinary();
        // Concat. Flags N I X B P E
        objectCode += parseFlags();
        // Convert First part to 3 HEX
        objectCode = convertToHex(objectCode);

        // Add 5 HEX Operand
        objectCode += (extendToLength(Integer.toHexString(operand), 5));

        // reset defaults
        super.resetFields();
        return objectCode.toUpperCase();
    }

    private String convertToHex(String binary) {
        return extendToLength(Integer.toHexString(Integer.parseInt(binary, 2)), 3);
    }

    private String parseOpCodeBinary() {
        // Parse OpCode as binary String , extend its length to 8, get first 6 bits
        return extendToLength(Integer.toBinaryString(opCode), 8).substring(0, 6);
    }

    private String parseFlags() {
        String flags = "";

        //n i
        if (!isIndirect && !isImmediate) flags += "11";
        else if (isIndirect) flags += "10";
        else flags += "01";
        //x
        if (isIndexed) flags += "1";
        else flags += "0";

        //base  && PC
        flags += "00";
        flags += "0";

        // Add E = 1
        return flags + "1";
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT4;
    }

}
