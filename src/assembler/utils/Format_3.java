package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_3 extends ObjectBuilder {
    // TODO : ارحم ميتين اهلي
    //TODO : DONOT MAKE THME PRIVATE FFS !
    boolean isIndirect;
    boolean isImmediate;
    boolean isIndexed;
    boolean isBaseRelative;
    boolean isPCRelative;

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
