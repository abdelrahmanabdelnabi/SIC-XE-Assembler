package src.assembler.utils;

import src.assembler.Instruction;
import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public abstract class ObjectBuilder {// the op code of the instruction in decimal
    Instruction inst = null;

    public void setInstruction(Instruction inst) {
        this.inst = inst;
    }

    /**
     * Returns a Hexadecimal string representation of the instruction built
     *
     * @return the instruction built in hexadecimal
     */
    @Override
    public abstract String toString();

    abstract void setIndirect(boolean isIndirect);

    abstract void setImmediate(boolean isImmediate);

    abstract void setIndexed(boolean isIndirect);

    abstract void setBaseRelative(boolean isBaseRelative);

    abstract void setPCRelative(boolean isPCRelative);

    abstract void setOperand(int operand);

    abstract Format getFormat();
}
