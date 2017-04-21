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
    public void setIndirect(boolean isIndirect) {

    }

    @Override
    public void setImmediate(boolean isImmediate) {

    }

    @Override
    public void setIndexed(boolean isIndirect) {

    }

    @Override
    public void setBaseRelative(boolean isBaseRelative) {
        super.setBaseRelative(false);
    }

    @Override
    public void setPCRelative(boolean isPCRelative) {
        super.setPCRelative(false);
    }

    @Override
    public void setOperand(int operand) {

    }

    @Override
    public void setSecondOperand(int secondOperand) {

    }

    @Override
    public Format getFormat() {
        return null;
    }
}
