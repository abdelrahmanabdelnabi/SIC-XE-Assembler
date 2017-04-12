package src.assembler;

import org.junit.Before;
import org.junit.Test;
import src.parser.InputReader;
import src.parser.Parser;

/**
 * Created by ahmed on 4/12/17.
 */
public class InstructionFormatTesting {
    private String relativePath = System.getProperty("user.dir");
    private String testFormat1_FilePath = relativePath + "/src/tests/format1.asm";
    private String testFormat2_FilePath = relativePath + "/src/tests/format2.asm";
    private String testFormat3_FilePath = relativePath + "/src/tests/format3.asm";
    private String testFormat4_FilePath = relativePath + "/src/tests/format4.asm";
    private Parser parser;
    private InputReader reader;
    private Assembler assembler;

    @Before
    public void setUp() {
        reader = new InputReader(InputReader.InputType.File, testFormat1_FilePath);
        parser = new Parser(reader);
    }

    @Test
    public void testFormat_1() {
        assembler = new Assembler(parser.getParsedInstuctions());
        assembler.executePassOne();
        assembler.executePassTwo();
        for (Instruction inst : assembler.instructions) {
            System.out.println(inst.getObjectCode());
        }
    }
}
