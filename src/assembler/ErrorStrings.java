package src.assembler;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class ErrorStrings {
    public static String INVALID_NUMBER_FORMAT = "Not a legal number";
    public static String LABEL_REDEFINITION = "Label already defined before, label cannot be defined twice";
    public static String UNDEFINED_MNEMONIC = "Undefined nmemonic";
    public static String INVALID_PLUS_SIGN_USE = "Invalid use of plus prefix (+). The (+) prefix can not be used with Format 1 or 2 instructions";
}
