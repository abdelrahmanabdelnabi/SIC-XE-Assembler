package src.assembler.utils;

import src.assembler.datastructures.Format;

import static src.assembler.utils.Common.extendToLength;

/**
 * Created by ahmed on 4/21/17.
 */
public class Format_4 extends Format_3 {

    @Override
    public String toString() {
        String objectString = "";
        // Add only first 6 bits of the opCode 'as binary'
        String BinaryString = Integer.toBinaryString(opCode);
        String extended = extendToLength(BinaryString, 8);
        String trimmed = extended.substring(0, 6);
        objectString += (trimmed);
        // N I X B P E
        objectString += (isIndirect);
        objectString += (isImmediate);
        objectString += (isIndexed);
        objectString += (isBaseRelative);
        objectString += (isPCRelative);
        objectString += ("1");
        //
        objectString = extendToLength(Integer.toHexString(Integer.parseInt(objectString, 2)), 3);
        objectString += (extendToLength(Integer.toHexString(operand), 5));

        return objectString.toUpperCase();
    }

    @Override
    public Format getFormat() {
        return Format.FORMAT4;
    }

}
