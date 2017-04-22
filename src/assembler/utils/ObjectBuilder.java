package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public abstract class ObjectBuilder {
    // the op code of the instruction in decimal
    int opCode;
    int operand;
    String objectCode;

    public static String buildFormatOne(int opCode) {
        return Integer.toHexString(opCode);
    }

    /**
     * Returns a Hexadecimal string representation of the instruction built
     *
     * @return the instruction built in hexadecimal
     */
    @Override
    public abstract String toString();

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public abstract void setIndirect(boolean isIndirect);

    public abstract void setImmediate(boolean isImmediate);

    public abstract void setIndexed(boolean isIndexed);

    public abstract void setBaseRelative(boolean isBaseRelative);

    public abstract void setPCRelative(boolean isPCRelative);

    public abstract void setOperand(int operand);

    public abstract void setSecondOperand(int secondOperand);

    public abstract Format getFormat();
}
