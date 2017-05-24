package src.assembler.core;

import src.assembler.datastructures.Instruction;
import src.assembler.datastructures.LiteralProp;
import src.assembler.datastructures.SymbolProp;
import src.filewriter.ObjectString;

import java.util.HashMap;
import java.util.List;

/**
 * Created by abdelrahman on 3/22/17.
 */

public class Assembler {
    // Instructions
    private final List<Instruction> inputInstructions;
    private PassOne passOne;
    private PassTwo passTwo;
    private ObjectString objectCodeGenerator;

    public Assembler(List<Instruction> inputInstructions) {
        this.inputInstructions = inputInstructions;
    }

    public void executePassOne() throws AssemblerException {
        passOne = new PassOne(inputInstructions);
        passOne.execute();
    }

    public void executePassTwo() throws AssemblerException {
        passTwo = new PassTwo(passOne.getInstructions(), passOne.getSymbolTable(), passOne
                .getLiteralTable(), passOne.getProgramName(), passOne.getProgramLength(), passOne
                .getStartAddress());
        passTwo.execute();
        // Bug fix , literals at start is null
        // moved this from constructor
        this.objectCodeGenerator = new ObjectString(inputInstructions, getLiteralsTable());
    }

    public String getObjectCode() {
        return objectCodeGenerator.toString();
    }

    public String getObjectCode2() {
        return passTwo.getObjectCode();
    }

    public HashMap<String, SymbolProp> getSymbolTable() {
        return passOne.getSymbolTable();
    }

    public HashMap<String, LiteralProp> getLiteralsTable() {
        return passOne.getLiteralTable();
    }

    public List<Instruction> getInstructions() {
        return passTwo.getOutputInstructions();
    }
}
