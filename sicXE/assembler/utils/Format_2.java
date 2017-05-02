package sicXE.assembler.utils;

import sicXE.assembler.datastructures.Format;

import static sicXE.assembler.Common.extendToLength;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_2 extends ObjectBuilder {
    private int secondOperand;

    @Override
    public String toString() {
        // Clear The String
        objectCode = "";
        // Insert Object Code
        objectCode += String.format("%02X", opCode);
        // Insert Operands
        objectCode += extendToLength(Integer.toHexString(operand), 1);
        objectCode += extendToLength(Integer.toHexString(secondOperand), 1);

        // reset defaults
        resetFields();
        return objectCode.toUpperCase();
    }

    @Override
    void resetFields() {
        super.resetFields();
        secondOperand = 0;
    }

    @Override
    public ObjectBuilder setIndirect(boolean isIndirect) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public ObjectBuilder setImmediate(boolean isImmediate) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public ObjectBuilder setIndexed(boolean isIndexed) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public ObjectBuilder setBaseRelative(boolean isBaseRelative) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public ObjectBuilder setPCRelative(boolean isPCRelative) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public ObjectBuilder setSecondOperand(int secondOperand) {
        this.secondOperand = secondOperand;
        return this;
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT2;
    }

}
