package src.assembler.core;

import src.assembler.datastructures.*;
import src.assembler.datastructures.SymbolProp.SymbolType;
import src.misc.Logger;

import java.util.*;

import static src.assembler.datastructures.InstructionPart.*;
import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.datastructures.SymbolProp.SymbolType.*;
import static src.misc.Common.*;
import static src.misc.ErrorStrings.*;

/*
 * Created by ahmed on 4/21/17.
 */
class PassOne {
    private final LocationCounter loc = new LocationCounter();
    private final HashMap<String, SymbolProp> symbolTable;
    private final Map<String, InstProp> OPTAB = OpcodeTable.getOpcodeTable();
    private final Set<String> directives = OpcodeTable.getAssemblerDirectivesSet();
    private final HashMap<String, LiteralProp> literalTable;
    private String errorString = "";
    private List<Instruction> instructions;
    private int literalCount = 1;
    private int builtLiterals = 1;
    private boolean orgFlag = false;

    PassOne(List<Instruction> instructions) {
        this.instructions = instructions;
        symbolTable = new HashMap<>();
        literalTable = new LinkedHashMap<>();
    }


    void execute() {
        Logger.Log("Start Pass One");
        START(instructions.get(0));

        for (Instruction inst : instructions) {
            inst.setAddress(loc.getCurrentCounterValue());

            // if END, stop
            if (inst.getMnemonic().toUpperCase().equals("END"))
                break;

            if (!inst.getMnemonic().equals("EQU"))
                handleLabel(inst);

            handleMnemonic(inst);
            handleOperand(inst);
        } // end for loop

        /*
        Handling Remaining Literals
         */
        LTORG();

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

    private void START(Instruction inst) {
        // check for START directive
        int startAddress = 0;

        if (inst.getMnemonic().equals("START")) {
            try {
                startAddress = Integer.parseInt(inst.getOperand(), 16);
            } catch (NumberFormatException e) {
                // build errorString string
                errorString = buildErrorString(inst.getLineNumber(), OPERAND, INVALID_NUMBER_FORMAT);
                Logger.Log(errorString);
                throw new AssemblerException(errorString);
            }
            loc.setCurrentCounterValue(startAddress);

            // if START found then remove it (without changing the original list)
            instructions = instructions.subList(1, instructions.size());

            // if program has a name, then put it in the symbol table
            if (!inst.getLabel().isEmpty())
                symbolTable.put(inst.getLabel(), new SymbolProp(startAddress, RELATIVE));
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
                errorString = buildErrorString(inst.getLineNumber(), LABEL, LABEL_REDEFINITION);
                // log error in log file
                Logger.LogError(errorString);
                throw new AssemblerException(errorString);
            } else {
                // insert label in symbol table
                symbolTable.put(label, new SymbolProp(loc.getCurrentCounterValue(), RELATIVE));
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
                errorString = buildErrorString(inst.getLineNumber(), MNEMONIC, UNDEFINED_MNEMONIC);
                Logger.LogError(errorString);
                throw new AssemblerException(errorString);
            }
            if (OPTAB.get(parsedMnemonic).getFormat() != Format.FORMAT3) {
                // errorString: + sign is used with a instruction that is not format 4
                errorString = buildErrorString(inst.getLineNumber(), MNEMONIC, INVALID_PLUS_SIGN_USE);
                Logger.LogError(errorString);
                throw new AssemblerException(errorString);
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
                    objCodeLength = BYTE(inst.getOperand());
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
                    LTORG();
                    break;


                // TODO :Add ORG, EQU, CSECT, EXTREF, EXTDEF
                case "EQU":
                    EQU(inst);
                    break;

                case "ORG":
                    ORG(inst);
                    break;

                case "CSECT":
                    CSECT(inst);
                    break;

                case "EXTREF":
                    break;

                case "EXTDEF":
                    break;

                default:
                    // do nothing
            }
        } else {
            // neither instruction nor assembler directive
            // error
            errorString = buildErrorString(inst.getLineNumber(), MNEMONIC, UNDEFINED_MNEMONIC);
            Logger.LogError(errorString);
            throw new AssemblerException(errorString);
        }
        // Add inst size to the LOC
        loc.increment(objCodeLength);
    }

    private void CSECT(Instruction inst) {

    }

    private void ORG(Instruction inst) {
        switch (inst.getOperandType()) {
            case NONE:
                if (!orgFlag) {
                    errorString = buildErrorString(inst.getLineNumber(), OPERAND, "First Org occurrence must have an operand");
                    Logger.LogError(errorString);
                    throw new AssemblerException(errorString);
                }
                orgFlag = false;
                loc.orgRestore();
                break;

            case VALUE:
                orgFlag = true;
                switch (inst.getValueType()) {
                    case LOCCTR:
                        loc.orgSet(loc.getCurrentCounterValue());
                        break;

                    case NUM:
                        loc.orgSet(parseNumOperand(inst.getOperand()));
                        break;

                    case LABEL:
                        if (!symbolTable.containsKey(inst.getOperand())) {
                            errorString = buildErrorString(inst.getLineNumber(), OPERAND, UNDEFINED_LABEL);
                            Logger.LogError(errorString);
                            throw new AssemblerException(errorString);
                        }
                        loc.orgSet(symbolTable.get(inst.getOperand()).getAddress());
                        break;

                    case EXPRESSION:
                        break;
                    case DATA:
                        break;
                }
        }
    }

