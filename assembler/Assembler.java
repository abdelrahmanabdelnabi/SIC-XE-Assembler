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

    public void generatePassOne() throws AssemblerException {
        if(instructions.size() == 0)
            return;

        // check for START directive
        Instruction first = instructions.get(0);
        if (first.getMnemonic().equals("START")) {
            try {
                // TODO: Check the base for the START operand decimal/hexadecimal
                loc.setCurrentCounterValue(Integer.parseInt(first.getOperand(), 10));
            } catch (NumberFormatException e) {
                // build error string
                String error = "error in line " + first.getLineNumber() + ". ";
                error += "in Operand of " + first.getMnemonic() + ": " + ErrorStrings.INVALID_NUMBER_FORMAT;
                throw new AssemblerException(error);
            }
        }

        for(int i = 1; i < instructions.size(); i++) {
            Instruction currentInst = instructions.get(i);

            
        }

    }
}
