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

    public static String buildDirectives(String operand) {
        switch (operand.charAt(0)) {
            case 'X':
                return operand.substring(1).replace("'", "");
            case 'C':
                String objectCode = "";
                operand = operand.substring(1).replace("'", "");
                for (int i = 0; i < operand.length(); i++) {
                    objectCode += Integer.toHexString(operand.charAt(i));
                }
                return objectCode;
            default:
                return "";
        }
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
