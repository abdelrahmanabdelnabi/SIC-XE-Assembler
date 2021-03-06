package src.assembler.core;

import src.assembler.datastructures.*;
import src.assembler.utils.Format_2;
import src.assembler.utils.Format_3;
import src.assembler.utils.Format_4;
import src.assembler.utils.ObjectBuilder;
import src.filewriter.ObjectCodeWriter;
import src.misc.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static src.assembler.datastructures.Instruction.InstructionType.Directive;
import static src.assembler.datastructures.Instruction.InstructionType.Instruction;
import static src.assembler.datastructures.InstructionPart.OPERAND;
import static src.assembler.datastructures.OpcodeTable.*;
import static src.assembler.datastructures.OperandType.REGISTER;
import static src.assembler.datastructures.OperandType.VALUE;
import static src.assembler.datastructures.OperandType.VALUE.NUM;
import static src.assembler.datastructures.RegisterTable.getRegisterNumber;
import static src.filewriter.ObjectCodeWriter.PLUS;
import static src.misc.Common.*;
import static src.misc.ErrorStrings.*;

/**
 * Created by ahmed on 4/21/17.
 */
class PassTwo {
    private final HashMap<String, SymbolProp> symbolTable;
    private final List<src.assembler.datastructures.Instruction> instructions;
    private final Map<String, InstProp> OP_TAB = getOpcodeTable();
    private final HashMap<String, LiteralProp> literalsTable;
    private String programName = "";
    private String errorString = "";
    // Flags for Base relative
    private boolean isBaseSet = false;
    private int baseAddress = 0;

    private ObjectCodeWriter ocw;

    PassTwo(List<Instruction> instructions, HashMap<String, SymbolProp> symbolTable
            , HashMap<String, LiteralProp> literalsTable, String programName, int programLength,
            int startAddress) {
        this.programName = programName;
        this.instructions = instructions;
        this.symbolTable = symbolTable;
        this.literalsTable = literalsTable;
        ocw = new ObjectCodeWriter(programName, startAddress,
                programLength);
    }

    void execute() throws AssemblerException {
        Logger.Log("Start Pass Two");
        for (Instruction inst : instructions) {
            ObjectBuilder format2 = new Format_2();
            ObjectBuilder format3 = new Format_3();
            ObjectBuilder format4 = new Format_4();
            /*
             * If is Instruction
             */
            if (inst.getInstructionType() == Instruction) {
                String mnemonic = inst.getMnemonic();
                // FORMAT 4
                if (mnemonic.startsWith("+")) {
                    checkIndexed(inst, format4);
                    checkIndirectImmediate(inst, format4);
                    handleFormat4(inst, format4);
                    String obj;
                    obj = format4.toString();
                    inst.setObjectCode(obj);
                    ocw.appendTextRecord(obj);
                    if (inst.getValueType() != NUM) {
                        String label = getRawOperand(inst.getOperand());
                        String modificationSymbol;

                        if (symbolTable.containsKey(label) && symbolTable.get(label).getType() == SymbolProp.SymbolType.EXTREF) {
                            // if label is an external reference
                            modificationSymbol = label;
                        } else {
                            modificationSymbol = programName;
                        }
                        ocw.addModificationRecord(inst.getAddress() + 1, 5, PLUS, modificationSymbol);
                    }

                } else {
                    Format format = OP_TAB.get(inst.getMnemonic()).getFormat();
                    switch (format) {
                        case FORMAT1:
                            String obj;
                            int opCode = getOpCode(inst.getMnemonic());
                            obj = ObjectBuilder.buildFormatOne(opCode);
                            inst.setObjectCode(obj);
                            ocw.appendTextRecord(obj);
                            break;
                        case FORMAT2:
                            handleFormat2(inst, format2);
                            obj = format2.toString();
                            inst.setObjectCode(obj);
                            ocw.appendTextRecord(obj);
                            break;
                        case FORMAT3:
                            obj = handleFormat3(inst, format3);
                            ocw.appendTextRecord(obj);
                            inst.setObjectCode(obj);
                            break;
                    }
                }
            }
            /*
             * if is assembler directive
             */
            else if (inst.getInstructionType() == Directive) {
                String directive = inst.getMnemonic();
                switch (directive) {
                    case "BASE":
                        // check if label is defined
                        String operand = inst.getOperand();
                        if (!symbolTable.containsKey(operand)) {
                            // if is number
                            if (Pattern.matches("[0-9]+", getRawOperand(operand))) {
                                isBaseSet = true;
                                baseAddress = parseNumOperand(getRawOperand(operand));
                            }
                            // else if not symbol nor number
                            else {
                                errorString = buildErrorString(inst.getLineNumber(), OPERAND,
                                        "Base Value is undefined label or invalid number format");
                                Logger.LogError(errorString);
                                throw new AssemblerException(errorString);
                            }
                        } else {
                            isBaseSet = true;
                            baseAddress = symbolTable.get(operand).getAddress();
                        }

                        break;
                    case "NOBASE":
                        isBaseSet = false;
                        baseAddress = 0;
                        break;
                    case "BYTE":
                        String obj = ObjectBuilder.buildDirectives(inst.getOperand());
                        inst.setObjectCode(obj);
                        ocw.appendTextRecord(obj);
                        break;
                    case "WORD":
                        String s = ObjectBuilder.buildDirectives(inst.getOperand());
                        inst.setObjectCode(s);
                        ocw.appendTextRecord(s);
                        break;
                    case "RESW":
                        // get the new address
                        // and open a new text record at the new address
                        int reservedLength = Integer.parseInt(inst.getOperand()) * 3;
                        ocw.startNewTextRecord(inst.getAddress() + reservedLength);
                        break;
                    case "RESB":
                        reservedLength = Integer.parseInt(inst.getOperand());
                        ocw.startNewTextRecord(inst.getAddress() + reservedLength);
                        break;
                    case "LTORG":
                        fillLiteralPool(inst.getAddress());
                        break;
                    case "END":
                        flushLiteralTable();
                        break;
                    case "CSECT":
                        break;
                    case "EXTREF":
                        // get list of referred symbols and add them to symbol table
                        // with the value 0
                        // also add R record for each symbol
                        String[] symbols = getSymbolList(inst.getOperand());
                        for (String str : symbols) {
                            symbolTable.put(str, new SymbolProp(0, SymbolProp.SymbolType.EXTREF));
                            ocw.appendReferRecord(str);
                        }
                        break;
                    case "EXTDEF":
                        String[] sybmols = getSymbolList(inst.getOperand());
                        for (String str : sybmols) {
                            // check that they are defined in the program
                            if (symbolTable.containsKey(str)) {
                                ocw.appendDefineRecord(str, symbolTable.get(str).getAddress());
                            } else {
                                // error
                                errorString = buildErrorString(inst.getLineNumber(), OPERAND, UNDEFINED_LABEL);
                                Logger.LogError(errorString);
                            }
                        }
                        break;

                }
            }
        }
        Logger.Log("End pass two successfully");
    }

