package src.assembler;

import src.assembler.datastructures.Format;
import src.assembler.datastructures.OperandType;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Instruction {
    private String label;
    private String mnemonic;
    private String operand;
    // Classification
    private InstructionType instructionType;
    private Format format;
    private OperandType operandType;
    private OperandType.VALUE valueType;
    // Place
    private int lineNumber;
    private int address;
    // ObjectCode
    private boolean hasObject = false;
    private String objectCode = "";

    public Instruction(String label, String mnemonic, String operand, int lineNumber) {
        this.label = label;
        this.mnemonic = mnemonic;
        this.operand = operand;
        this.lineNumber = lineNumber;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getOperand() {
        return operand;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(InstructionType instructionType) {
        this.instructionType = instructionType;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s %-10s\n", label, mnemonic, operand);
    }

    public String getObjectCode() {
        return objectCode.toUpperCase();
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public boolean getHasObject() {
        return hasObject;
    }

    public void setHasObject() {
        this.hasObject = true;
    }

    public OperandType getOperandType() {
        return operandType;
    }

    public void setOperandType(OperandType operandType) {
        this.operandType = operandType;
    }

    public OperandType.VALUE getValueType() {
        return valueType;
    }

    public void setValueType(OperandType.VALUE valueType) {
        this.valueType = valueType;
    }

    public enum InstructionType {
        Directive, Instruction
    }
}
