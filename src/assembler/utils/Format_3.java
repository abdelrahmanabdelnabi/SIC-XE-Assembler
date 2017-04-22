package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_3 extends ObjectBuilder {
    // TODO : ارحم ميتين اهلي
    // TODO : الديزاين ده خره يا حبي
    // TODO : DONOT MAKE THME PRIVATE FFS !
    boolean isIndirect;
    boolean isImmediate;
    boolean isIndexed;
    boolean isBaseRelative;
    boolean isPCRelative;

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

        // Add 3 HEX Operand
        objectCode += (extendToLength(Integer.toHexString(operand), 3));

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
        //n
        if (isIndirect) flags += "1";
        else flags += "0";
        //i
        if (isImmediate) flags += "1";
        else flags += "0";
        //x
        if (isIndexed) flags += "1";
        else flags += "0";
        //b
        if (isBaseRelative) flags += "1";
        else flags += "0";
        //p
        if (isPCRelative) flags += "1";
        else flags += "0";

        // Add E = 0
        return flags + "0";
    }

    @Override
    public void setIndirect(boolean isIndirect) {
        this.isIndirect = isIndirect;

    }

    @Override
    public void setImmediate(boolean isImmediate) {
        this.isImmediate = isImmediate;

    }

    @Override
    public void setIndexed(boolean isIndexed) {
        this.isIndexed = isIndexed;

    }

    @Override
    public void setBaseRelative(boolean isBaseRelative) {
        this.isBaseRelative = isBaseRelative;

    }

    @Override
    public void setPCRelative(boolean isPCRelative) {
        this.isPCRelative = isPCRelative;

    }

    @Override
    public void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    public void setSecondOperand(int secondOperand) {
        throw new UnsupportedOperationException("Format 3 and 4 do not have a second operand");
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT3;
    }
}
