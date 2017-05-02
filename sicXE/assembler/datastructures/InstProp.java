package sicXE.assembler.datastructures;

/**
 * Created by abdelrahman on 4/7/17.
 */
public class InstProp {
    private final OperandType firstOperand;
    private final OperandType secondOperand;
    private final int opCode;
    private final Format format;

    InstProp(int opCode, Format format, OperandType firstOperand, OperandType secondOperand) {
        this.opCode = opCode;
        this.format = format;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public OperandType getFirstOperand() {
        return firstOperand;
    }

    public OperandType getSecondOperand() {
        return secondOperand;
    }

    int getOpCode() {
        return opCode;
    }

    public Format getFormat() {
        return format;
    }
}
