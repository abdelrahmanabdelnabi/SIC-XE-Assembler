package src.assembler.utils;

import org.junit.Test;
import src.assembler.Instruction;
import src.assembler.core.Assembler;
import src.assembler.datastructures.OpcodeTable;
import src.parser.InputReader;
import src.parser.Parser;

import static org.junit.Assert.assertEquals;

/**
 * Created by ahmed on 4/12/17.
 */
public class ObjectBuilderTesting {
    private String relativePath = System.getProperty("user.dir");
    private String format1_FilePath = relativePath + "/src/testIn/format1.asm";
    private String format2_FilePath = relativePath + "/src/testIn/format2.asm";
    private String format3_FilePath = relativePath + "/src/testIn/format3.asm";
    private String format4_FilePath = relativePath + "/src/testIn/format4.asm";
    private Parser parser;
    private InputReader reader;

    @Test
    public void testFormat_1() {
        reader = new InputReader(InputReader.InputType.File, format1_FilePath);
        parser = new Parser(reader);
        Run();
    }

    @Test
    public void testFormat_2() {
        reader = new InputReader(InputReader.InputType.File, format2_FilePath);
        parser = new Parser(reader);
        Run();
    }

    @Test
    public void testFormat_3() {
        reader = new InputReader(InputReader.InputType.File, format3_FilePath);
        parser = new Parser(reader);
        Run();
    }

    @Test
    public void testFormat_4() {
        reader = new InputReader(InputReader.InputType.File, format4_FilePath);
        parser = new Parser(reader);
        Run();
    }

    private void Run() {
        Assembler assembler = new Assembler(parser.getParsedInstuctions());
        assembler.executePassOne();
        assembler.executePassTwo();

        System.out.println(assembler.getObjectCode());
    }

    @Test
    public void testFormatFIX() {
        // FIX
        String obj = ObjectBuilder.buildFormatOne(OpcodeTable.getOpCode("FIX"));

        assertEquals("C4", obj);
    }

    @Test
    public void testFLOAT() {
        String obj = ObjectBuilder.buildFormatOne(OpcodeTable.getOpCode("FLOAT"));
        assertEquals("C0", obj);
    }

    @Test
    public void testHIO() {
        String obj = ObjectBuilder.buildFormatOne(OpcodeTable.getOpCode("HIO"));
        assertEquals("F4", obj);
    }
}
