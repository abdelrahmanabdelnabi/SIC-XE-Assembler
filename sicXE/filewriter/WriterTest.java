package sicXE.filewriter;

import org.junit.Test;
import sicXE.assembler.core.Assembler;
import sicXE.parser.InputReader;
import sicXE.parser.Parser;

/*
 * Created by ahmed on 4/19/17.
 */
public class WriterTest {
    private final String relativePath = System.getProperty("user.dir");
    private final String format1_FilePath = relativePath + "/sicXE/testIn/format1.asm";
    // --Commented out by Inspection (5/1/17 3:49 AM):private String format2_FilePath = relativePath + "/sicXE/testIn/format2.asm";
    // --Commented out by Inspection (5/1/17 3:49 AM):private String format3_FilePath = relativePath + "/sicXE/testIn/format3.asm";
    // --Commented out by Inspection (5/1/17 3:49 AM):private String format4_FilePath = relativePath + "/sicXE/testIn/format4.asm";
    private Parser parser;

    @Test
    public void testFormat_1() {
        InputReader reader = new InputReader(InputReader.InputType.File, format1_FilePath);
        parser = new Parser(reader);
        Run();
    }

//    @Test
//    public void testFormat_2() {
//        reader = new InputReader(InputReader.InputType.File, format2_FilePath);
//        parser = new Parser(reader);
//        Run();
//    }

    private void Run() {
        Assembler assembler = new Assembler(parser.getParsedInstuctions());

        assembler.executePassOne();
        assembler.executePassTwo();

        System.out.println(assembler.getObjectCode());

        Writer writer = new Writer(relativePath + "/sicXE/testOut/");

        writer.setFileName("format1_obj.txt");
        writer.writeToFile(assembler.getObjectCode());

        writer.setFileName("format1_symTab.txt");
        StringGenerator symbolsString = new SymbolsString(assembler.getSymbolTable(), assembler.getLiteralsTable());
        writer.writeToFile(symbolsString.toString());

        writer.setFileName("format1_LstFile.txt");
        StringGenerator aboFayezString = new ListingString(assembler.getInstructions());
        writer.writeToFile(aboFayezString.toString());
    }
}
