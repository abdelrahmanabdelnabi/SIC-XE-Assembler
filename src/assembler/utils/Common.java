package src.assembler.utils;

import src.assembler.InstructionPart;

import java.util.regex.Pattern;

/**
 * Created by ahmed on 4/21/17.
 */
public class Common {
    public static String extendToLength(String str, int len) {
        StringBuilder zeroes = new StringBuilder();
        for (int i = 0; i < len; i++) zeroes.append("0");
        return (zeroes.toString() + str).substring(str.length());
    }

    public static String buildErrorString(int lineNumber, InstructionPart ip, String error) {
        return "error in assembling line " + lineNumber + " in the " + ip.toString() + " part: " + error;
    }

    /**
     * @param operand Takes operand String if it's a number ( 123 || 0x123 )
     * @return value , the operand's Integer Value
     */
    public static int parseNumOperand(String operand) {
        int value = 0;
        // if is Hex
        if (Pattern.matches("0x[0-9]+", operand))
            value = Integer.parseInt(operand.substring(2), 16);
        // if is decimal
        if (Pattern.matches("[0-9]+", operand))
            value = Integer.parseInt(operand);
        return value;
    }
}
