package src.assembler;

import src.assembler.datastructures.Format;
import src.assembler.datastructures.InstProp;
import src.assembler.datastructures.LocationCounter;
import src.assembler.datastructures.OpcodeTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static src.assembler.InstructionPart.*;
import static src.assembler.datastructures.OpcodeTable.*;

/*
 * Created by ahmed on 4/21/17.
 */
public class PassOne {
    private List<Instruction> instructions;
    private LocationCounter loc = new LocationCounter();
    private HashMap<String, SymbolProperties> symbolTable;
    private Map<String, InstProp> OPTAB = OpcodeTable.getOpcodeTable();
    private Set<String> directives = OpcodeTable.getAssemblerDirectivesSet();

    PassOne(List<Instruction> instructions) {
        this.instructions = instructions;
        symbolTable = new HashMap<>();
    }

    private static String buildErrorString(int lineNumber, InstructionPart ip, String error) {
        return "error in assembling line " + lineNumber + " in the " + ip.toString() + " part: " + error;
    }

    public void execute() {
        Logger.Log("Start Pass One");
        checkForSTART(instructions.get(0));

        for (Instruction inst : instructions) {
            inst.setAddress(loc.getCurrentCounterValue());

            // if END, stop
            if (inst.getMnemonic().equals("end"))
                break;
            handleLabel(inst);
            handleMnemonic(inst);
        } // end for loop

        setProgramLength(loc.getCurrentCounterValue() - OpcodeTable.getStartAddress());
        Logger.Log("End Pass One");
    }

    private void checkForSTART(Instruction inst) {
        // check for START directive
        int startAddress = 0;

        if (inst.getMnemonic().equals("START")) {


            try {
                startAddress = Integer.parseInt(inst.getOperand(), 16);
            } catch (NumberFormatException e) {
                // build error string
                String error = buildErrorString(inst.getLineNumber(), OPERAND, ErrorStrings.INVALID_NUMBER_FORMAT);
                Logger.Log(error);
                throw new AssemblerException(error);
            }

            loc.setCurrentCounterValue(startAddress);

            // if START found then remove it (without changing the original list)
            instructions = instructions.subList(1, instructions.size());

            // if program has a name, then put it in the symbol table
            if (!inst.getLabel().isEmpty())
                symbolTable.put(inst.getLabel(), new SymbolProperties(startAddress));
            setProgramName(inst.getLabel());
            setStartAddress(loc.getCurrentCounterValue());
        }
        loc.setCurrentCounterValue(startAddress);
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
                        LABEL, ErrorStrings.LABEL_REDEFINITION);
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

        int objCodeLength = 0;
        String mnemonic = inst.getMnemonic();
        inst.setAddress(loc.getCurrentCounterValue());

        // Check for Format 4
        if (mnemonic.startsWith("+")) {
            objCodeLength = 4;
            String parsedMnemonic = mnemonic.substring(1);

            // Search OPTAB for OPCODE
            if (!OpcodeTable.isOpcode(parsedMnemonic)) {
                // mnemonic not found
                String error = buildErrorString(inst.getLineNumber(),
                        MNEMONIC, ErrorStrings.UNDEFINED_MNEMONIC);
                Logger.LogError(error);
                throw new AssemblerException(error);
            }
            if (OPTAB.get(parsedMnemonic).getFormat() != Format.FORMAT3) {
                // error: + sign is used with a instruction that is not format 4
                String error = buildErrorString(inst.getLineNumber
                        (), MNEMONIC, ErrorStrings.INVALID_PLUS_SIGN_USE);
                Logger.LogError(error);
                throw new AssemblerException(error);
            }
            inst.setFormat(Format.FORMAT4);
            inst.setType(Instruction.InstructionType.Instruction);
            inst.setHasObject(true);

        } else if (OPTAB.containsKey(mnemonic)) {
            inst.setType(Instruction.InstructionType.Instruction);
            switch (OPTAB.get(mnemonic).getFormat()) {
                case FORMAT1:
                    objCodeLength = 1;
                    inst.setFormat(Format.FORMAT1);
                    break;
                case FORMAT2:
                    objCodeLength = 2;
                    inst.setFormat(Format.FORMAT2);
                    break;
                case FORMAT3:
                    objCodeLength = 3;
                    inst.setFormat(Format.FORMAT3);
            }
            inst.setHasObject(true);
        } else if (directives.contains(mnemonic)) {
            // TODO : Handle directives
            // handle only the Directives that affect the instruction addresses
            inst.setType(Instruction.InstructionType.Directive);
            switch (mnemonic) {
                case "BYTE":
                    objCodeLength = 1;
                    break;
                case "RESB":
                    objCodeLength = Integer.parseInt(inst.getOperand());
                    break;
                case "WORD":
                    objCodeLength = 3;
                    break;
                case "RESW":
                    objCodeLength = Integer.parseInt(inst.getOperand()) * 3;
                    break;
            }
        } else {
            // neither instruction nor assembler directive
            // error
            String error = buildErrorString(inst.getLineNumber(), MNEMONIC, ErrorStrings.UNDEFINED_MNEMONIC);
            Logger.LogError(error);
            throw new AssemblerException(error);
        }
        // Add inst size to the LOC
        loc.increment(objCodeLength);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public HashMap<String, SymbolProperties> getSymbolTable() {
        return symbolTable;
    }
}
