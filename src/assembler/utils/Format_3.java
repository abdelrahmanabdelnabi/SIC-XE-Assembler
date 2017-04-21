package src.assembler.utils;

import src.assembler.datastructures.Format;

/**
 * Created by ahmed on 4/12/17.
 */
public class Format_3 extends Format_2 {

    String isIndirect = "0";
    String isImmediate = "0";
    String isIndexed = "0";
    String isBaseRelative = "0";
    String isPCRelative = "0";

    private StringBuilder builder;

    @Override
    public String toString() {
        // TODO: implement this method
        String objectCode = builder.toString();

        builder.setLength(0);
        return objectCode;
    }

    @Override
    public void setIndirect() {
        this.isIndirect = "1";

    }

    @Override
    public void setImmediate() {
        this.isImmediate = "1";

    }

    @Override
    public void setIndexed() {
        this.isIndexed = "1";
    }

    @Override
    public void setBaseRelative() {
        this.isBaseRelative = "1";

    }

    @Override
    public void setPCRelative() {
        this.isPCRelative = "1";

    }

    @Override
    public void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    public void setSecondOperand(int secondOperand) {
        throw new UnsupportedOperationException("Format 3 and 4 do not have a second operand");
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT3;

    }
}
