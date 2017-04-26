package src.assembler.core;

import src.assembler.Instruction;
import src.assembler.SymbolProperties;
import src.assembler.datastructures.LiteralProp;
import src.filewriter.ObjectString;

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
    private ObjectString objectCodeGenerator;

    public Assembler(List<Instruction> inputInstructions) {
        this.inputInstructions = inputInstructions;
        this.objectCodeGenerator = new ObjectString(inputInstructions);
    }

    public void executePassOne() throws AssemblerException {
        passOne = new PassOne(inputInstructions);
        passOne.execute();
    }

    public void executePassTwo() throws AssemblerException {
        passTwo = new PassTwo(passOne.getInstructions(), passOne.getSymbolTable());
        passTwo.execute();
    }

    public String getObjectCode() {
        return objectCodeGenerator.toString();
    }

    public HashMap<String, SymbolProperties> getSymbolTable() {
        return passOne.getSymbolTable();
    }

    public HashMap<String, LiteralProp> getLiteralsTable() {
        return passOne.getLiteralTable();
    }

    public List<Instruction> getInstructions() {
        return passTwo.getOutputInstructions();
    }
}
