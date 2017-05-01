package src.assembler.core;

import org.junit.Before;
import org.junit.Test;
import src.parser.InputReader;
import src.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by abdelrahman on 4/25/17.
 * <p>
 * Integration Tests
 * <p>
 * code1: WORD directives, format 2 and 3, SHIFT, no literals
 * code2:
 * code3: Lecture code (has format 4, modification records, and no literals)
 * code4: Same as code3 but uses literals
 * code7: Uses Literals, expressions, and code blocks
 * code5: Literals only
 * code7: SIC program -- should not test
 */
public class AssemblerTest {
    private static final String TESTS_DIRECTORY = System.getProperty("user.dir") + "/src/tests";
    private Parser parser;
    private InputReader reader;
    private String code1;
    private String code2;
    private String code3;
    private String code4;
    private String code5;
    private String addrImm;
    private String addrIndirect;

    private String correctObjectCode1;
    private String correctObjectCode2;
    private String correctObjectCode3;
    private String correctObjectCode4;
    private String correctObjectCode5;
    private String correctAddrImm;
    private String correctAddrIndirect;

    @Before
    public void setUp() {
        reader = new InputReader(InputReader.InputType.String, "");
        parser = new Parser(reader);

        // read test programs
        code1 = readFile(TESTS_DIRECTORY + "/code1/code1.asm");
        code2 = readFile(TESTS_DIRECTORY + "/code2/code2.asm");
        code3 = readFile(TESTS_DIRECTORY + "/code3/code3.asm");
        code4 = readFile(TESTS_DIRECTORY + "/code4/code4.asm");
        code5 = readFile(TESTS_DIRECTORY + "/code5/code5.asm");
        addrImm = readFile(TESTS_DIRECTORY + "/addr-immediate/addr-immediate.asm");
        addrIndirect = readFile(TESTS_DIRECTORY + "/addr-indirect/addr-indirect.asm");
        correctObjectCode1 = readFile(TESTS_DIRECTORY + "/code1/code1.obj");
        correctObjectCode2 = readFile(TESTS_DIRECTORY + "/code2/code2.obj");
        correctObjectCode3 = readFile(TESTS_DIRECTORY + "/code3/code3.obj");
        correctObjectCode4 = readFile(TESTS_DIRECTORY + "/code4/code4.obj");
        correctObjectCode5 = readFile(TESTS_DIRECTORY + "/code5/code5.obj");
        correctAddrImm = readFile(TESTS_DIRECTORY + "/addr-immediate/addr-immediate.obj");
        correctAddrIndirect = readFile(TESTS_DIRECTORY + "/addr-indirect/addr-indirect.obj");
    }


    @Test
    public void testCode1() {
        // ORIGINAL CODE IS FAULTY
        reader.setInputString(code1);
        String actual = runAssembler();
        assertEquals("Generated object code does not match the expected code", correctObjectCode1, actual);
    }

    @Test
    public void testCode2() throws Exception {
        reader.setInputString(code2);
        String actual = runAssembler();
        assertEquals("Generated object code does not match the expected code", correctObjectCode2, actual);
    }

    @Test
    public void testCode3() {
        reader.setInputString(code3);
        String actual = runAssembler();
        assertEquals("Generated object code does not match the expected code", correctObjectCode3, actual);
    }

    @Test
    public void testCode4() throws Exception {
        reader.setInputString(code4);
        String actual = runAssembler();
        assertEquals("Generated object code does not match the expected code", correctObjectCode4, actual);
    }

    @Test
    public void testCode5() throws Exception {
        reader.setInputString(code5);
        String actual = runAssembler();
        assertEquals("Generated object code does not match the expected code", correctObjectCode5, actual);
    }

    @Test
    public void testIndirectAddressing() throws Exception {
        reader.setInputString(addrIndirect);
        String actual = runAssembler();
        assertEquals("Generated object code does not match the expected code", correctAddrIndirect, actual);
    }

    private String runAssembler() {
        parser.parse();
        Assembler assembler = new Assembler(parser.getParsedInstuctions());
        assembler.executePassOne();
        assembler.executePassTwo();

        return assembler.getObjectCode();
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