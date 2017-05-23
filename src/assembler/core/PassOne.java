package src.assembler.core;

import src.assembler.ErrorStrings;
import src.assembler.Instruction;
import src.assembler.Logger;
import src.assembler.datastructures.SymbolProp;
import src.assembler.datastructures.*;

import java.util.*;

import static src.assembler.Common.buildErrorString;
import static src.assembler.InstructionPart.*;
import static src.assembler.datastructures.OpcodeTable.*;

/*
 * Created by ahmed on 4/21/17.
 */
class PassOne {
    private final LocationCounter loc = new LocationCounter();
    private final HashMap<String, SymbolProp> symbolTable;
    private final Map<String, InstProp> OPTAB = OpcodeTable.getOpcodeTable();
    private final Set<String> directives = OpcodeTable.getAssemblerDirectivesSet();
    private final HashMap<String, LiteralProp> literalTable;
    private List<Instruction> instructions;
    private int literalCount = 1;
    private int builtLiterals = 1;

    PassOne(List<Instruction> instructions) {
        this.instructions = instructions;
        symbolTable = new HashMap<>();
        literalTable = new LinkedHashMap<>();
    }


    void execute() {
        Logger.Log("Start Pass One");
        checkForSTART(instructions.get(0));

        for (Instruction inst : instructions) {
            inst.setAddress(loc.getCurrentCounterValue());

            // if END, stop
            if (inst.getMnemonic().toUpperCase().equals("END"))
                break;
            handleLabel(inst);
            handleMnemonic(inst);
            handleOperand(inst);
        } // end for loop

        /*
        Handling Remaining Literals
         */
        buildLiterals();

        // Set Program Length
        setProgramLength(loc.getCurrentCounterValue() - OpcodeTable.getStartAddress());
        Logger.Log("End Pass One");
    }

    /**
     * This Method validates operand part of the instruction
     *
     * @param inst , the current fetched instruction
     */
    private void handleOperand(Instruction inst) {
        String operand = inst.getOperand();

        // if no operand found
        if (operand.length() == 0) return;

        // if literal
        if (operand.startsWith("=")) {
            if (isDuplicateLiteral(operand))
                return;
            literalTable.put(operand, new LiteralProp(operand, literalCount++));
        }

    }

    /**
     * @param literal operand
     * @return true if is dup. else, false
     */
    private boolean isDuplicateLiteral(String literal) {
        int literalValue = LiteralProp.calcLiteralValue(literal);
        // Search by value
        for (Map.Entry<String, LiteralProp> cur : literalTable.entrySet())
            if (cur.getValue().getValue() == literalValue)
                return true;

        return false;
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
                symbolTable.put(inst.getLabel(), new SymbolProp(startAddress));
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
                Logger.LogError(error);
                throw new AssemblerException(error);
            } else {
                // insert label in symbol table
                symbolTable.put(label, new SymbolProp(loc.getCurrentCounterValue()));
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
            inst.setInstructionType(Instruction.InstructionType.Instruction);
            inst.setHasObject();

        } else if (OPTAB.containsKey(mnemonic)) {
            inst.setInstructionType(Instruction.InstructionType.Instruction);
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
            inst.setHasObject();
        } else if (directives.contains(mnemonic)) {
            // handle only the Directives that affect the instruction addresses
            inst.setInstructionType(Instruction.InstructionType.Directive);
            switch (mnemonic) {
                case "BYTE":
                    inst.setHasObject();
                    objCodeLength = calcByteDirectiveSize(inst.getOperand());
                    break;
                case "RESB":
                    objCodeLength = Integer.parseInt(inst.getOperand());
                    break;
                case "WORD":
                    inst.setHasObject();
                    objCodeLength = 3;
                    break;
                case "RESW":
                    objCodeLength = Integer.parseInt(inst.getOperand()) * 3;
                    break;
                /*
                 * Handle LTORG, create literals if found
                 */
                case "LTORG":
                    // check for un-build literals
                    buildLiterals();
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

    private void buildLiterals() {
        // For all literals
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Map.Entry<String, LiteralProp> literal : literalTable.entrySet()) {
                // If Not Built !, then build it and increment loc
                if (!literal.getValue().isBuilt() && literal.getValue().getLiteralNumber() == builtLiterals) {
                    builtLiterals++;
                    flag = true;
                    literal.getValue().buildLiteral(loc.getCurrentCounterValue());
                    // FIX
                    // Always Handle Literals as Word
                    loc.increment(3);
                }
            }
        }
    }

    private int calcByteDirectiveSize(String operand) {
        switch (operand.charAt(0)) {
            case 'C':
                return operand.length() - 3;
            case 'X':
                return (operand.length() - 2) / 2;
            default:
                Logger.LogError("Unknown byte directive type !" + operand);
        }
        return 0;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    HashMap<String, SymbolProp> getSymbolTable() {
        return symbolTable;
    }

    HashMap<String, LiteralProp> getLiteralTable() {
        return literalTable;
    }
}
