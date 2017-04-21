package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format3_4 extends Format_2 {
    // TODO implement M
    // E !
    @Override
    public String toString() {
        return null;
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
    void setSecondOperand(int secondOperand) {
        throw new UnsupportedOperationException("Format 3 and 4 do not have a second operand");
    }

    @Override
    Format getFormat() {
        return null;
    }
}
