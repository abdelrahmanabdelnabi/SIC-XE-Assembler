package src.assembler;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Created by abdelrahman on 4/21/17.
 */
public class RegexTest {

    static String onlyLetters = "labEL";

    static String inDirect = "@LABEL";

    static String immediate = "#Label";

    @Test
    public void testOperandIsLabel() {

        assertEquals(onlyLetters.matches("[a-zA-Z]+"), true);
        assertEquals(inDirect.matches("[a-zA-Z]+"), false);
        assertEquals(immediate.matches("[a-zA-Z]+"), false);

    }

    @Test
    public void testOperandIsInDirect() {

        assertEquals(inDirect.matches("@[a-zA-Z]+"), true);
        assertEquals(onlyLetters.matches("@[a-zA-Z]+"), false);
        assertEquals(immediate.matches("@[a-zA-Z]+"), false);

    }

    @Test
    public void testOperandIsImmediate() {
        boolean matches = immediate.matches("#[a-zA-Z]+");

        assertEquals(immediate.matches("#[a-zA-Z]+"), true);
        assertEquals(onlyLetters.matches("#[a-zA-Z]+"), false);
        assertEquals(inDirect.matches("#[a-zA-Z]+"), false);
    }

}
