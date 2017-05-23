package src.misc;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class ErrorStrings {
    public static final String INVALID_NUMBER_FORMAT = "Not a legal number";
    public static final String LABEL_REDEFINITION = "Label already defined before, label cannot be defined twice";
    //    public static String UNDEFINED_MNEMONIC = "Undefined nmemonic";
    public static final String INVALID_PLUS_SIGN_USE = "Invalid use of plus prefix (+). The (+) prefix" +
            " can not be used with Format 1 or 2 instructions";
    public static final String UNDEFINED_MNEMONIC = "mnemonic is neither a SIC, SIC/XE or and an " +
            "assembler directive";

    public static final String UNDEFINED_LABEL = "Undefined label";

    public static final String DISP_OUT_OF_RANGE = "The displacement relative to the program counter or" +
            " the base address are out of range";

    public static final String INVALID_OPERAND_FORMAT = "Operand format is invalid";
}
