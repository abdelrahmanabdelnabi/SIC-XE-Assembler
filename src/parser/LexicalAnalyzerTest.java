package src.parser;

import org.junit.Before;
import org.junit.Test;
import src.assembler.Logger;
import src.filewriter.Writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/*
 * Created by ahmed on 4/28/17.
 */
public class LexicalAnalyzerTest {
    private String code = "";
    private String relativePath = System.getProperty("user.dir");
    private String test_Control_Section = "tests/0_control_section/control_section.asm";
    private String test_Control_Section_Error = "tests/0_control_section_error/control_section_error.asm";
    private String test_equ = "tests/0_equ/equ.asm";
    private String test_equ_bonus = "tests/0_equ_bonus/equ_bouns.asm";
    private String test_equ_error = "tests/0_equ_error/equ_error.asm";
    private String testFilePath = "/tests/format4/format4.asm";
    private Parser parser;
    private InputReader inputReader;

    @Before
    public void setUp() {
        inputReader = new InputReader(InputReader.InputType.String, "");
        parser = new Parser(inputReader);
    }

    @Test
    public void setTest_Control_Section() {
        code = loadFile(test_Control_Section);
        runTest(code);
    }

    @Test
    public void setTest_Control_Section_Error() {
        code = loadFile(test_Control_Section_Error);
        runTest(code);
    }

    @Test
    public void setTest_equ() {
        code = loadFile(test_equ);
        runTest(code);
    }

    @Test
    public void setTest_equ_bonus() {
        code = loadFile(test_equ_bonus);
        runTest(code);
    }

    @Test
    public void setTest_equ_error() {
        code = loadFile(test_equ_error);
        runTest(code);
    }

    @Test
    public void setCodeTest() {
        code = loadFile(testFilePath);
        runTest(code);
    }

    public void runTest(String code) {
        inputReader.setInputString(code);
        parser.parse();

        LexicalAnalyzer analyser = new LexicalAnalyzer(parser.getParsedInstuctions());
        analyser.inspectCode();

        Writer writer = new Writer(relativePath);
        writer.setFileName(testFilePath.replace(".asm", "_log.txt"));
        writer.writeToFile(Logger.getLogString());
    }

    private String loadFile(String filePath) {
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
