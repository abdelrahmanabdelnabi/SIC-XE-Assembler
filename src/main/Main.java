package src.main;

/*
 * Created by abdelrahman on 4/9/17.
 */

import src.assembler.Logger;
import src.assembler.core.Assembler;
import src.assembler.core.AssemblerException;
import src.filewriter.ListingString;
import src.filewriter.SymbolsString;
import src.filewriter.Writer;
import src.parser.InputReader;
import src.parser.LexicalAnalyzer;
import src.parser.Parser;
import src.parser.ParsingException;

public class Main {
    public static void main(String[] args) {

        // take argument from command line
        // String path = args[0];
        String relativePath = System.getProperty("user.dir");

        // change file path to change test file
        String path = relativePath + "/src/tests/code4/code4.asm";

        InputReader reader = new InputReader(InputReader.InputType.File, path);
        Parser parser = new Parser(reader);

        try {
            parser.parse();
        } catch (ParsingException pe) {
            // TODO: output errors to stdout as well as log file
            System.out.println("Parsing Error: " + pe.getMessage());
        }

        /*
            Validate Syntax Check
         */
        LexicalAnalyzer analyser = new LexicalAnalyzer(parser.getParsedInstuctions());
        analyser.inspectCode();

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
        String errorFile = path.replace(".asm", "_log.txt");
        String symTabFile = path.replace(".asm", "_symTab.txt");
        String lstTabFile = path.replace(".asm", "_LstFile.txt");
        String objectFile = path.replace(".asm", "_obj.obj");

        /* if there's no error write data to files
                else write only log file */

        if (Logger.getErrorsCnt() == 0) {
            // Symbols
            writer.setFileName(symTabFile);
            writer.writeToFile(new SymbolsString(assembler.getSymbolTable(), assembler.getLiteralsTable()).toString());

            // object
            writer.setFileName(objectFile);
            writer.writeToFile(assembler.getObjectCode());

            // abo fayez table
            writer.setFileName(lstTabFile);
            writer.writeToFile(new ListingString(assembler.getInstructions()).toString());
        }
        // Log file
        writer.setFileName(errorFile);
        writer.writeToFile(Logger.getLogString());
    }
}