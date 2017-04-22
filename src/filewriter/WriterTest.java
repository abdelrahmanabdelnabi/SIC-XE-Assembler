package src.filewriter;

import org.junit.Test;
import src.assembler.Assembler;
import src.assembler.Instruction;
import src.parser.InputReader;
import src.parser.Parser;

/**
 * Created by ahmed on 4/19/17.
 */
public class WriterTest {
    private String relativePath = System.getProperty("user.dir");
    private String format1_FilePath = relativePath + "/src/testIn/format1.asm";
    private String format2_FilePath = relativePath + "/src/testIn/format2.asm";
    private String format3_FilePath = relativePath + "/src/testIn/format3.asm";
    private String format4_FilePath = relativePath + "/src/testIn/format4.asm";
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

        for (Instruction inst : assembler.getInstructions()) {
            System.out.println(inst.getObjectCode());
        }
        Writer writer = new Writer(relativePath + "/src/testOut/");

        writer.setFileName("format1.obj");
        StringGenerator objectString = new ObjectString(assembler.getInstructions());
        writer.writeToFile(objectString.toString());

        writer.setFileName("format1_symTab.txt");
        StringGenerator symbolsString = new SymbolsString(assembler.getSymbolTable());
        writer.writeToFile(symbolsString.toString());

        writer.setFileName("format1_LstFile.txt");
        StringGenerator aboFayezString = new ListingString(assembler.getInstructions());
        writer.writeToFile(aboFayezString.toString());
    }
}
