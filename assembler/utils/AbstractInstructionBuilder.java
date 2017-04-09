package assembler.datastructures.utils;

/**
 * Created by abdelrahman on 4/9/17.
 */

/**
 * incrementally builds the hexadecimal object code of an instruction.
 * it takes the opCode and operand, and flags and returns hexadecimal object code
 * of the instruction as a string
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
