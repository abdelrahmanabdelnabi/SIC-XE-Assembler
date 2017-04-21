package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/21/17.
 */
public class Format_4 extends Format_3 {


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
        super.setBaseRelative(false);
    }

    @Override
    void setPCRelative(boolean isPCRelative) {
        super.setPCRelative(false);
    }

    @Override
    void setOperand(int operand) {

    }

    @Override
    void setSecondOperand(int secondOperand) {

    }

    @Override
    Format getFormat() {
        return null;
    }
}
