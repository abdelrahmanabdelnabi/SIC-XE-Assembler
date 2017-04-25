package src.main;

import src.assembler.core.Assembler;
import src.assembler.core.AssemblerException;
import src.assembler.Logger;
import src.filewriter.ListingString;
import src.filewriter.ObjectString;
import src.filewriter.SymbolsString;
import src.filewriter.Writer;
import src.parser.InputReader;
import src.parser.Parser;
import src.parser.ParsingException;

/*
 * Created by abdelrahman on 4/9/17.
 */
public class Main {
    public static void main(String[] args) {

        // take argument from command line
        // String path = args[0];
        String relativePath = System.getProperty("user.dir");

        String path = relativePath + "/src/testCodes/addr-indirect/addr-indirect.asm";

        InputReader reader = new InputReader(InputReader.InputType.File, path);
        Parser parser = new Parser(reader);

        try {
            parser.parse();
        } catch (ParsingException pe) {
            // TODO: output errors to stdout as well as log file
            System.out.println("Parsing Error: " + pe.getMessage());
        }

        Assembler assembler = new Assembler(parser.getParsedInstuctions());

        try {
            assembler.executePassOne();
            assembler.executePassTwo();

        } catch (AssemblerException ae) {
            // TODO: output errors to stdout as well as log file
            System.out.println("Assembling Error: " + ae.getMessage());

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        Writer writer = new Writer("");

        path = path.replace("testIn", "testOut");

        // check for errors
        String errorFile = path.replace(".asm", "_log.txt");
        String symTab = path.replace(".asm", "_symTab.txt");
        String aboFayezTab = path.replace(".asm", "_LstFile.txt");
        String objectFile = path.replace(".asm", "_obj.obj");

        // Symbols
        writer.setFileName(symTab);
        writer.writeToFile(new SymbolsString(assembler.getSymbolTable()).toString());

        // object
        writer.setFileName(objectFile);
        writer.writeToFile(assembler.getObjectCode());

        // abo fayez table
        writer.setFileName(aboFayezTab);
        writer.writeToFile(new ListingString(assembler.getInstructions()).toString());

        // Log file
        writer.setFileName(errorFile);
        writer.writeToFile(Logger.getLogString());
    }
}
