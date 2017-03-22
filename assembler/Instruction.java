package assembler;

/**
 * Created by abdelrahman on 3/22/17.
 */
public class Instruction {
    private String label;
    private String mnemonic;
    private String operand;
    private InstrcutionType type;

    public Instruction(String label, String mnemonic, String operand) {
        this.label = label;
        this.mnemonic = mnemonic;
        this.operand = operand;
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

    public static enum InstrcutionType {
        Directive,
        Instrcution;
    }
}
