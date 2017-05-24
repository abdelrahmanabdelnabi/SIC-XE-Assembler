package src.assembler.core;

import org.junit.Before;
import org.junit.Test;
import src.parser.InputReader;
import src.parser.LexicalAnalyzer;
import src.parser.Parser;

import static org.junit.Assert.assertEquals;
import static src.misc.Common.fileToString;

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
    private static final String TESTS_DIRECTORY = System.getProperty("user.dir") + "/tests";

    private Parser parser;
    private InputReader reader;
    private String code1;
    private String code2;
    private String code3;
    private String code4;
    private String code5;
    private String addrIndirect;
    private String equate;
    private String controlsec;

    private String correctObjectCode1;
    private String correctObjectCode2;
    private String correctObjectCode3;
    private String correctObjectCode4;
    private String correctObjectCode5;
    private String correctObjectEquate;
    private String correctAddrIndirect;
    private String getCorrectControlsec;

    @Before
    public void setUp() {
        reader = new InputReader(InputReader.InputType.String, "");
        parser = new Parser(reader);

        // read test programs
        code1 = fileToString(TESTS_DIRECTORY + "/code1/code1.asm");
        code2 = fileToString(TESTS_DIRECTORY + "/code2/code2.asm");
        code3 = fileToString(TESTS_DIRECTORY + "/code3/code3.asm");
        code4 = fileToString(TESTS_DIRECTORY + "/code4/code4.asm");
        code5 = fileToString(TESTS_DIRECTORY + "/code5/code5.asm");
        addrIndirect = fileToString(TESTS_DIRECTORY + "/addr-indirect/addr-indirect.asm");
        equate = fileToString(TESTS_DIRECTORY + "/0_equ/equ.asm");
        controlsec = fileToString(TESTS_DIRECTORY + "/0_control_section/control_section.asm");

        correctObjectCode1 = fileToString(TESTS_DIRECTORY + "/code1/code1.obj");
        correctObjectCode2 = fileToString(TESTS_DIRECTORY + "/code2/code2.obj");
        correctObjectCode3 = fileToString(TESTS_DIRECTORY + "/code3/code3.obj");
        correctObjectCode4 = fileToString(TESTS_DIRECTORY + "/code4/code4.obj");
        correctObjectCode5 = fileToString(TESTS_DIRECTORY + "/code5/code5.obj");
        correctAddrIndirect = fileToString(TESTS_DIRECTORY + "/addr-indirect/addr-indirect.obj");
        correctObjectEquate = fileToString(TESTS_DIRECTORY + "/0_equ/equ.obj");
        getCorrectControlsec = fileToString(TESTS_DIRECTORY +
                "/0_control_section/control_section.obj");
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

    @Test
    public void testEquate() {
        reader.setInputString(equate);
        String actual = runAssembler();
        assertEquals("Generated object code does not match the expected code",
                correctObjectEquate, actual);

    }

    @Test
    public void testControlSection() {
        InputReader reader = new InputReader(InputReader.InputType.String, controlsec);
        parser = new Parser(reader);
        parser.parse();
        LexicalAnalyzer analyzer = new LexicalAnalyzer(parser.getParsedInstuctions());
        analyzer.inspectCode();
        MultiProgramAssembler mpa = new MultiProgramAssembler(parser.getParsedInstuctions());

        mpa.executePassOne();
        mpa.executePassTwo();

        String[] codes = mpa.getObjectCode();

        StringBuilder actual = new StringBuilder();
        for(String s : codes) {
            actual.append(s);
            actual.append("\n");
        }

        assertEquals("Generated object code does not match the expected code",
                getCorrectControlsec, actual.toString());

    }

    private String runAssembler() {
        parser.parse();
        LexicalAnalyzer analyzer = new LexicalAnalyzer(parser.getParsedInstuctions());
        analyzer.inspectCode();
        Assembler assembler = new Assembler(parser.getParsedInstuctions());
        assembler.executePassOne();
        assembler.executePassTwo();

        String method1 = assembler.getObjectCode();
        String method2 = assembler.getObjectCode2();

        //assertEquals("two methods of object code generation do not match", method1, method2);

        return method2;
    }

//    private String fileToString(String filePath) {
//        String result = "";
//        try {
//            File file = new File(filePath);
//            FileInputStream fis = new FileInputStream(file);
//            byte[] data = new byte[(int) file.length()];
//            fis.read(data);
//            fis.close();
//
//            result = new String(data, "UTF-8");
//        } catch (FileNotFoundException e) {
//            System.err.println("Can not find file: " + (filePath));
//        } catch (IOException e) {
//            System.err.println("Can not read file: " + filePath);
//        }
//        return result;
//    }

}