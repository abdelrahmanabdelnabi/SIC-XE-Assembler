package assembler;

import assembler.datastructures.LocationCounter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Assembler {
    private HashMap<String, SymbolProperties> symbolTable;
    // literal table: hashmap<String, literalProperties>
    LocationCounter loc = new LocationCounter();

    ArrayList<Instruction> instructions;

    public Assembler(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void generatePassOne() throws AssemblerException {
        if (instructions.size() == 0)
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
                String error = buildErrorString(first.getLineNumber(),
                        InstructionPart.OPERAND, ErrorStrings.INVALID_NUMBER_FORMAT);
                throw new AssemblerException(error);
            }
        } else {
            loc.setCurrentCounterValue(0);
        }


        for (Instruction currentInst : instructions) {

            if (currentInst.getMnemonic().equals("end"))
                break;

            String label = currentInst.getLabel();

            if (!label.equals("")) {
                // search symbol table for label
                if (symbolTable.containsKey(label)) {
                    // build error string
                    String error = buildErrorString(currentInst.getLineNumber(),
                            InstructionPart.LABEL, ErrorStrings.LABEL_REDEFINITION);
                    throw new AssemblerException(error);
                } else {
                    // insert label in symbol table
                    symbolTable.put(label, new SymbolProperties(loc.getCurrentCounterValue()));
                }
            }

        }

    }


    private static String buildErrorString(int lineNumber, InstructionPart ip, String error) {
        StringBuilder builder = new StringBuilder();
        builder.append("error in assembling line ").append(lineNumber)
                .append(" in the ").append(ip.toString()).append(" part: ").append(error);

        return builder.toString();
    }

    private enum InstructionPart {
        LABEL, MNEMONIC, OPERAND;
    }
}
