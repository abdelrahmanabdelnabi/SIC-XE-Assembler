package assembler;

import assembler.datastructures.LocationCounter;

import java.util.ArrayList;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Assembler {
    private hashmap<String, symbolProperties> symbolTable;
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

                // if START found then remove it (without changing the original list)
                instructions = new ArrayList<Instruction>(instructions.subList(1, instructions.size()));
            } catch (NumberFormatException e) {
                // build error string
                String error = "error in line " + first.getLineNumber() + ". ";
                error += "in Operand of " + first.getMnemonic() + ": " + ErrorStrings.INVALID_NUMBER_FORMAT;
                throw new AssemblerException(error);
            }
        } else {
            loc.setCurrentCounterValue(0);
        }


        for(Instruction currentInst : instructions) {

            if(currentInst.getMnemonic().equals("end"))
                break;




        }

    }
}
