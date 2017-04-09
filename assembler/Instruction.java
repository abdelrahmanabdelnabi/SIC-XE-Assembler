package assembler;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Instruction {
    private String label;
    private String mnemonic;
    private String operand;
    private InstrcutionType type;
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

    public void setType(InstrcutionType type) {
        this.type = type;
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

    public InstrcutionType getType() {
        return type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public static enum InstrcutionType {
        Directive,
        Instrcution;
    }
}
