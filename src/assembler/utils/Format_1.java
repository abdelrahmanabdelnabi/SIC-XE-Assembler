package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.datastructures.OpcodeTable.getHexOpcode;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_1 extends ObjectBuilder {

    @Override
    public String toString() {
        return getHexOpcode(inst.getMnemonic());
    }

    @Override
    void setIndirect(boolean isIndirect) {

    }

    @Override
    void setImmediate(boolean isImmediate) {

    }

    @Override
    void setIndexed(boolean isIndirect) {

    }

    @Override
    void setBaseRelative(boolean isBaseRelative) {

    }

    @Override
    void setPCRelative(boolean isPCRelative) {

    }

    @Override
    void setOperand(int operand) {

    }

    @Override
    Format getFormat() {
        return null;
    }


}
