package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/21/17.
 */
public class Format_4 extends Format_3 {
    private StringBuilder builder = new StringBuilder();

    @Override
    public String toString() {
        // Add only first 6 bits of the opCode 'as binary'
        builder.append(Integer.toBinaryString(opCode).substring(0, 6));
        // N I X B P E
        builder.append(isIndirect);
        builder.append(isImmediate);
        builder.append(isIndexed);
        builder.append(isBaseRelative);
        builder.append(isPCRelative);
        builder.append("1");

        int value = Integer.parseInt(builder.toString(), 2);
        builder = new StringBuilder(Integer.toHexString(value));
        builder.append(extendToLength(Integer.toHexString(operand), 5));
        return builder.toString().toUpperCase();
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT4;
    }

}
