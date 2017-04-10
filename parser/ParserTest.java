package parser;

import assembler.datastructures.OpcodeTable;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static parser.InputReader.InputType;

/**
 * Created by abdelrahman on 4/9/17.
 */
public class ParserTest {
    private String path = "/home/ahmed/Desktop/SIC-XE-Assembler/tests/parsingTest.asm";
    private Parser parser;
    private InputReader reader;

    @Before
    public void setUp() {
        reader = new InputReader(InputType.File, path);
        parser = new Parser(reader);
        OpcodeTable.getOpcodeTable();
    }

    @Test
    public void testReader_Parser() {
        parser.parse();
//        for (Instruction inst : parser.getParsedInstuctions()) {
//            System.out.println(inst);
//        }
        System.out.println(Arrays.toString(parser.getParsedInstuctions().toArray()));
    }

}