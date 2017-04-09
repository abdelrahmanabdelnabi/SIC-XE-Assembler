package main;

import assembler.Assembler;
import parser.InputReader;
import parser.Parser;

/**
 * Created by abdelrahman on 4/9/17.
 */
public class Main {
    public static void main(String[] args) {
        String path = "";
        InputReader reader = new InputReader(InputReader.InputType.File, path);
        Parser parser = new Parser(reader);
        parser.parse();

        Assembler assembler = new Assembler(parser.getParsedInstuctions());

        assembler.generatePassOne();
        // assembler.generatePassTwo();
    }
}
