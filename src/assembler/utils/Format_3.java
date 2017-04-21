package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_3 extends Format_2 {

    String isIndirect = "0";
    String isImmediate = "0";
    String isIndexed = "0";
    String isBaseRelative = "0";
    String isPCRelative = "0";

//    private StringBuilder builder;

    @Override
    public String toString() {
//        // TODO: implement this method
//        String objectCode = builder.toString();
//
//        builder.setLength(0);
//        return objectCode;
        String objectString = "";
        // Add only first 6 bits of the opCode 'as binary'
        String BinaryString = Integer.toBinaryString(opCode);
        String extended = extendToLength(BinaryString, 8);
        String trimmed = extended.substring(0, 6);
        objectString += (trimmed);
        // N I X B P E
        objectString += (isIndirect);
        objectString += (isImmediate);
        objectString += (isIndexed);
        objectString += (isBaseRelative);
        objectString += (isPCRelative);
        objectString += ("0");
        //
        objectString = extendToLength(Integer.toHexString(Integer.parseInt(objectString, 2)), 3);
        objectString += (extendToLength(Integer.toHexString(operand), 3));

        return objectString.toUpperCase();
    }

    @Override
    public void setIndirect() {
        this.isIndirect = "1";

    }

    @Override
    public void setImmediate() {
        this.isImmediate = "1";

    }

    @Override
    public void setIndexed() {
        this.isIndexed = "1";
    }

    @Override
    public void setBaseRelative() {
        this.isBaseRelative = "1";

    }

    @Override
    public void setPCRelative() {
        this.isPCRelative = "1";

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
