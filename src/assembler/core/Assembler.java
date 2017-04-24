package src.assembler.core;

import src.assembler.Instruction;
import src.assembler.SymbolProperties;

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
