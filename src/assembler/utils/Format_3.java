package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_3 extends Format_2 {

    private boolean isIndirect;
    private boolean isImmediate;
    private boolean isIndexed;
    private boolean isBaseRelative;
    private boolean isPCRelative;

    private StringBuilder builder;

    @Override
    public String toString() {
        // TODO: implement this method
        String objectCode = builder.toString();

        builder.setLength(0);
        return objectCode;
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
