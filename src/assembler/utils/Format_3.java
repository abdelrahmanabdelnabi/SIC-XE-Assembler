package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_3 extends ObjectBuilder {
    // TODO: implement ME
    protected int operand;
    private int secondOperand;
    private boolean isIndirect;
    private boolean isImmediate;
    private boolean isIndexed;
    private boolean isBaseRelative;
    private boolean isPCRelative;

    @Override
    public String toString() {
        return null;
    }

    @Override
    void setIndirect(boolean isIndirect) {
        this.isIndirect = isIndirect;

    }

    @Override
    void setImmediate(boolean isImmediate) {
        this.isImmediate = isImmediate;

    }

    @Override
    void setIndexed(boolean isIndexed) {
        this.isIndexed = isIndexed;

    }

    @Override
    void setBaseRelative(boolean isBaseRelative) {
        this.isBaseRelative = isBaseRelative;

    }

    @Override
    void setPCRelative(boolean isPCRelative) {
        this.isPCRelative = isPCRelative;

    }

    @Override
    void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    void setSecondOperand(int secondOperand) {
        throw new UnsupportedOperationException("Format 3 and 4 do not have a second operand");
    }

    @Override
    Format getFormat() {
        return null;
    }
}
