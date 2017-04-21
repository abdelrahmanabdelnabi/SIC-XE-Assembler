package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public abstract class ObjectBuilder {
    // the op code of the instruction in decimal
    int opCode;

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

    public abstract void setIndirect();

    public abstract void setImmediate();

    public abstract void setIndexed();

    public abstract void setBaseRelative();

    public abstract void setPCRelative();

    public abstract void setOperand(int operand);

    public abstract void setSecondOperand(int secondOperand);

    public abstract Format getFormat();
}
