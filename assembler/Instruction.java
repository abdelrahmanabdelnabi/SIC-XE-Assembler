package assembler;

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

    public enum InstructionType {
        Directive, Instruction
    }
}
