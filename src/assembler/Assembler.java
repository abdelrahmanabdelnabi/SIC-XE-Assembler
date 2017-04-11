package src.assembler;

import src.assembler.datastructures.Format;
import src.assembler.datastructures.InstProp;
import src.assembler.datastructures.LocationCounter;
import src.assembler.datastructures.OpcodeTable;
import src.assembler.utils.AbstractInstructionBuilder;
import src.assembler.utils.Format3_4Builder;
import src.assembler.utils.FormatTwoBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private Set<String> directives = OpcodeTable.getAssemblerDirectivesSet();

    private Map<String, InstProp> OPTAB = OpcodeTable.getOpcodeTable();

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

    public void executePassOne() throws AssemblerException {
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

            String mnemonic = currentInst.getMnemonic();

            if (mnemonic.startsWith("+")) {
                objCodeLength = 4;
                String parsedMnemonic = mnemonic.substring(1);

                // Search OPTAB for OPCODE
                if (!OpcodeTable.isOpcode(parsedMnemonic)) {
                    // mnemonic not found
                    String error = buildErrorString(currentInst.getLineNumber(),
                            InstructionPart.MNEMONIC, ErrorStrings.UNDEFINED_MNEMONIC);

                    Logger.LogError(error);
                    throw new AssemblerException(error);
                }

                if (OPTAB.get(parsedMnemonic).getFormat() != Format.FORMAT3_4) {
                    // error: + sign is used with a instruction that is not format 3
                    String error = buildErrorString(currentInst.getLineNumber
                            (), InstructionPart.MNEMONIC, ErrorStrings.INVALID_PLUS_SIGN_USE);

                    Logger.LogError(error);

                    throw new AssemblerException(error);
                }

                // no errors, update location counter
                loc.increment(4);

            } else if(OPTAB.containsKey(mnemonic)) {

                switch (OPTAB.get(mnemonic).getFormat()) {
                    case FORMAT1:
                        loc.increment(1);
                        break;
                    case FORMAT2:
                        loc.increment(2);
                        break;
                    case FORMAT3_4:
                        loc.increment(3);
                }
            } else if(directives.contains(mnemonic)) {
                // handle only the Directives that affect the instruction addresses

                switch (mnemonic) {
                    case "BYTE":
                        break;
                    case "RESB":
                        break;
                    case "WORD":
                        break;
                    case "RESW":
                        break;
                }

            } else {
                // neither instruction nor assembler directive
                // error
                String error = buildErrorString(currentInst.getLineNumber(), InstructionPart
                        .MNEMONIC, ErrorStrings.UNDEFINED_MNEMONIC);

                Logger.LogError(error);

                throw new AssemblerException(error);
            }

        } // end for loop


    }


    public void executePassTwo() throws AssemblerException {
        // TODO: implement this method
    }


    private enum InstructionPart {
        LABEL, MNEMONIC, OPERAND
    }
}
