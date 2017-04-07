package assembler;

import java.util.ArrayList;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Assembler {
    // symbol table: hashmap<String, symbolProperties>
    // literal table: hashmap<String, literalProperties>

    int locationCounter;
    ArrayList<Instruction> instructions;

    public Assembler(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void generatePassOne() {

    }
}
