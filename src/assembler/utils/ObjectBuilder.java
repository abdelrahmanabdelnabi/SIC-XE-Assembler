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

    public ObjectBuilder setOpCode(int opCode) {
        this.opCode = opCode;
        return this;
    }

    public abstract ObjectBuilder setIndirect(boolean isIndirect);

    public abstract ObjectBuilder setImmediate(boolean isImmediate);

    public abstract ObjectBuilder setIndexed(boolean isIndexed);

    public abstract ObjectBuilder setBaseRelative(boolean isBaseRelative);

    public abstract ObjectBuilder setPCRelative(boolean isPCRelative);

    public ObjectBuilder setOperand(int operand) {
        this.operand = operand;
        return this;
    }

    public abstract ObjectBuilder setSecondOperand(int secondOperand);

    public abstract Format getFormat();

    void resetFields() {
        opCode = 0;
        operand = 0;
    }
}
