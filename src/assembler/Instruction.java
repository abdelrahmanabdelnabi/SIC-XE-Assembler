package src.assembler;

import src.assembler.datastructures.Format;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Instruction {
    private String label;
    private String mnemonic;
    private String operand;
    private InstructionType type;
    private int lineNumber;
    private int address;
    private String objectCode = "";
    private boolean hasError = false;
    private boolean hasObject = false;
    private Format format;

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

    public InstructionType getType() {
        return type;
    }

    public void setType(InstructionType type) {
        this.type = type;
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

    public boolean HasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
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

    public void setHasObject(boolean hasObject) {
        this.hasObject = hasObject;
    }

    public enum InstructionType {
        Directive, Instruction
    }
}