    private void handleFormat2(Instruction inst, ObjectBuilder format_2) {
        String mnemonic = inst.getMnemonic();

        format_2.setOpCode(getOpCode(mnemonic));

        // 1st Operand
        if (getFirstOperandType(mnemonic) == REGISTER) {
            format_2.setOperand(getRegisterNumber(inst.getOperand().split(",")[0]));
        } else if (getFirstOperandType(mnemonic) == VALUE) {
            format_2.setOperand(Integer.parseInt(inst.getOperand().split(",")[0]));
        }

        // 2nd Operand
        if (getSecondOperandType(mnemonic) == REGISTER) {
            format_2.setSecondOperand(getRegisterNumber(inst.getOperand().split(",")[1]));
        } else if (getSecondOperandType(mnemonic) == VALUE) {
            format_2.setSecondOperand(Integer.parseInt(inst.getOperand().split(",")[1]) - 1);
        } else {
            format_2.setSecondOperand(0);
        }

    }

    private String handleFormat3(Instruction inst, ObjectBuilder format3) {
        // prepare needed input
        int PC = inst.getAddress() + 3;
        int opCode = getOpCode(inst.getMnemonic());
        String operand = inst.getOperand();

        // corner case: RSUB doesn't have operands
        if (inst.getMnemonic().equals("RSUB"))
            return format3.setOpCode(opCode).setIndirect(true).setImmediate(true).setOperand(0).toString();


        // Checks if an operand is Valid.. does not account for literals
        // note also that this does NOT allow spaces in the operand
        boolean validLiteral = operand.startsWith("=");//operand.matches("=X'[A-F0-9]+'|=C'[a-zA-Z0-9]+'");
        boolean validOperand =
                operand.matches("([#@]?([a-zA-Z][a-zA-Z0-9]*|-?([0-9]+|(0x)?-?[0-9A-F]+)))|" +
                        "(([a-zA-Z][a-zA-Z0-9]*|-?([0-9]+|(0x)?-?[0-9A-F]+))(,X)?)");

        boolean validFormat = validLiteral || validOperand;

        if (!validFormat) {
            errorString = buildErrorString(inst.getLineNumber(), OPERAND, INVALID_OPERAND_FORMAT);
            Logger.LogError(errorString);
            throw new AssemblerException(errorString);
        }

        // prepare needed flags
        boolean indirect = operand.startsWith("@");
        boolean indexed = operand.endsWith(",X");
        boolean immediate = operand.startsWith("#");
        boolean simple = !(indirect || indexed || immediate);

        String rawOperand = getRawOperand(operand);

        boolean isDecimal = rawOperand.matches("-?[0-9]+");
        boolean isHexaDecimal = rawOperand.matches("(0x-?[0-9A-F]+)");

        int displacement = 0;

        if (!(isDecimal || isHexaDecimal)) {
            if (validOperand && !symbolTable.containsKey(rawOperand)) {
                errorString = buildErrorString(inst.getLineNumber(), OPERAND, UNDEFINED_LABEL);
                Logger.LogError(errorString);
                throw new AssemblerException(inst.toString() + " " + errorString);
            }
        } else { // if number
            // check if it fits in the displacement of a format 3 instruction
            int value;
            if (isDecimal) {
                value = Integer.parseInt(rawOperand);
            } else // hexadecimal
            {
                String hexaNumber = rawOperand.replace("0x", "");
                value = Integer.parseInt(hexaNumber, 16);
            }

            if (!isFitConstant(value)) {
                errorString = buildErrorString(inst.getLineNumber(), OPERAND, DISP_OUT_OF_RANGE);
                Logger.LogError(errorString);
                throw new AssemblerException(errorString);
            }
            displacement = value;
        }

        // set displacement if not a number
        if (!(isDecimal || isHexaDecimal)) {

            int targetAddress;
            if (validLiteral) {
                // get literal address from literal table
                targetAddress = literalsTable.get(operand).getAddress();


            } else { // symbol
                targetAddress = symbolTable.get(rawOperand).getAddress();
            }

            // check range of operand for base and pc relative
            if (isFitPCRelative(targetAddress - PC)) {
                displacement = targetAddress - PC;
                format3.setPCRelative(true);
            } else if (isBaseSet && isFitConstant(targetAddress - baseAddress)) {
                displacement = targetAddress - baseAddress;
                format3.setBaseRelative(true);
            } else {
                // error
                // operand address can not fit into a format 3 instruction
                errorString = buildErrorString(inst.getLineNumber(), OPERAND, DISP_OUT_OF_RANGE);
                Logger.LogError(errorString);
                throw new AssemblerException(errorString);
            }
        }

        format3.setOperand(displacement);
        format3.setOpCode(opCode);

        // according to the last page in the reference operand can either be one of simple
        // or indirect or indexed or immediate

        // set flags
        if (simple) {
            format3.setIndirect(true);
            format3.setImmediate(true);
        } else if (immediate) {
            format3.setIndirect(false);
            format3.setImmediate(true);
        } else if (indexed) {
            format3.setIndirect(true);
            format3.setImmediate(true);
            format3.setIndexed(true);
        } else { // indirect
            format3.setIndirect(true);
            format3.setImmediate(false);
        }

        return format3.toString();
    }

