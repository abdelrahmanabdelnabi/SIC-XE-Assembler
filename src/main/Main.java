package src.main;

import src.assembler.Assembler;
import src.assembler.Logger;
import src.filewriter.FormAboFayezString;
import src.filewriter.FormObjectString;
import src.filewriter.FormSymbolsString;
import src.filewriter.Writer;
import src.parser.InputReader;
import src.parser.Parser;

/*
 * Created by abdelrahman on 4/9/17.
 */
public class Main {
    public static void main(String[] args) {
        // take argument from command line
//        String path = args[0];
        String path = "/home/ahmed/Data/Workspace/IdeaProjects/SIC-XE-Assembler/src/testIn/format2.asm";

        InputReader reader = new InputReader(InputReader.InputType.File, path);
        Parser parser = new Parser(reader);
        Assembler assembler = new Assembler(parser.getParsedInstuctions());

        assembler.executePassOne();
        assembler.executePassTwo();

        path = path.replace("testIn", "testOut");

        // check for errors
        if (Logger.getErrorsCnt() == 0) {
            String symTab = path.replace(".asm", "_symTab.txt");
            String aboFayezTab = path.replace(".asm", "_aboFayezTable.txt");
            String objectFile = path.replace(".asm", ".obj");
            //
            Writer writer = new Writer("");
            // Symbols
            writer.setFileName(symTab);
            writer.toFile(new FormSymbolsString(assembler.getSymbolTable()).toString());
            // object
            writer.setFileName(objectFile);
            writer.toFile(new FormObjectString(assembler.instructions).toString());
            // abo fayez table
            writer.setFileName(aboFayezTab);
            writer.toFile(new FormAboFayezString(assembler.instructions).toString());

        } else {
            Writer writer = new Writer("");
            String errorFile = path.replace(".asm", "_log.txt");
            writer.setFileName(errorFile);
            writer.toFile(Logger.getLogString());
        }
    }
}
