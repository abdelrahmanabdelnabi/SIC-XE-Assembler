package src.parser;

import org.junit.Before;
import org.junit.Test;
import src.misc.Logger;

import static src.misc.Common.fileToString;
import static src.parser.InputReader.InputType;

/**
 * Created by abdelrahman on 4/9/17.
 */
public class ParserTest {
    private final String relativePath = System.getProperty("user.dir");
    private final String testFilePath = relativePath + "/tests/PROG_FORMAT4/PROG_FORMAT4.asm";
    private final String testControlSection = relativePath + "/tests/0_control_section/control_section.asm";
    private final String testControlSectionError = relativePath + "/tests/0_control_section_error/control_section_error.asm";
    private final String testEqu = relativePath + "/tests/0_equ/equ.asm";
    private final String testEquBonus = relativePath + "/tests/0_equ_bonus/equ_bouns.asm";
    private final String testEquError = relativePath + "/tests/0_equ_error/equ_error.asm";

    private String code;
    private Parser parser;
    private InputReader reader;

    @Before
    public void setUp() {
        reader = new InputReader(InputType.String, "");
        parser = new Parser(reader);
    }

    @Test
    public void setTestControlSection() {
        code = fileToString(testControlSection);
        runTest(code);
    }

    @Test
    public void setTestControlSectionError() {
        code = fileToString(testControlSectionError);
        runTest(code);
    }

    @Test
    public void setTestEqu() {
        code = fileToString(testEqu);
        runTest(code);
    }

    @Test
    public void setTestEquBonus() {
        code = fileToString(testEquBonus);
        runTest(code);
    }

    @Test
    public void setTestEquError() {
        code = fileToString(testEquError);
        runTest(code);
    }

    @Test
    public void setGeneralTest() {
        code = fileToString(testFilePath);
        runTest(code);
    }

    private void runTest(String code) {
        Logger.reset();
        reader.setInputString(code);
        parser.parse();
        System.out.println("\n\n" + Logger.getLogString());
    }
}