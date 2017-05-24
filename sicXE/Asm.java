package sicXE;

/*
 * Created by abdelrahman on 4/9/17.
 */

import src.assembler.core.AssemblerException;
import src.assembler.core.MultiProgramAssembler;
import src.assembler.datastructures.LiteralProp;
import src.assembler.datastructures.SymbolProp;
import src.filewriter.ListingString;
import src.filewriter.SymbolsString;
import src.filewriter.Writer;
import src.misc.Logger;
import src.parser.InputReader;
import src.parser.LexicalAnalyzer;
import src.parser.Parser;
import src.parser.ParsingException;

import java.io.File;
import java.util.List;
import java.util.Map;

class Asm {
    private static MultiProgramAssembler assembler;
    private static Writer writer = new Writer("");

    public static void main(String[] args) {
//         Uncomment to take args from Command Line
//        String relativePath = System.getProperty("user.dir") + "/" + args[0];
//        String absolutePath = args[0];
//        String filePath = lockFile(relativePath, absolutePath);

        // change filePath to change test file
        String relativePath = System.getProperty("user.dir");// + "/" + args[0];
        String filePath = relativePath + "/tests/0_equ_bonus/equ_bouns.asm";

        if (filePath.length() != 0) {

            // create file reader
            InputReader reader = new InputReader(InputReader.InputType.File, filePath);

            // create parser
            Parser parser = new Parser(reader);
            // parse file
            try {
                parser.parse();
            } catch (ParsingException pe) {
                // TODO: output errors to stdout as well as log file
                System.out.println("Parsing Error: " + pe.getMessage());
            }


            // Validate Syntax Check
            LexicalAnalyzer analyzer = new LexicalAnalyzer(parser.getParsedInstuctions());
            analyzer.inspectCode();

            // check if no syntax errors found
            if (Logger.getErrorsCnt() == 0) {
                // continue assembling file
                assembler = new MultiProgramAssembler(parser.getParsedInstuctions());
                try {
                    assembler.executePassOne();
                    assembler.executePassTwo();
                } catch (AssemblerException ae) {
                    // TODO: output errors to stdout as well as log file
                    System.out.println("Assembling Error: " + ae.getMessage());
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }

            // end assembling and out Log file
            String errorFile = filePath.replace(".asm", "_log.txt");
            String symTabFile = filePath.replace(".asm", "_symTab.txt");
            String lstTabFile = filePath.replace(".asm", "_LstFile.txt");
            String objectFile = filePath.replace(".asm", "_obj.txt");

        /* if there's no errors found, write data to files
                else write only log file */

            if (Logger.getErrorsCnt() == 0) {
                // Symbols
                writer.setFileName(symTabFile);

                String tables = "";

                List<Map<String, SymbolProp>> symbolsTables = assembler.getSymbolTables();
                List<Map<String, LiteralProp>> literalsTables = assembler.getLiteralsTables();
                for(int i = 0; i < symbolsTables.size(); i++) {
                    tables += new SymbolsString(symbolsTables.get(i), literalsTables.get(i))
                            .toString();
                }

                writer.writeToFile(tables);

                // object
                writer.setFileName(objectFile);
                String[] codes = assembler.getObjectCode();

                StringBuilder output = new StringBuilder();
                for(String s : codes) {
                    output.append(s);
                    output.append("\n");
                }

                writer.writeToFile(output.toString());

                // abo listing table
                writer.setFileName(lstTabFile);
                writer.writeToFile(new ListingString(parser.getParsedInstuctions()).toString());
            }
            // Log file
            writer.setFileName(errorFile);
            writer.writeToFile(Logger.getLogString());
        }
    }

    private static String lockFile(String relativePath, String absolutePath) {
        File file = new File(relativePath);
        if (file.exists() && !file.isDirectory()) {
            return relativePath;
        }
        file = new File(absolutePath);
        if (file.exists() && !file.isDirectory()) {
            return absolutePath;
        }
        Logger.LogError("Can not Lock file, check if file exists at the given path");
        return "";
    }
}