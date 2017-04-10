package src.assembler;

import src.assembler.datastructures.LocationCounter;
import src.assembler.utils.AbstractInstructionBuilder;
import src.assembler.utils.Format3_4Builder;
import src.assembler.utils.FormatTwoBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abdelrahman on 3/22/17.
 */

public class Assembler {

    // Instruction Builders
    AbstractInstructionBuilder format2Builder;
    AbstractInstructionBuilder format3_4Builder;
    ArrayList<Instruction> instructions;

    // SYMTAB
    private HashMap<String, SymbolProperties> symbolTable;

    // literal table: hashmap<String, literalProperties>
    private LocationCounter loc = new LocationCounter();

    public Assembler(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
        format3_4Builder = new Format3_4Builder();
        format2Builder = new FormatTwoBuilder();
    }

    private static String buildErrorString(int lineNumber, InstructionPart ip, String error) {
        StringBuilder builder = new StringBuilder();
        builder.append("error in assembling line ").append(lineNumber)
                .append(" in the ").append(ip.toString()).append(" part: ").append(error);

        return builder.toString();
    }

    public void generatePassOne() throws AssemblerException {
        if (instructions.size() == 0)
            return;

        // check for START directive
        Instruction first = instructions.get(0);
        if (first.getMnemonic().equals("START")) {
            try {
                // TODO: Check the base for the START operand decimal/hexadecimal
                // DONE
                loc.setCurrentCounterValue(Integer.parseInt(first.getOperand(), 16));

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


            // if END, stop
            if (currentInst.getMnemonic().equals("end"))
                break;

            String label = currentInst.getLabel();

            // if there is a symbol in the sybmol field
            if (!label.equals("")) {

                // search symbol table for label
                if (symbolTable.containsKey(label)) {

                    // duplicate label error
                    // build error string
                    String error = buildErrorString(currentInst.getLineNumber(),
                            InstructionPart.LABEL, ErrorStrings.LABEL_REDEFINITION);

                    // log error in log file
                    Logger.Log(error);

                    throw new AssemblerException(error);


                } else {
                    // insert label in symbol table
                    symbolTable.put(label, new SymbolProperties(loc.getCurrentCounterValue()));
                }
            }

            // Parse OPCODE
            // Check for Format 4
            int objCodeLength = 0;

            if(currentInst.getMnemonic().startsWith("+")) {
                objCodeLength = 4;

                String parsedMnemonic = currentInst.getMnemonic().substring(1);

                // Search OPTAB for OPCODE
                //if()

            }



        }

    }


    public void generatePassTwo() {

    }


    private enum InstructionPart {
        LABEL, MNEMONIC, OPERAND
    }
}
