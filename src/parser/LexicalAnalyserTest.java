package src.parser;

import org.junit.Before;
import org.junit.Test;
import src.assembler.Logger;
import src.filewriter.Writer;

import static src.parser.InputReader.InputType.File;


/*
 * Created by ahmed on 4/28/17.
 */
public class LexicalAnalyserTest {
    private String relativePath = System.getProperty("user.dir");
    private String testFilePath = "/src/tests/format4/format4.asm";
    private Parser parser;

    @Before
    public void setUp() {
        InputReader inputReader = new InputReader(File, relativePath + testFilePath);
        parser = new Parser(inputReader);
        parser.parse();
    }

    @Test
    public void testAnalyser() {
        LexicalAnalyser analyser = new LexicalAnalyser(parser.getParsedInstuctions());
        analyser.inspectCode();
        Writer writer = new Writer(relativePath);
        writer.setFileName(testFilePath.replace(".asm", "_log.txt"));
        writer.writeToFile(Logger.getLogString());
    }
}
