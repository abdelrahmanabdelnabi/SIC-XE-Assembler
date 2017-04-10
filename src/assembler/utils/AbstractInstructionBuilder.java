package src.assembler.utils;

/**
 * Created by abdelrahman on 4/9/17.
 */

import src.assembler.datastructures.Format;

/**
 * incrementally builds the hexadecimal object code of an instruction.
 * it takes the opCode and operand, and flags and returns hexadecimal object code
 * of the instruction as a string
 */
public abstract class AbstractInstructionBuilder {
    // the op code of the instruction in decimal
    protected int opCode;

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    /**
     * Returns a Hexadecimal string representation of the instruction built
     * @return the instruction built in hexadecimal
     */
    @Override
    public abstract String toString();

    public abstract void setIndirect(boolean isIndirect);
    public abstract void setImmediate(boolean isImmediate);
    public abstract void setIndexed(boolean isIndirect);
    public abstract void setBaseRelative(boolean isBaseRelative);
    public abstract void setPCRelative(boolean isPCRelative);

    public abstract void setOperand(int operand);
    public abstract Format getFormat();
}
