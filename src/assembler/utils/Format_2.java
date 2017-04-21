package src.assembler.utils;

import src.assembler.Logger;
import src.assembler.datastructures.Format;
import src.assembler.datastructures.RegisterTable;

import java.util.HashMap;

import static src.assembler.datastructures.OpcodeTable.getOpCode;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_2 extends ObjectBuilder {
    protected int firstOperand;
    protected int secondOperand;

    public Format_2() {
    }

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

    }

    @Override
    Format getFormat() {
        return null;
    }

}
