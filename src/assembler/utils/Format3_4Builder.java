package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by abdelrahman on 4/9/17.
 */
public class Format3_4Builder extends AbstractInstructionBuilder{
    private int operand;
    private boolean isIndirect;
    private boolean isImmediate;
    private boolean isIndexed;
    private boolean isBaseRelative;
    private boolean isPCRelative;


    @Override
    public String toString() {
        String objectCode = Integer.toHexString(opCode) + Integer.toHexString(operand);

        opCode = 0;
        operand = 0;
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
        if (this.isBaseRelative && this.isPCRelative)
            this.isPCRelative = false;
    }

    @Override
    public void setPCRelative(boolean isPCRelative) {
        this.isPCRelative = isPCRelative;
        if (this.isPCRelative && this.isBaseRelative)
            this.isBaseRelative = false;
    }

    @Override
    public void setOperand(int operand) {

    }

    @Override
    public Format getFormat() {
        return null;
    }
}