package src.parser;

import org.junit.Before;
import org.junit.Test;
import src.assembler.Logger;
import src.filewriter.Writer;

import static src.assembler.Common.fileToString;


/*
 * Created by ahmed on 4/28/17.
 */
public class LexicalAnalyzerTest {
    private String code = "";
    private String relativePath = System.getProperty("user.dir");
    private String test_Control_Section = relativePath + "/tests/0_control_section/control_section.asm";
    private String test_Control_Section_Error = relativePath + "/tests/0_control_section_error/control_section_error.asm";
    private String test_equ = relativePath + "/tests/0_equ/equ.asm";
    private String test_equ_bonus = relativePath + "/tests/0_equ_bonus/equ_bouns.asm";
    private String test_equ_error = relativePath + "/tests/0_equ_error/equ_error.asm";
    private String testGeneralFile = relativePath + "path to some random shit";
    private String testFilePath = "";// relativePath + "/tests/format4/format4.asm";
    private Parser parser;
    private InputReader inputReader;

    @Before
    public void setUp() {
        inputReader = new InputReader(InputReader.InputType.String, "");
        parser = new Parser(inputReader);
    }

    @Test
    public void setTest_Control_Section() {
        code = fileToString(test_Control_Section);
        testFilePath = test_Control_Section;
        runTest(code);
    }

    @Test
    public void setTest_Control_Section_Error() {
        code = fileToString(test_Control_Section_Error);
        testFilePath = test_Control_Section_Error;
        runTest(code);
    }

    @Test
    public void setTest_equ() {
        code = fileToString(test_equ);
        testFilePath = test_equ;
        runTest(code);
    }

    @Test
    public void setTest_equ_bonus() {
        code = fileToString(test_equ_bonus);
        testFilePath = test_equ_bonus;
        runTest(code);
    }

    @Test
    public void setTest_equ_error() {
        code = fileToString(test_equ_error);
        testFilePath = test_equ_error;
        runTest(code);
    }

//    @Test
//    public void setTestGeneralFile() {
//        code = fileToString(testGeneralFile);
//        runTest(code);
//    }

    private void runTest(String code) {
        Logger.reset();

        inputReader.setInputString(code);
        parser.parse();

        LexicalAnalyzer analyser = new LexicalAnalyzer(parser.getParsedInstuctions());
        analyser.inspectCode();

        Writer writer = new Writer("");
        writer.setFileName(testFilePath.replace(".asm", "_log.txt"));
        writer.writeToFile(Logger.getLogString());
    }

}
