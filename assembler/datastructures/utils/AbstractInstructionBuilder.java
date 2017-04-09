package assembler.datastructures.utils;

/**
 * Created by abdelrahman on 4/9/17.
 */
public abstract class AbstractInstructionBuilder {
    // the op code of the instruction in decimal
    private int opCode;

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    /**
     * Returns a Hexadecimal string representation of the instruction built
     * @return the instruction built in hexadecimal
     */
    @Override
    public abstract String toString();
}
