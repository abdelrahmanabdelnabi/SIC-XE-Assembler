package src.assembler.utils;

/**
 * Created by ahmed on 4/21/17.
 */
public class Common {
    public static String extendToLength(String str, int len) {
        StringBuilder zeroes = new StringBuilder();
        for (int i = 0; i < len; i++) zeroes.append("0");
        return (zeroes.toString() + str).substring(str.length());
    }
}
