package src.assembler.datastructures;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class InstProp {
    private OperandType FirstOperand;
    private OperandType SecondOperand;
    private int opCode;
    private Format format;

    InstProp(int opCode, Format format) {
        this.opCode = opCode;
        this.format = format;
    }

    public OperandType getFirstOperand() {
        return FirstOperand;
    }

    public void setFirstOperand(OperandType firstOperand) {
        FirstOperand = firstOperand;
    }

    public OperandType getSecondOperand() {
        return SecondOperand;
    }

    public void setSecondOperand(OperandType secondOperand) {
        SecondOperand = secondOperand;
    }

    int getOpCode() {
        return opCode;
    }

    public Format getFormat() {
        return format;
    }
}
