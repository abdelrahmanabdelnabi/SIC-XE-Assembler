package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_2 extends ObjectBuilder {
    protected int operand;
    private int secondOperand;

    @Override
    public String toString() {
        String builder = String.format("%02X", opCode);
        builder += extendToLength(Integer.toHexString(operand), 1);
        builder += extendToLength(Integer.toHexString(secondOperand), 1);
//        String builder = String.format("%02X", opCode) +
//                String.format("%01X", operand) +
//                String.format("%01X", secondOperand);
//
//        // TODO: reset values to default

        return builder;
    }

    @Override
    public void setIndirect(boolean isIndirect) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public void setImmediate(boolean isImmediate) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public void setIndexed() {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public void setBaseRelative(boolean isBaseRelative) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public void setPCRelative(boolean isPCRelative) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    public void setSecondOperand(int secondOperand) {
        this.secondOperand = secondOperand;
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT2;
    }

}
