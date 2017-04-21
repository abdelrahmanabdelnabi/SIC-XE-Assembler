package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_2 extends ObjectBuilder {
    protected int operand;
    private int secondOperand;

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02X", opCode));
        builder.append(String.format("%01X", operand));
        builder.append(String.format("%01X", secondOperand));

        // TODO: reset values to default

        return builder.toString();
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
    public void setIndexed(boolean isIndexed) {
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
//        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public void setSecondOperand(int secondOperand) {
        this.secondOperand = secondOperand;
//        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT2;
    }

}
