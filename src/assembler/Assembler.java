package src.assembler;

import java.util.HashMap;
import java.util.List;

/**
 * Created by abdelrahman on 3/22/17.
 */

public class Assembler {
    // Instructions
    private List<Instruction> inputInstructions;
    private PassOne passOne;
    private PassTwo passTwo;

    public Assembler(List<Instruction> inputInstructions) {
        this.inputInstructions = inputInstructions;
    }

    public static String extendToLength(String str, int len) {
        StringBuilder zeroes = new StringBuilder();

        for (int i = 0; i < len; i++) zeroes.append("0");

        return (zeroes.toString() + str).substring(str.length());
    }

    public void executePassOne() throws AssemblerException {
        passOne = new PassOne(inputInstructions);
        passOne.execute();
    }

    public void executePassTwo() throws AssemblerException {
        passTwo = new PassTwo(passOne.getInstructions(), passOne.getSymbolTable());
        passTwo.execute();
    }

    public HashMap<String, SymbolProperties> getSymbolTable() {
        return passOne.getSymbolTable();
    }

    public List<Instruction> getInstructions() {
        return passTwo.getOutputInstructions();
    }
}