    private void handleFormat4(Instruction inst, ObjectBuilder format4) {
        format4.setOpCode(getOpCode(inst.getMnemonic().substring(1)));
        int TA = getOperandTargetAddress(inst);
        format4.setOperand(TA);
    }

    private void checkIndexed(Instruction inst, ObjectBuilder objectBuilder) {
        if (inst.getOperand().contains(",X")) objectBuilder.setIndexed(true);
    }

    private void checkIndirectImmediate(Instruction inst, ObjectBuilder objectBuilder) {
        String operand = inst.getOperand();
        if (operand.startsWith("@")) objectBuilder.setIndirect(true);
        else if (operand.startsWith("#")) objectBuilder.setImmediate(true);
    }

    private int getOperandTargetAddress(Instruction instruction) {
        String operand = getRawOperand(instruction.getOperand());
        int TA = 0;
        // check is value or symbol
        if (Pattern.matches("[0-9]+|0x[0-9A-F]+", operand)) {
            TA = parseNumOperand(operand);
        } else if (symbolTable.containsKey(operand)) {
            TA = symbolTable.get(operand).getAddress();
        } else {
            errorString = buildErrorString(instruction.getLineNumber(), OPERAND,
                    "Can not calc operand target address, Symbol is not a Label nor EXTREF");
            Logger.LogError(errorString);
        }
        return TA;
    }

    private boolean isFitPCRelative(int displacement) {
        return displacement >= -2048 && displacement <= 2047;
    }

    /* returns true if the number is between 0 and 4095 inclusive */
    private boolean isFitConstant(int number) {
        return number >= 0 && number <= 4095;
    }

    public List<Instruction> getOutputInstructions() {
        return instructions;
    }

    public String getObjectCode() {
        return ocw.getObjectCode();
    }

    private void fillLiteralPool(int poolStartAddress) {

        int expectedAddress = poolStartAddress;
        Iterator<Map.Entry<String, LiteralProp>> iterator = literalsTable.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, LiteralProp> entry = iterator.next();
            int address = entry.getValue().getAddress();
            if (address == expectedAddress) {
                String objectCode = entry.getValue().getObjectCode();
                ocw.appendTextRecord(objectCode);
                expectedAddress += objectCode.length() / 2;
                iterator.remove();
            }
        }
    }

    private void flushLiteralTable() {
        for (Map.Entry<String, LiteralProp> entry : literalsTable.entrySet()) {
            String objectCode = entry.getValue().getObjectCode();
            ocw.appendTextRecord(objectCode);
        }
    }

    private String[] getSymbolList(String listOfSymbols) {
        String[] tokens = listOfSymbols.split(",");

        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim(); // trim extra spaces
        }

        return tokens;
    }

}
