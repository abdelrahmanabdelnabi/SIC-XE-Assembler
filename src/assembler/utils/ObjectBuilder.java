package src.assembler.utils;

import src.assembler.datastructures.Format;

import java.util.regex.Pattern;

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
        String objectCode = "";
        if (Pattern.matches("C'[a-zA-Z0-9]+'", operand)) {
            operand = operand.substring(1).replace("'", "");
            StringBuilder objectCodeBuilder = new StringBuilder();
            for (int i = 0; i < operand.length(); i++) {
                objectCodeBuilder.append(Integer.toHexString(operand.charAt(i)).toUpperCase());
            }
            objectCode = objectCodeBuilder.toString();
        } else if (Pattern.matches("X'[A-F0-9]+'", operand)) {
            objectCode = operand.substring(1).replace("'", "");
        } else if (Pattern.matches("0x[0-9A-F]+", operand)) {
            objectCode = operand.replace("0x", "");
        } else if (Pattern.matches("[0-9]+", operand)) {
            objectCode = Integer.toHexString(Integer.parseInt(operand)).toUpperCase();
        }

//        switch (operand.charAt(0)) {
//            case 'X':
//                return operand.substring(1).replace("'", "");
//            case 'C':
//                String objectCode = "";
//                operand = operand.substring(1).replace("'", "");
//                for (int i = 0; i < operand.length(); i++) {
//                    objectCode += Integer.toHexString(operand.charAt(i));
//                }
//                return objectCode;
//            default:
//                return "";
//        }
        return objectCode;
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
