package assembler;

import assembler.datastructures.LocationCounter;

import java.util.ArrayList;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Assembler {
    // symbol table: hashmap<String, symbolProperties>
    // literal table: hashmap<String, literalProperties>
    LocationCounter loc = new LocationCounter();

    ArrayList<Instruction> instructions;

    public Assembler(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void generatePassOne() {
        Instruction first = instructions.get(0);
        if(first.getMnemonic().equals("START")) {
            loc.setCurrentCounterValue(Integer.parseInt(first.getOperand()));
        }

    }
}
