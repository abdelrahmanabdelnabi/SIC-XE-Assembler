package src.parser;

import org.junit.Before;
import org.junit.Test;
import src.assembler.datastructures.OpcodeTable;

import java.util.Arrays;

import static src.parser.InputReader.InputType;

/**
 * Created by abdelrahman on 4/9/17.
 */
public class ParserTest {
    private String path = "/home/ahmed/Desktop/SIC-XE-Assembler/src.tests/parsingTest.asm";
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
//        for (Instruction inst : src.parser.getParsedInstuctions()) {
//            System.out.println(inst);
//        }
        System.out.println(Arrays.toString(parser.getParsedInstuctions().toArray()));
    }

}