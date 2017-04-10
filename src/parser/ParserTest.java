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
    private String relativePath = System.getProperty("user.dir");
    private String testFilePath = relativePath + "/src/tests/parsingTest.asm";
    private Parser parser;
    private InputReader reader;

    @Before
    public void setUp() {
        reader = new InputReader(InputType.File, testFilePath);
        parser = new Parser(reader);
    }

    @Test
    public void testReader_Parser() {
        parser.parse();
        System.out.println(Arrays.toString(parser.getParsedInstuctions().toArray()));
    }

}