package src.assembler;

import src.assembler.datastructures.Format;
import src.assembler.datastructures.InstProp;
import src.assembler.datastructures.LocationCounter;
import src.assembler.datastructures.OpcodeTable;
import src.assembler.utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by abdelrahman on 3/22/17.
 */

public class Assembler {

    // Instruction Builders
    ArrayList<Instruction> instructions;

    // SYMTAB
    private HashMap<String, SymbolProperties> symbolTable;

    private Set<String> directives = OpcodeTable.getAssemblerDirectivesSet();

    private Map<String, InstProp> OPTAB = OpcodeTable.getOpcodeTable();

    // literal table: hashmap<String, literalProperties>
    private LocationCounter loc = new LocationCounter();

    public Assembler(ArrayList<Instruction> instructions) {
        this.instructions = instructions;
    }

    private static String buildErrorString(int lineNumber, InstructionPart ip, String error) {
        StringBuilder builder = new StringBuilder();
        builder.append("error in assembling line ").append(lineNumber)
                .append(" in the ").append(ip.toString()).append(" part: ").append(error);

        return builder.toString();
    }


    public void executePassOne() throws AssemblerException {
        if (instructions.isEmpty())
            return;

        checkForSTART(instructions.get(0));

        for (Instruction inst : instructions) {
            inst.setAddress(loc.getCurrentCounterValue());

            // if END, stop
            if (inst.getMnemonic().equals("end"))
                break;

            handleLabel(inst);
            handleMnemonic(inst);


        } // end for loop


    }

    private void checkForSTART(Instruction inst) {
        // check for START directive
        if (inst.getMnemonic().equals("START")) {
            try {
                loc.setCurrentCounterValue(Integer.parseInt(inst.getOperand(), 16));
                // if START found then remove it (without changing the original list)
                instructions = new ArrayList<>(instructions.subList(1, instructions.size()));
            } catch (NumberFormatException e) {
                // build error string
                String error = buildErrorString(inst.getLineNumber(), InstructionPart.OPERAND, ErrorStrings.INVALID_NUMBER_FORMAT);
                throw new AssemblerException(error);
            }
        } else {
            loc.setCurrentCounterValue(0);
        }
    }

    private void handleLabel(Instruction inst) {
        String label = inst.getLabel();
        // if there is a symbol in the sybmol field
        if (!label.equals("")) {
            // search symbol table for label
            if (symbolTable.containsKey(label)) {
                // duplicate label error
                // build error string
                String error = buildErrorString(inst.getLineNumber(),
                        InstructionPart.LABEL, ErrorStrings.LABEL_REDEFINITION);
                // log error in log file
                Logger.Log(error);
                throw new AssemblerException(error);
            } else {
                // insert label in symbol table
                symbolTable.put(label, new SymbolProperties(loc.getCurrentCounterValue()));
            }
        }
    }

    private void handleMnemonic(Instruction inst) {
        // Check for Format 4
        int objCodeLength = 0;
        String mnemonic = inst.getMnemonic();
        if (mnemonic.startsWith("+")) {
            objCodeLength = 4;
            String parsedMnemonic = mnemonic.substring(1);
            // Search OPTAB for OPCODE
            if (!OpcodeTable.isOpcode(parsedMnemonic)) {
                // mnemonic not found
                String error = buildErrorString(inst.getLineNumber(),
                        InstructionPart.MNEMONIC, ErrorStrings.UNDEFINED_MNEMONIC);
                Logger.LogError(error);
                throw new AssemblerException(error);
            }
            if (OPTAB.get(parsedMnemonic).getFormat() != Format.FORMAT3) {
                // error: + sign is used with a instruction that is not format 4
                String error = buildErrorString(inst.getLineNumber
                        (), InstructionPart.MNEMONIC, ErrorStrings.INVALID_PLUS_SIGN_USE);
                Logger.LogError(error);
                throw new AssemblerException(error);
            }
            // no errors, update location counter
            loc.increment(4);
            inst.setFormat(Format.FORMAT4);
            inst.setType(Instruction.InstructionType.Instruction);
        } else if (OPTAB.containsKey(mnemonic)) {
            inst.setType(Instruction.InstructionType.Instruction);
            switch (OPTAB.get(mnemonic).getFormat()) {
                case FORMAT1:
                    loc.increment(1);
                    inst.setFormat(Format.FORMAT1);
                    break;
                case FORMAT2:
                    loc.increment(2);
                    inst.setFormat(Format.FORMAT2);
                    break;
                case FORMAT3:
                    loc.increment(3);
                    inst.setFormat(Format.FORMAT3);
            }
        }

        // TODO : Handle directives
        else if (directives.contains(mnemonic)) {
            // handle only the Directives that affect the instruction addresses
            inst.setType(Instruction.InstructionType.Directive);
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
            String error = buildErrorString(inst.getLineNumber(), InstructionPart.MNEMONIC, ErrorStrings.UNDEFINED_MNEMONIC);
            Logger.LogError(error);
            throw new AssemblerException(error);
        }
        // Add inst size to the LOC
        loc.increment(objCodeLength);
    }

    public void executePassTwo() throws AssemblerException {
        // TODO: format 3, 4 & assembler directives
        ObjectBuilder format1 = new Format_1();
        ObjectBuilder format2 = new Format_2();
        ObjectBuilder format3 = new Format_3();
        ObjectBuilder format4 = new Format_4();
        for (Instruction curInst : instructions) {
            /**
             * If is Instruction
             */
//            if (curInst.getType() == Instruction.InstructionType.Instruction) {
            Format format = OPTAB.get(curInst.getMnemonic()).getFormat();
            if (curInst.getType() == Instruction.InstructionType.Instruction) {
                switch (format) {
                    case FORMAT1:
                        format1.setInstruction(curInst);
                        curInst.setObjectCode(format1.toString());
                        break;
                    case FORMAT2:
                        format2.setInstruction(curInst);
                        curInst.setObjectCode(format2.toString());
                        break;
                    case FORMAT3:
                        format3.setInstruction(curInst);
                        curInst.setObjectCode(format3.toString());
                        break;
                    case FORMAT4:
                        format4.setInstruction(curInst);
                        curInst.setObjectCode(format4.toString());
                    default:
                        break;
                }
            }
            /**
             * if is assembler directive
             */
            else if (curInst.getType() == Instruction.InstructionType.Directive) {

            }
        }
    }


    private enum InstructionPart {
        LABEL, MNEMONIC, OPERAND
    }
}
