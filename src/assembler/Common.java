package src.assembler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        return "Error line: " + lineNumber + ", Invalid " + ip.toString() + ", Message: " + error;
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
        if (operand.startsWith("X"))
            obj = operand.substring(1).replace("'", "");

        else if (operand.startsWith("C"))
            for (int i = 2; i < operand.length() - 1; i++)
                obj += Integer.toHexString(operand.charAt(i));

        else if (Pattern.matches("0x[0-9A-F]+", operand))
            obj = operand.substring(2);

        return Integer.parseInt(obj, 16);
    }


    /**
     * @param operand "crude"
     * @return RawOperand
     */
    public static String getRawOperand(String operand) {
        // TODO : Add More If Needed
        return operand.replace(" ", "").
                replace(",X", "").
                replace("@", "").
                replace("#", "").
                replace("=", "");
    }

    /**
     * @param filePath String
     * @return file content as a string
     */
    public static String fileToString(String filePath) {
        String result = "";
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            result = new String(data, "UTF-8");
        } catch (FileNotFoundException e) {
            System.err.println("Can not find file: " + (filePath));
        } catch (IOException e) {
            System.err.println("Can not read file: " + filePath);
        }
        return result;
    }
}
