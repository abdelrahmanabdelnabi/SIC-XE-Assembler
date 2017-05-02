package sicXE.parser;

import org.junit.Before;
import org.junit.Test;
import sicXE.assembler.Logger;
import sicXE.filewriter.Writer;

import static sicXE.parser.InputReader.InputType.File;


/*
 * Created by ahmed on 4/28/17.
 */
public class LexicalAnalyzerTest {
    private final String relativePath = System.getProperty("user.dir");
    private final String testFilePath = "/sicXE/tests/format4/format4.asm";
    private Parser parser;

    @Before
    public void setUp() {
        InputReader inputReader = new InputReader(File, relativePath + testFilePath);
        parser = new Parser(inputReader);
        parser.parse();
    }

    @Test
    public void testAnalyser() {
        LexicalAnalyzer analyser = new LexicalAnalyzer(parser.getParsedInstuctions());
        analyser.inspectCode();
        Writer writer = new Writer(relativePath);
        writer.setFileName(testFilePath.replace(".asm", "_log.txt"));
        writer.writeToFile(Logger.getLogString());
    }
}
