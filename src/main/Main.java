package src.main;

import src.assembler.Logger;
import src.assembler.core.Assembler;
import src.assembler.core.AssemblerException;
import src.filewriter.ListingString;
import src.filewriter.SymbolsString;
import src.filewriter.Writer;
import src.parser.InputReader;
import src.parser.Parser;
import src.parser.ParsingException;

/*
 * Created by abdelrahman on 4/9/17.
 */
/*
    TODO : Test addr-immediate  Passed
    TODO : Test addr-indirect   Failed // Base/Pc Issue
    TODO : Test addr-simple     Failed // Base Relative Issue
    TODO : Test base
    TODO : Test Code1
    TODO : Test Code2
    TODO : Test Code3
    TODO : Test Code4
    TODO : Test Code5
    TODO : Test Code6
    TODO : Test Code7
    TODO : Test Format1
    TODO : Test Format2
    TODO : Test Format3
    TODO : Test Format4
    TODO : Test Literals
    TODO : Test-Storage
 */
public class Main {
    public static void main(String[] args) {

        // take argument from command line
        // String path = args[0];
        String relativePath = System.getProperty("user.dir");

        // change file path to change test file
        String path = relativePath + "/src/tests/base/base.asm";

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

        // check for errors
        String errorFile = path.replace(".asm", "_log.txt");
        String symTab = path.replace(".asm", "_symTab.txt");
        String lstTab = path.replace(".asm", "_LstFile.txt");
        String objectFile = path.replace(".asm", "_obj.obj");

        // Symbols
        writer.setFileName(symTab);
        writer.writeToFile(new SymbolsString(assembler.getSymbolTable()).toString());

        // object
        writer.setFileName(objectFile);
        writer.writeToFile(assembler.getObjectCode());

        // abo fayez table
        writer.setFileName(lstTab);
        writer.writeToFile(new ListingString(assembler.getInstructions()).toString());

        // Log file
        writer.setFileName(errorFile);
        writer.writeToFile(Logger.getLogString());
    }
}
