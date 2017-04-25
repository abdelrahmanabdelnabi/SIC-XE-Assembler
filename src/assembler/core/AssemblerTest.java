package src.assembler.core;

import org.junit.Before;
import org.junit.Test;
import src.parser.InputReader;
import src.parser.Parser;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by abdelrahman on 4/25/17.
 * <p>
 * Integration Tests
 */
public class AssemblerTest {
    private Parser parser;
    private InputReader reader;
    private Assembler assembler;

    private static final String TESTS_DIRECTORY = System.getProperty("user.dir") + "/src/tests";

    private String code1;
    private String code2;
    private String code3;

    private String correctObjectCode1;
    private String correctObjectCode2;
    private String correctObjectCode3;

    @Before
    public void setUp() {
        reader = new InputReader(InputReader.InputType.String, "");
        parser = new Parser(reader);

        // read test programs
        code1 = readFile(TESTS_DIRECTORY + "/code1/code1.asm");
        code2 = readFile(TESTS_DIRECTORY + "/code2/code2.asm");
        code3 = readFile(TESTS_DIRECTORY + "/code3/code3.asm");
        correctObjectCode1 = readFile(TESTS_DIRECTORY + "/code1/code1.obj");
        correctObjectCode2 = readFile(TESTS_DIRECTORY + "/code2/code2.obj");
        correctObjectCode3 = readFile(TESTS_DIRECTORY + "/code3/code3.obj");
    }

    @Test
    public void testCode1() {
        reader.setInputString(code1);
        parser.parse();
        assembler = new Assembler(parser.getParsedInstuctions());
        assembler.executePassOne();
        assembler.executePassTwo();

        String actual = assembler.getObjectCode();

        assertEquals("Generated object code does not match the expected code", correctObjectCode1, actual);
    }

    @Test
    public void testCode3() {
        reader.setInputString(code3);
        parser.parse();
        assembler = new Assembler(parser.getParsedInstuctions());
        assembler.executePassOne();
        assembler.executePassTwo();

        String actual = assembler.getObjectCode();

        assertEquals("Generated object code does not match the expected code", correctObjectCode3, actual);
    }
    private String readFile(String filePath) {
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