    private void LTORG() {
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

    private int BYTE(String operand) {
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

    private void EQU(Instruction inst) {
        int value = 0;
        SymbolType type = NONE;
        switch (inst.getValueType()) {
            case NUM:
                value = parseNumOperand(inst.getOperand());
                type = ABSOLUTE;
                break;

            case DATA:
                value = parseDataOperand(inst.getOperand());
                type = ABSOLUTE;
                break;

            case LABEL:
                if (!symbolTable.containsKey(inst.getOperand())) {
                    errorString = buildErrorString(inst.getLineNumber(), OPERAND, FORWARD_REFERENCING);
                    Logger.LogError(errorString);
                    throw new AssemblerException(errorString);
                }
                if (symbolTable.containsKey(inst.getLabel())) {
                    errorString = buildErrorString(inst.getLineNumber(), LABEL, LABEL_REDEFINITION);
                    Logger.LogError(errorString);
                }
                value = symbolTable.get(inst.getOperand()).getAddress();
                type = symbolTable.get(inst.getOperand()).getType();
                break;

            case LOCCTR:
                value = loc.getCurrentCounterValue();
                type = orgFlag ? ABSOLUTE : RELATIVE;
                break;

            case EXPRESSION:
                int tmpValue, relCnt = 0, absCnt = 0;
                Boolean errorMultiplyDivide = false;
                String tokens[] = inst.getOperand().split("[-]|[+]");
                String signs;
                StringBuilder signsBuilder = new StringBuilder("+");

                for (int i = 0; i < inst.getOperand().length(); i++)
                    if (inst.getOperand().charAt(i) == '+' || inst.getOperand().charAt(i) == '-')
                        signsBuilder.append(inst.getOperand().charAt(i));
                    else if (inst.getOperand().charAt(i) == '*' || inst.getOperand().charAt(i) == '/')
                        errorMultiplyDivide = true;

                if (errorMultiplyDivide) {
                    errorString = buildErrorString(inst.getLineNumber(), OPERAND, "Multiplication And Division Are Not Allowed in EQU");
                    Logger.LogError(errorString);
                } else {
                    signs = signsBuilder.toString();

                    for (String token : tokens) {
                        try {
                            // if is number
                            tmpValue = Integer.parseInt(token);
                            if (signs.charAt(0) == '+') {
                                absCnt++;
                                value += tmpValue;
                            } else {
                                absCnt--;
                                value -= tmpValue;
                            }
                        }
                        // if is symbol
                        catch (NumberFormatException e) {
                            if (!symbolTable.containsKey(token)) {
                                errorString = buildErrorString(inst.getLineNumber(), OPERAND, FORWARD_REFERENCING);
                                Logger.LogError(errorString);
                                throw new AssemblerException(errorString);
                            }
                            tmpValue = symbolTable.get(token).getAddress();
                            switch (symbolTable.get(token).getType()) {
                                case ABSOLUTE:
                                    if (signs.charAt(0) == '+') {
                                        absCnt++;
                                        value += tmpValue;
                                    } else if (signs.charAt(0) == '-') {
                                        absCnt--;
                                        value -= tmpValue;
                                    }
                                    break;

                                case RELATIVE:
                                    if (signs.charAt(0) == '+') {
                                        relCnt++;
                                        value += tmpValue;
                                    } else if (signs.charAt(0) == '-') {
                                        relCnt--;
                                        value -= tmpValue;
                                    }
                                    break;
                            }
                        }
                        signs = signs.replaceFirst("[-]|[+]", "");
                    }
                    // if added to relative
                    if (absCnt == 0 && relCnt >= 2) {
                        errorString = buildErrorString(inst.getLineNumber(), OPERAND, "Can not Add to relative symbols");
                        Logger.LogError(errorString);
//                    throw new AssemblerException(errorString);
                    }
                    // all terms are absolute or relative in pairs
                    else if (absCnt >= 0 && relCnt == 0) {
                        type = ABSOLUTE;
                    }
                    // all terms are relative in pairs but for 1
                    else if (absCnt == 0 && relCnt == 1) {
                        type = RELATIVE;
                    }
                    // a mix of abs and rel, or 2 relative added, not allowed as far as i get it
                    else
                        type = NONE;
                }
                break;

            default:
                errorString = buildErrorString(inst.getLineNumber(), OPERAND, "Invalid EQU Operand");
                Logger.LogError(errorString);
                throw new AssemblerException(errorString);
        }
        // check duplication
        if (symbolTable.containsKey(inst.getLabel())) {
            errorString = buildErrorString(inst.getLineNumber(), LABEL, LABEL_REDEFINITION);
            Logger.LogError(errorString);
            throw new AssemblerException(errorString);
        }
        // if everything is OK
        symbolTable.put(inst.getLabel(), new SymbolProp(value, type));
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
