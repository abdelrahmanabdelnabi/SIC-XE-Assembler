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
    protected int operand;
    private int secondOperand;

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02X", opCode));
        builder.append(String.format("%01X", operand));
        builder.append(String.format("%01X", secondOperand));

        // TODO: reset values to default

        return builder.toString();
    }

    @Override
    void setIndirect(boolean isIndirect) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    void setImmediate(boolean isImmediate) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    void setIndexed(boolean isIndexed) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    void setBaseRelative(boolean isBaseRelative) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    void setPCRelative(boolean isPCRelative) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    void setOperand(int operand) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    void setSecondOperand(int secondOperand) {
        throw new UnsupportedOperationException("Format 2 doesn't have flags");
    }

    @Override
    Format getFormat() {
        return Format.FORMAT2;
    }

}
