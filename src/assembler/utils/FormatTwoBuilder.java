package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by abdelrahman on 4/9/17.
 */
public class FormatTwoBuilder extends AbstractInstructionBuilder{
    private int operand;

    @Override
    public String toString() {
        // TODO: implement this method
        return null;
    }

    @Override
    public void setIndirect(boolean isIndirect) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setImmediate(boolean isImmediate) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setIndexed(boolean isIndirect) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setBaseRelative(boolean isBaseRelative) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setPCRelative(boolean isPCRelative) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT2;
    }
}
