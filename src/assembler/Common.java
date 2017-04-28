package src.assembler;

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

    /**
     * @param operand , takes data Operand  C'EOF'  ||  X'F1'
     * @return its integer value
     */
    public static int parseDataOperand(String operand) {
        String obj = "";
        switch (operand.charAt(0)) {
            case 'X':
                obj = operand.substring(1).replace("'", "");
                break;
            case 'C':
                for (int i = 2; i < operand.length() - 1; i++) {
                    obj += Integer.toHexString(operand.charAt(i));
                }
        }
        return Integer.parseInt(obj, 16);
    }

    /**
     * @param operand "crude"
     * @return RawOperand
     */
    public static String getRawOperand(String operand) {
        // TODO : Add More If Needed
        return operand.replace("C", "").
                replace("X", "").
                replace(",X", "").
                replace("@", "").
                replace("#", "").
                replace("'", "").
                replace("=", "").
                replace(" ", "");
    }

    public static String getOperandWithoutModeFlags(String operand) {
        return operand.replace(" ", "").
                replace("#", "").
                replace("@", "").
                replace("=", "").
                replace(",X", "");
    }
}
